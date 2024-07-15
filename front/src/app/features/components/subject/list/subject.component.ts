import { Component, OnInit } from '@angular/core';
import { Subject } from '../interface/subject.model';
import { SubjectService } from '../service/subject.service';
import { AuthService } from 'src/app/features/auth/auth.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-subject',
  templateUrl: './subject.component.html',
  styleUrls: ['./subject.component.scss']
})
export class SubjectComponent implements OnInit {
  subjects: Subject[] = [];
  user: any;

  constructor(
    private subjectService: SubjectService,
    private authService: AuthService, 
    private snackBar: MatSnackBar 
  ) { }

  ngOnInit(): void {
    this.user = this.authService.userValue;
    this.loadSubjects();
  }

  loadSubjects(): void {
    this.subjectService.getSubjects().subscribe({
      next: (data) => {
        this.subjects = data;
      },
      error: (error) => {
        console.error('Erreur lors du chargement des sujets', error);
      },
      complete: () => {
        console.log('Chargement des sujets complet');
      }
    });
  }

  subscribe(subjectId: number): void {
    if (this.user) {
      this.subjectService.subscribeToSubject(this.user.id, subjectId).subscribe({
        next: () => {
          this.snackBar.open('Abonné avec succès', 'Fermer', {
            duration: 3000
          });
        },
        error: (error) => {
          console.error('Erreur lors de la souscription', error);
          this.snackBar.open('Erreur lors de la souscription', 'Fermer', {
            duration: 3000
          });
        }
      });
    }
  }
}
