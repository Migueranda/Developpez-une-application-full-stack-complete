import { Component, OnInit } from '@angular/core';
import { Post } from '../interface/post.model';
import { PostService } from '../service/post.service';
import { Router } from '@angular/router';
import { UserService } from '../../user/service/user.sevice';


@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.scss']
})
export class PostComponent implements OnInit {
  user: any;
  posts : Post[] =[];

  constructor(private postService : PostService, private router: Router, private userService: UserService){}


  ngOnInit(): void {
    this.loadPosts();
    this.user = this.userService.getUser();
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
