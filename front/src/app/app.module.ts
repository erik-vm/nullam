import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './navbar/navbar.component';
import { FooterComponent } from './footer/footer.component';
import { AddEventComponent } from './add-event/add-event.component';
import { AddParticipantComponent } from './add-participant/add-participant.component';
import { HomeComponent } from './home/home.component';
import { ComingEventsComponent } from './coming-events/coming-events.component';
import { PastEventsComponent } from './past-events/past-events.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    FooterComponent,
    AddEventComponent,
    AddParticipantComponent,
    HomeComponent,
    ComingEventsComponent,
    PastEventsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
