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
  // user: any;
  userNames: { [key: number]: string } = {};
  posts : Post[] =[];
  sorted: boolean = false;

  constructor(private postService : PostService, private router: Router, private userService: UserService,
    // iconRegistry: MatIconRegistry,
    // sanitizer: DomSanitizer
  ){
    // iconRegistry.addSvgIcon(
    //   'arrow_back',
    //   sanitizer.bypassSecurityTrustResourceUrl('assets/icons/arrow-down.svg')
    // );
  }


  ngOnInit(): void {
    this.loadPosts();
    // this.user = this.userService.getUser();
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
    this.sorted = !this.sorted;
    if (this.sorted) {
      this.posts.sort((a, b) => new Date(b.date).getTime() - new Date(a.date).getTime());
    } else {
      this.loadPosts(); // Reload posts to reset sorting
    }
  }
  // loadPosts():void{
  //   this.postService.getPosts().subscribe({
  //     next: (data) => {
  //       this.posts = data;
  //   },
  //   error: (error) => {
  //       console.error('Erreur lors du chargement des posts', error);
  //   },
  //   complete: () => {
  //       console.log('Chargement des posts complet');
  //   }
      
  //   })
  // }

  navigateToPost(postId: number): void {
    this.router.navigate(['/post', postId]);
  }
}
