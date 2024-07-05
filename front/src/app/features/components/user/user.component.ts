import { Component, OnInit } from '@angular/core';
import { User } from './interface/user.model';
import { UserService } from './service/user.sevice';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit {
 user: User | undefined; ;

  constructor(private userService: UserService) { }

  ngOnInit(): void {


    this.userService.getUserById(1).subscribe({
      next: (userData) => {
        this.user = userData;
      },
      error: (error) => {
        console.error('Error fetching user data', error);
      }
    });
  }
  


}
