import { Component, HostListener, OnInit, ViewChild } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { Post } from '../post/interface/post.model';
import { PostService } from '../post/service/post.service';
import { MatSidenav } from '@angular/material/sidenav';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
 
})
export class HeaderComponent implements OnInit {

  isMobile: boolean | undefined;
  constructor(
    private postService: PostService, private breakpointObserver: BreakpointObserver) {  this.isMobile = window.innerWidth <= 600; }
    @HostListener('window:resize', ['$event'])
    onResize(event: any) {
      this.isMobile = event.target.innerWidth <= 600;
    }
  ngOnInit(): void {
    
  }
  
} 
