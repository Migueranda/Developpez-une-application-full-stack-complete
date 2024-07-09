import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { Post } from '../interface/post.model';

@Injectable({
  providedIn: 'root'
})
export class PostService {
  private pathService = '/api/post';

  constructor(private httpClient: HttpClient) {}

  getPosts(): Observable<Post[]> {

    return this.httpClient.get<{post: Post[]}>(this.pathService).pipe(
        map((response: { post: any; }) => response.post) 
    );
  }

  createPost(post: Post): Observable<Post> {
    return this.httpClient.post<Post>(this.pathService, post);
  }

  getPostById(id: number): Observable<Post> {
    return this.httpClient.get<Post>(`${this.pathService}/${id}`);
  }

  updatePost(post: Post): Observable<Post> {
    return this.httpClient.put<Post>(`${this.pathService}/${post.id}`, post);
  }

//   deletePost(id: number): Observable<void> {
//     return this.http.delete<void>(`${this.apiUrl}/${id}`);
//   }
}
