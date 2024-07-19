import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';
import { Comment, CreateComment } from '../interface/comment.model'; 

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  private pathService = '/api';
  constructor(private httpClient: HttpClient) {}

  getCommentsByPostId(postId: number): Observable<Comment[]> {
    return this.httpClient.get<Comment[]>(`${this.pathService}/post/${postId}/comment`);
  }

  addComment(postId: number, comment: CreateComment): Observable<Comment> {
    return this.httpClient.post<Comment>(`${this.pathService}/post/${postId}/comment`, comment);
  }
}
