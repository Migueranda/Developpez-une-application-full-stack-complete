import { RouterModule, Routes } from "@angular/router";
import { SubjectComponent } from "./components/subject/subject.component";
import { NgModule } from "@angular/core";

const routes : Routes = [
    {
        path:'subject',
        title: 'Subject',
        component: SubjectComponent,
       // canActivate: [AuthGuard], // Garde d'authentification pour protéger la route
        data: { 
            title: 'Subject Page' 
        }
    },

    {
        path: '', 
        redirectTo: '/subject', 
        pathMatch: 'full' // Redirection par défaut
    },
]

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})

export class SubjectRoutingModule {}