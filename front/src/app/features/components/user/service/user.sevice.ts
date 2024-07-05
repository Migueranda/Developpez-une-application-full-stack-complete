import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../interface/user.model';
@Injectable({
  providedIn: 'root'
})
export class UserService {

  private pathService = '/api/users';

  constructor(private httpClient: HttpClient) {}
  
  getUsers(): Observable<User[]> {
    return this.httpClient.get<User[]>(this.pathService);
  }

  getUserById(id: number): Observable<User> {
      return this.httpClient.get<User>(`${this.pathService}/${id}`);
  }

  createUser(user: User): Observable<User> {
    return this.httpClient.post<User>(this.pathService, user);
  }

  updateUser(id: number, user: User): Observable<User> {
    return this.httpClient.put<User>(`${this.pathService}/${id}`, user);
  }

  deleteUser(id: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.pathService}/${id}`);
  }
}
