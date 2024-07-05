import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../interface/user.model';
@Injectable({
  providedIn: 'root'
})
export class UserService {

  private pathService = '/api/user';

  constructor(private httpClient: HttpClient) {}

  getUserById(id: number): Observable<User> {
      return this.httpClient.get<User>(`${this.pathService}/${id}`);
  }
}
