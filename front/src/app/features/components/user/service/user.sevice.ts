import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../interface/user.model';
import { AuthService } from 'src/app/features/auth/auth.service';
@Injectable({
  providedIn: 'root'
})
export class UserService {


  private pathService = '/api/user';

  constructor(private httpClient: HttpClient,  private authService: AuthService ) {}

  getUser(): any {
    return this.authService.userValue;
  }

  getUserById(userId: number): Observable<User> {
    return this.httpClient.get<User>(`${this.pathService}/${userId}`);
  }

  updateUser(userId: number, user: User): Observable<User> {
    return this.httpClient.put<User>(`${this.pathService}/${userId}`, user);
  }

  isLoggedIn(): boolean {
    return !!this.authService.userValue;
  }
  
  // getUsers(): Observable<User[]> {
  //   return this.httpClient.get<User[]>(this.pathService);
  // }

  // getUserById(id: number): Observable<User> {
  //     return this.httpClient.get<User>(`${this.pathService}/${id}`);
  // }

  // createUser(user: User): Observable<User> {
  //   return this.httpClient.post<User>(this.pathService, user);
  // }

  // updateUser(id: number, user: User): Observable<User> {
  //   return this.httpClient.put<User>(`${this.pathService}/${id}`, user);
  // }

  // deleteUser(id: number): Observable<void> {
  //   return this.httpClient.delete<void>(`${this.pathService}/${id}`);
  // }
}
