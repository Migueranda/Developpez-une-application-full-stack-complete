import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, map } from "rxjs";
import { Subject } from "../interface/subject.model";


@Injectable({
    providedIn: 'root'
})

export class SubjectService{

    private pathService = '/api/subject';
    private subscriptionPathService = '/api/subscriptions';

    constructor(private httpClient: HttpClient) {}

    getSubjects(): Observable<Subject[]> {
        return this.httpClient.get<{subject: Subject[]}>(this.pathService).pipe(
            map(response => response.subject) 
        );
    }

    subscribeToSubject(userId: number, subjectId: number): Observable<any> {
        return this.httpClient.post(`${this.subscriptionPathService}/${userId}/${subjectId}`, {});
    }

    unsubscribeFromSubject(userId: number, subjectId: number): Observable<any> {
        return this.httpClient.delete(`${this.subscriptionPathService}/${userId}/${subjectId}`);
    }
}