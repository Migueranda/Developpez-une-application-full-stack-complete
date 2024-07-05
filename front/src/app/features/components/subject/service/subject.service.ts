import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, map } from "rxjs";
import { Subject } from "../interface/subject.model";
// import { Subject } from "../components/subject/interfaces/subject.model";


@Injectable({
    providedIn: 'root'
})

export class SubjectService{

    private pathService = '/api/subject';

    constructor(private httpClient: HttpClient) {}

    getSubjects(): Observable<Subject[]> {
        return this.httpClient.get<{subject: Subject[]}>(this.pathService).pipe(
            map(response => response.subject) // Extraire le tableau de sujets
        );
    }

//    public getSubjects(): Observable<Subject[]> {
//         return this.httpClient.get<Subject[]>(`${this.pathService}`);
//     }

    // getSubject(id: number): Observable<SubjectDto> {
    //     return this.http.get<SubjectDto>(`${this.baseUrl}/${id}`);
    //   }
    
    //   createSubject(subject: SubjectDto): Observable<SubjectDto> {
    //     return this.http.post<SubjectDto>(this.baseUrl, subject);
    //   }
    
    //   updateSubject(id: number, subject: SubjectDto): Observable<SubjectDto> {
    //     return this.http.put<SubjectDto>(`${this.baseUrl}/${id}`, subject);
    //   }
    
    //   deleteSubject(id: number): Observable<void> {
    //     return this.http.delete<void>(`${this.baseUrl}/${id}`);
    //   }
    
}