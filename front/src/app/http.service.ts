import {HttpClient} from '@angular/common/http'
import { Injectable } from '@angular/core';
import { Person } from './person.interface';
import { Company } from './company.interface';
import { Event } from './event.interface';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HttpService {
  baseUrl = 'http://localhost:8080'

  constructor(private http: HttpClient) { }


//Person services

addPerson(person : Person): Observable<unknown>{
  return this.http.post(`${this.baseUrl}/person/save`, person)
}

findPersonById(id : number) : Observable<Person>{
  return this.http.get<Person>(`${this.baseUrl}/person/find=${id}`)
}

updatePersonById(id:number, person:Person): Observable<unknown>{
  return this.http.put(`${this.baseUrl}/person/update=${id}`, person)
}

deletePersonById(id : number): Observable<unknown>{
  return this.http.delete(`${this.baseUrl}/person/delete=${id}`)
}



//Company services

addCompany(company : Company): Observable<unknown>{
  return this.http.post(`${this.baseUrl}/company/save`, company)
}

findCompanyById(id : number) : Observable<Company>{
  return this.http.get<Company>(`${this.baseUrl}/company/find=${id}`)
}

updateCompanyById(id:number, company:Company): Observable<unknown>{
  return this.http.put(`${this.baseUrl}/company/update=${id}`, company)
}

deleteCompanyById(id : number): Observable<unknown>{
  return this.http.delete(`${this.baseUrl}/company/delete=${id}`)
}



//Event services

addEvent(event : Event): Observable<unknown>{
  return this.http.post(`${this.baseUrl}/event/save`, event)
}

findEventById(id : number) : Observable<Event>{
  return this.http.get<Event>(`${this.baseUrl}/event/find=${id}`)
}

updateEventById(id:number, event:Event): Observable<unknown>{
  return this.http.put(`${this.baseUrl}/event/update=${id}`, event)
}

deleteEventById(id : number): Observable<unknown>{
  return this.http.delete(`${this.baseUrl}/event/delete=${id}`)
}

//Displaying events with different conditions 

getAllEvents() : Observable<Array<Event>>{
  return this.http.get<Event[]>(`${this.baseUrl}/event`)
}

getAllComingEvents() : Observable<Array<Event>>{
  return this.http.get<Event[]>(`${this.baseUrl}/event/coming`)
}

getComingEventsResultsPerSearch(result:number) : Observable<Array<Event>>{
  return this.http.get<Event[]>(`${this.baseUrl}/event/coming/${result}`)
}

getAllPastEvents() : Observable<Array<Event>>{
  return this.http.get<Event[]>(`${this.baseUrl}/event/past`)
}

getPastEventsResultsPerSearch(result:number) : Observable<Array<Event>>{
  return this.http.get<Event[]>(`${this.baseUrl}/event/past/${result}`)
}

getPersonInEventWithEventId(eventId : number):Observable<Array<Event>>{
  return this.http.get<Event[]>(`${this.baseUrl}/event/${eventId}/persons`)
}
getCompaniesInEventWithEventId(eventId : number):Observable<Array<Event>>{
  return this.http.get<Event[]>(`${this.baseUrl}/event/${eventId}/companies`)
}

//Adding participants to events

addPersonToEventByPersonIdAndEventId(eventId: number, personId:number): Observable<unknown>{
  return this.http.put(`${this.baseUrl}/event/add/event=${eventId}/person=${personId}`, null)
}

addCompanyToEventByPersonIdAndEventId(eventId: number, companyId:number): Observable<unknown>{
  return this.http.put(`${this.baseUrl}/event/add/event=${eventId}/company=${companyId}`, null)
}

}
