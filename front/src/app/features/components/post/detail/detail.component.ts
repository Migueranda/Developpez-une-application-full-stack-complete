import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { PostService } from '../service/post.service';
import { SubjectService } from '../../subject/service/subject.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Post } from '../interface/post.model';
import { CommentService } from '../service/comment.service';

@Component({
  selector: 'app-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.scss']
})
export class DetailComponent implements OnInit {

   posts: Post | undefined;
   public commentForm: FormGroup ;
   public subjects$ = this.subjectService.getSubjects();
   subejctMap = new Map<number, string>();
   comments: Comment[] = [];
  
  private postId: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private postService: PostService,
    private subjectService: SubjectService,
    private commentService: CommentService,
    private matSnackBar: MatSnackBar,
    private router: Router

  ) { 
    this.commentForm = this.fb.group({
      description: ['', Validators.required] 
    });
  }

  ngOnInit(): void {        
     const id = this.route.snapshot.paramMap.get('id')!

     if (id) {
      this.postService.getPostById(+id).subscribe(post => {
        this.posts = post;
        this.loadSubject(post.themeId);  
        
      });
    }
    this.loadSubjects();

    //  if (id) {
    //   const postId = +id; // Convertit la chaîne en nombre
    //   this.postService.getPostById(postId)
    //       .subscribe((post: Post) => this.posts = post);
    // } else {
    //     console.error('Invalid ID'); // Gérer le cas où l'ID n'est pas disponible
    // }
  
  }

  
  loadSubjects(): void {
    this.subjectService.getSubjects().subscribe(themes => {
      themes.forEach(theme => {
        this.subejctMap.set(theme.id, theme.title);
      });
    });
  }

  loadSubject(themeId: number): void {
    const themeTitle = this.subejctMap.get(themeId) || 'Thème inconnu';
    console.log('Le thème associé:', themeTitle); 
  }

  onSubmit(): void {
    if(this.commentForm?.valid && this.posts?.id){
      this.commentService.addComment(this.posts.id, this.commentForm.value).subscribe({
        next: (comment) => {
          this.matSnackBar.open('Commentaire ajouté avec succès', 'Fermer', { duration: 3000 });
          this.commentForm?.reset();
          // Optionnel : recharger les commentaires ou mettre à jour l'affichage
        },
        error: () => {
          this.matSnackBar.open('Commentaire ajouté avec succès', 'Fermer',{ duration: 3000 });
        }

      });
    }
  }

}
// this.postService
    //   .getPostById(id)
    //   .subscribe((post: Post) => this.post = post);