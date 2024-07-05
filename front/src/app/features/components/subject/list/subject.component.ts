import { Component, OnInit } from '@angular/core';
import { Subject } from '../interface/subject.model';
import { SubjectService } from '../service/subject.service';
// import { SubjectService } from '../../service/subject.service';
// import { Subject, User } from './interfaces/subject.model';

@Component({
  selector: 'app-subject',
  templateUrl: './subject.component.html',
  styleUrls: ['./subject.component.scss']
})
export class SubjectComponent implements OnInit {

  subjects: Subject[] = [];  

  constructor(private subjectService: SubjectService ) { }

  ngOnInit(): void {
    this.loadSubjects();
  }

  loadSubjects(): void {
    this.subjectService.getSubjects().subscribe({
      next: (data) => {
          this.subjects = data;
      },
      error: (error) => {
          console.error('Erreur lors du chargement des subjects', error);
      },
      complete: () => {
          console.log('Chargement des subjects complet');
      }
  });
  }

 
}
