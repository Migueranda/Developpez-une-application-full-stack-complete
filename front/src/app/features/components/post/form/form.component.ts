import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { PostService } from '../service/post.service';
// import { SubjectService } from 'src/app/features/service/subject.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Post } from '../interface/post.model';
import { SubjectService } from '../../subject/service/subject.service';
import { AuthService } from 'src/app/features/auth/auth.service';


@Component({
  selector: 'form-field',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class FormComponent implements OnInit {

  public postForm: FormGroup | undefined;
  public subjects$ = this.subjectService.getSubjects();
  private postId: string | null = null;
  public onUpdate: boolean = false;
  private userId: number | null = null;

  constructor(
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private matSnackBar: MatSnackBar,
    private postService: PostService,
    private subjectService: SubjectService,
    private authService: AuthService,
    private router: Router
  ) { }

  ngOnInit(): void {
    const user = this.authService.userValue;
    this.userId = user?.id || null;

    this.postId = this.route.snapshot.paramMap.get('id');

    if (this.postId) {
      this.postService.getPostById(+this.postId).subscribe({
        next: (post) => this.initForm(post),
        error: () => this.matSnackBar.open('Error loading the post', 'Close', { duration: 3000 })
      });
    } else {
      this.initForm();
    }
  }

  private initForm(post?: Post): void {
    this.postForm = this.fb.group({
      title: [
        post ? post.title : '',
        [Validators.required]
      ],
      themeId: [
        post ? post.themeId : '',
        [Validators.required]
      ],
      description: [
        post ? post.description : '',
        [
          Validators.required,
          Validators.maxLength(2000)
        ]
      ],
      userId: [this.userId, [Validators.required]]
      // userId: ['1', [Validators.required]] 
    });
  }

  public onSubmit(): void {
    if (this.postForm?.valid) {
      const post = this.postForm.value;
      const operation = this.postId ? this.postService.updatePost({ ...post, id: +this.postId }) : this.postService.createPost(post);

      operation.subscribe({
        next: (result) => {
          this.matSnackBar.open('Post saved successfully!', 'Close', { duration: 3000 });
          this.router.navigate(['/post']);
        },
        error: () => this.matSnackBar.open('Failed to save the post', 'Close', { duration: 3000 })
      });
    }
  }
}
