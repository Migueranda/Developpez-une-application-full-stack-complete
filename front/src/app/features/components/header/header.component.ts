import { Component, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { Post } from '../post/interface/post.model';
import { PostService } from '../post/service/post.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
 
})
export class HeaderComponent implements OnInit {

 
  constructor(private postService: PostService) { }

  ngOnInit(): void {
    
  }

}
