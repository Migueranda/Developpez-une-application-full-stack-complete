import { Component, OnInit } from '@angular/core';
import { User } from './interface/user.model';
import { UserService } from './service/user.sevice';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit {
//  users: User | undefined; 

users: User[] = [];
 

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.userService.getUsers().subscribe(data => {
      this.users = data;
    });
  }
  


}
