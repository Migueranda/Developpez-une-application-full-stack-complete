import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { AppModule } from './app.module';
import { SubjectComponent } from './features/components/subject/list/subject.component';
import { PostComponent } from './features/components/post/list/post.component';
// import { SubjectComponent } from './features/components/subject/subject.component';
// import { PostComponent } from './features/components/post/post.component';


// consider a guard combined with canLoad / canActivate route option
// to manage unauthenticated user to access private routes
const routes: Routes = [
  { 
    path: '', component: HomeComponent
  },
  {
    path: 'subject', component : SubjectComponent
  },
  {
    path: 'post', component : PostComponent
  },
  
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
