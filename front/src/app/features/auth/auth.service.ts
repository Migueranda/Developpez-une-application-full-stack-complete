import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, catchError, tap, throwError } from 'rxjs';
import { User } from '../components/subject/interface/subject.model';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private userSubject: BehaviorSubject<User | null>;
  public user: Observable<User | null>;

  private pathService = '/api/auth';
  private token: string | null = null;

  constructor(private httpClient: HttpClient, private router: Router) {
    const userFromStorage = localStorage.getItem('user');
    const tokenFromStorage = localStorage.getItem('token');
    this.userSubject = new BehaviorSubject<User | null>(userFromStorage ? JSON.parse(userFromStorage) : null);
    this.user = this.userSubject.asObservable();
    this.token = tokenFromStorage;
  }

  public get userValue(): User | null {
    return this.userSubject.value;
  }

  register(user: User): Observable<User> {
    return this.httpClient.post<User>(`${this.pathService}/register`, user).pipe(
      tap(response => {
        console.log('User registration successful:', response);
      }),
      catchError(error => {
        console.error('User registration error:', error);
        return throwError(error.error || 'Server error');
      })
    );
  }

  login(credentials: { email: string, password: string }): Observable<User> {
    return this.httpClient.post<User>(`${this.pathService}/login`, credentials).pipe(
      tap(user => {
        localStorage.setItem('user', JSON.stringify(user));
        localStorage.setItem('token', user.token);
        this.token = user.token;
        this.userSubject.next(user);
      }),
      catchError(error => {
        console.error('Login error:', error);
        return throwError(error.error || 'Server error');
      })
    );
  }

  logout(): void {
    localStorage.removeItem('user');
    localStorage.removeItem('token');
    this.userSubject.next(null);
    this.router.navigate(['/login']);
  }

  getToken(): string | null {
    return this.token;
  }
}
