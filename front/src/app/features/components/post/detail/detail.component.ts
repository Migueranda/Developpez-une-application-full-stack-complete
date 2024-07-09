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
import { switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.scss']
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
    private authService: AuthService
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
      this.postService.getPostById(this.postId).subscribe(post => {
        this.posts = post;
        this.loadSubject(post.themeId);
        this.loadComments(this.postId!); 
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
        if (this.userMap.has(comment.userId)) {
          return of(this.userMap.get(comment.userId)); 
        } else {
          return this.userService.getUserById(comment.userId).pipe(
            switchMap(user => {
              console.log(`Ajout de l'utilisateur ${user.userName} dans le cache pour userId ${comment.userId}`);
              this.userMap.set(comment.userId, user.userName);
              return of(user.userName);
            })
          );
        }
      });

      forkJoin(userObservables).subscribe(usernames => {
        console.log('Commentaires chargés :', comments);
        console.log('Noms des utilisateurs :', usernames);
        this.comments = comments.map((comment, index) => ({
          ...comment,
          username: usernames[index]
        }));
      });
    });
  }

  getUsername(userId: number): string {
    const username = this.userMap.get(userId);
    console.log(`Récupération du nom d'utilisateur pour userId ${userId}: ${username}`);
    return username || 'Utilisateur inconnu';
  }

  onSubmit(): void {
    if (this.commentForm?.valid && this.posts?.id && this.user) {
      const commentData: CreateComment = {
        postId: this.posts.id,
        userId: this.user.id,
        description: this.commentForm.value.description
      };
      this.commentService.addComment(this.posts.id, commentData).subscribe({
        next: (comment) => {
          this.matSnackBar.open('Commentaire ajouté avec succès', 'Fermer', { duration: 3000 });
          this.commentForm?.reset();
          if (this.posts?.id) {
            this.loadComments(this.posts.id); 
          }
        },
        error: () => {
          this.matSnackBar.open('Échec de l\'ajout du commentaire', 'Fermer', { duration: 3000 });
        }
      });
    }
  }
}
