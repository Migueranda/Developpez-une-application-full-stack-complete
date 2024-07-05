import { Component, OnInit } from '@angular/core';
import { Post } from '../interface/post.model';
import { PostService } from '../service/post.service';
import { Router } from '@angular/router';
// import { Post } from './interface/post.model';
// import { PostService } from './service/post.service';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.scss']
})
export class PostComponent implements OnInit {

  posts : Post[] =[];

  constructor(private postService : PostService, private router: Router){}


  ngOnInit(): void {
    this.loadPosts();
  }

  loadPosts():void{
    this.postService.getPosts().subscribe({
      next: (data) => {
        this.posts = data;
    },
    error: (error) => {
        console.error('Erreur lors du chargement des posts', error);
    },
    complete: () => {
        console.log('Chargement des posts complet');
    }
      
    })
  }

  navigateToPost(postId: number): void {
    this.router.navigate(['/post', postId]);
  }
}
