import { Component, OnInit } from '@angular/core';
import { User } from './interface/user.model';
import { UserService } from './service/user.sevice';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthService } from '../../auth/auth.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit {

  profileForm: FormGroup;
  // users: User[] = [];
  user: any;
  

  constructor(
    private userService: UserService,
    private authService: AuthService,
    private router: Router,
    private fb: FormBuilder,
    private snackBar: MatSnackBar
  ) { 
    this.profileForm = this.fb.group({
      userName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8)]]
    });
  }

  ngOnInit(): void {
    // this.userService.getUser().subscribe((data: User[]) => {
    //   this.users = data;
    // });

    this.user = this.authService.userValue;
    this.profileForm?.patchValue({
      userName: this.user.userName,
      email: this.user.email
    })
  }
  onSubmit(): void{
    if(this.profileForm?.valid){
      this.userService.updateUser(this.user.id, this.profileForm.value).subscribe({
          next: (response) => {
            this.snackBar.open('Profil mis à jour avec succès', 'Fermer', {
              duration: 3000,
            });
          },
          error: (error) => {
            this.snackBar.open('Erreur lors de la mise à jour du profil', 'Fermer', {
              duration: 3000,
            });
          }
      })
    }
  }
  onLogout(): void {
    this.authService.logout();
  }

}
