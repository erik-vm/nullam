import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddCompanyComponent } from './add-company/add-company.component';
import { AddEventComponent } from './add-event/add-event.component';
import { AddPersonComponent } from './add-person/add-person.component';
import { HomeComponent } from './home/home.component';
import { ParticipantsComponent } from './participants/participants.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'home',
    pathMatch: 'full',
  },
  {
    path: 'home',
    component: HomeComponent,
  },
  {
    path: 'add-event',
    component: AddEventComponent,
  },
  {
    path: 'add-person',
    component: AddPersonComponent
  },
  {
    path: 'add-company',
    component: AddCompanyComponent
  },
  {
    path: 'participants',
    component: ParticipantsComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
