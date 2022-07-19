import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavComponent } from './nav/nav.component';
import { HomeComponent } from './home/home.component';
import { AddEventComponent } from './add-event/add-event.component';
import { FooterComponent } from './footer/footer.component';
import { ComingEventsComponent } from './coming-events/coming-events.component';
import { PastEventsComponent } from './past-events/past-events.component';
import { AddPersonComponent } from './add-person/add-person.component';
import { AddCompanyComponent } from './add-company/add-company.component';
import { ParticipantsComponent } from './participants/participants.component';

@NgModule({
  declarations: [
    AppComponent,
    NavComponent,
    HomeComponent,
    AddEventComponent,
    FooterComponent,
    ComingEventsComponent,
    PastEventsComponent,
    AddPersonComponent,
    AddCompanyComponent,
    ParticipantsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
