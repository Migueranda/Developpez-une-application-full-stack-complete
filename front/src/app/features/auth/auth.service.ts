
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, catchError, tap, throwError } from 'rxjs';
import { User } from '../components/subject/interface/subject.model'; 
import { Router } from '@angular/router';
@Injectable({
  providedIn: 'root'
})
export class AuthService {  

  private userSubject: BehaviorSubject<any> ;
  public user: Observable<any>;
  
  private pathService = '/api/auth'

  constructor(private httpClient: HttpClient, private router: Router) {
    const userFromStorage = localStorage.getItem('user');
    this.userSubject = new BehaviorSubject<any>(userFromStorage ? JSON.parse(userFromStorage) : null);
    this.user = this.userSubject.asObservable();
  }

  public get userValue(): any {
    return this.userSubject.value;
  }

  register(user: User): Observable<User> {
    return this.httpClient.post<User>(`${this.pathService}/register`, user).pipe(
     catchError(error =>{
      return throwError(error.error || 'Server error');
     })
    );
  }

  login(credentials: { email: string, password: string }): Observable<User> {
    return this.httpClient.post<User>(`${this.pathService}/login`, credentials).pipe(
        tap(user => {
            localStorage.setItem('user', JSON.stringify(user));
            this.userSubject.next(user);
        })
    )
  }

  logout():void{
    localStorage.removeItem('user');
    this.userSubject.next(null);
    this.router.navigate(['/login']);
  }
}
