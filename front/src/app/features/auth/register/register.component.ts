import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, FormGroupDirective, NgForm, Validators } from '@angular/forms';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';
import { ErrorStateMatcher } from '@angular/material/core';
import { MatIconRegistry } from '@angular/material/icon';
import { DomSanitizer } from '@angular/platform-browser';

export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})


export class RegisterComponent implements OnInit {

  emailFormControl = new FormControl('', [Validators.required, Validators.email]);
  passwordFormControl = new FormControl('', [
    Validators.required,
    Validators.minLength(8),
    Validators.pattern(/^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[\W_]).+$/)
  ]);
  userNameFormControl = new FormControl('', [Validators.required]);

  hidePassword = true;
  matcher = new MyErrorStateMatcher();
  errorMessage: string = '';

  registerForm = new FormGroup({
    email: this.emailFormControl,
    password: this.passwordFormControl,
    userName: this.userNameFormControl
  });

  constructor(
    private authService: AuthService,
    private router: Router,
    private fb: FormBuilder,
    iconRegistry: MatIconRegistry,
    sanitizer: DomSanitizer
  ) 
  { 
    iconRegistry.addSvgIcon(
      'arrow_back',
      sanitizer.bypassSecurityTrustResourceUrl('assets/icons/arrow_back.svg'))
   }

  ngOnInit(): void {
  }

  register(): void {
    if (this.registerForm.valid) {
      const { email, userName, password } = this.registerForm.value;
      this.authService.register({ email, userName, password } as any).subscribe({
        next: (response) => {
          this.router.navigate(['/login']);
        },
        error: (error) => {
          if (error === 'Email already exists') {
            this.errorMessage = 'Email already in use. Please use a different email.';
          } else {
            this.errorMessage = 'Registration failed. Please try again.';
          }
        },
      });
    } else {
      this.errorMessage = 'Please enter valid credentials.';
    }
  }

  togglePasswordVisibility(): void {
    this.hidePassword = !this.hidePassword;
  }
  
}
