import { Component, OnInit } from '@angular/core';
import { Post } from '../interface/post.model';
import { PostService } from '../service/post.service';
import { Router } from '@angular/router';
import { UserService } from '../../user/service/user.sevice';
import { forkJoin } from 'rxjs';
import { MatIconRegistry } from '@angular/material/icon';
import { DomSanitizer } from '@angular/platform-browser';


@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.scss']
})
export class PostComponent implements OnInit {

  userNames: { [key: number]: string } = {};
  posts : Post[] =[];
  sorted: boolean = false;
  sortAscending: boolean = true;

  constructor(private postService : PostService, private router: Router, private userService: UserService,
   
  ){ }


  ngOnInit(): void {
    this.loadPosts();
  }

  loadPosts(): void {
    this.postService.getPosts().subscribe({
      next: (posts) => {
        this.posts = posts;
        this.loadUserNames();
      },
      error: (error) => {
        console.error('Erreur lors du chargement des posts', error);
      },
      complete: () => {
        console.log('Chargement des posts complet');
      }
    });
  }

  loadUserNames(): void {
    const userIds = Array.from(new Set(this.posts.map(post => post.userId))); 
    const userRequests = userIds.map(id => this.userService.getUserById(id));
    
    forkJoin(userRequests).subscribe(users => {
      users.forEach(user => {
        this.userNames[user.id] = user.userName;
      });
    });
  }

  toggleSortByDate(): void {
    this.sortAscending = !this.sortAscending;
    this.sortPosts();
  }

  sortPosts(): void {
    if (this.sortAscending) {
      this.posts.sort((a, b) => new Date(b.date).getTime() - new Date(a.date).getTime());
    } else {
      this.posts.sort((a, b) => new Date(a.date).getTime() - new Date(b.date).getTime());
    }
  }

  navigateToPost(postId: number): void {
    this.router.navigate(['/post', postId]);
  }
}
