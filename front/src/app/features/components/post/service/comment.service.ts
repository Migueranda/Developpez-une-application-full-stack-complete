import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Comment } from '../interface/comment.model'; 

@Injectable({
  providedIn: 'root'
})
export class CommentService {
  constructor(private httpClient: HttpClient) {}
  
  addComment(postId: number, commentData: { description: string }): Observable<Comment> {
    // ID utilisateur en dur pour le test
    const userId = 1; // Remplacez 1 par un ID valide qui existe dans votre base de données

    const data = {
        ...commentData,
        userId: userId
    };
    return this.httpClient.post<Comment>(`/api/post/${postId}/comment`, data);
}

}
