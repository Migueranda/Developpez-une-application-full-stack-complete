import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { PostService } from '../service/post.service';
import { SubjectService } from '../../subject/service/subject.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Post } from '../interface/post.model';
import { CommentService } from '../service/comment.service';
import { UserService } from '../../user/service/user.sevice';
import { Comment, CreateComment } from '../interface/comment.model';
import { AuthService } from 'src/app/features/auth/auth.service'; 
import { forkJoin, of } from 'rxjs';
import { catchError, map, switchMap } from 'rxjs/operators';
import { DatePipe } from '@angular/common';


@Component({
  selector: 'app-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.scss'],
  providers: [DatePipe]
})
export class DetailComponent implements OnInit {
 

  posts: Post | undefined;
  public commentForm: FormGroup;
  public subjects$ = this.subjectService.getSubjects();
  subjectMap = new Map<number, string>();
  comments: any[] = [];
  user: any;
  userMap = new Map<number, string>(); 
  private postId: number | undefined;

  constructor(
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private postService: PostService,
    private subjectService: SubjectService,
    private commentService: CommentService,
    private userService: UserService,
    private matSnackBar: MatSnackBar,
    public router: Router,
    private authService: AuthService,
    private datePipe: DatePipe 

  ) {
    this.commentForm = this.fb.group({
      description: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.user = this.authService.userValue; 
    const postIdParam = this.route.snapshot.paramMap.get('id');

    if (postIdParam !== null) {
      this.postId = +postIdParam; 
      this.postService.getPostById(this.postId).pipe(
        switchMap(post => {
          this.posts = post;
          this.loadSubject(post.themeId);
          return this.userService.getUserById(post.userId);
        })
      ).subscribe(user => {
        if (user) {
          this.userMap.set(user.id, user.userName);
          this.loadComments(this.postId!); 
        }
      });
    }
    this.loadSubjects();
  }

  loadSubjects(): void {
    this.subjectService.getSubjects().subscribe(themes => {
      themes.forEach(theme => {
        this.subjectMap.set(theme.id, theme.title);
      });
    });
  }

  loadSubject(themeId: number): void {
    const themeTitle = this.subjectMap.get(themeId) || 'Thème inconnu';
    console.log('Le thème associé:', themeTitle);
  }

loadComments(postId: number): void {
  this.commentService.getCommentsByPostId(postId).subscribe(comments => {
      const userObservables = comments.map(comment => {
          //  pour récupérer le nom d'utilisateur pour chaque commentaire
          return this.userService.getUserById(comment.userId).pipe(
            map(user => {
                return {...comment, userName: user.userName};
            }),
            catchError(error => {
                console.error(`Erreur lors de la récupération du nom d'utilisateur pour userId: ${comment.userId}`, error);
                return of({...comment, userName: 'Erreur de récupération'});
            })
        );
        
      });

      forkJoin(userObservables).subscribe(commentsWithUsers => {
          this.comments = commentsWithUsers.map(comment => ({
              ...comment,
              formattedDate: this.datePipe.transform(comment.date, 'short')
          }));
          console.log("Commentaires après traitement:", this.comments);
      });
  });
}

onSubmit(): void {
  if (this.commentForm?.valid && this.posts?.id && this.user) {
      const commentData: CreateComment = {
          postId: this.posts.id,
          userId: this.user.id,
          description: this.commentForm.value.description,
          date: new Date() 
      };
      this.commentService.addComment(this.posts.id, commentData).subscribe({
          next: (comment) => {
              this.matSnackBar.open('Commentaire ajouté avec succès', 'Fermer', { duration: 3000 });
              this.commentForm?.reset();
          
              this.comments.push({
                  ...comment,
                  username: this.user.userName,
                  formattedDate: this.datePipe.transform(new Date(), 'short')
              });
          },
          error: (error) => {
              this.matSnackBar.open('Échec de l\'ajout du commentaire', 'Fermer', { duration: 3000 });
          }
      });
  }
}

getUsername(userId: number): string {
  if (!userId) { // Vérification si userId est null ou undefined
      console.log("Appel de getUsername avec userId indéfini.");
      return 'Utilisateur inconnu';
  }
  const username = this.userMap.get(userId);
  return username || 'Utilisateur inconnu';
}

}
