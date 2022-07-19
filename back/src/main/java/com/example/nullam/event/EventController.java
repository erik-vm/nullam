package com.example.nullam.event;

import com.example.nullam.company.Company;
import com.example.nullam.exceptions.*;
import com.example.nullam.person.Person;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
@AllArgsConstructor
public class EventController {

    private final EventService eventService;


    @GetMapping
    List<Event> showAllEvents() throws Exception {
        return eventService.showAllEvents();
    }

    @GetMapping("/coming")
    List<Event> showAllComingEvents() throws Exception {
        return  eventService.comingEvents();
    }

    @GetMapping("/coming/{results}")
    List<Event> showComingEventsResultsPerSearch(@PathVariable("results") int results) throws Exception {
        return eventService.upComingNextEvents(results);
    }

    @GetMapping("/past")
    List<Event> showAllPastEvents() throws Exception {
        return eventService.pastEvents();
    }
    @GetMapping("/past/{results}")
    List<Event> showPastEventsResultsPerSearch(@PathVariable("results") int results) throws Exception {
        return eventService.latestPastEvents(results);
    }

    @GetMapping("/find={id}")
    Event findEventById(@PathVariable("id") Long id) throws EventNotFound {
        return eventService.findEventById(id);
    }

    @GetMapping("/{eventId}/persons")
    List<Person> personsInEventWithEventId(@PathVariable("eventId") Long eventId) throws EventNotFound {
        return eventService.personsInEventWithEventId(eventId);
    }
     @GetMapping("/{eventId}/companies")
    List<Company> companiesInEventWithEventId(@PathVariable("eventId") Long eventId) throws EventNotFound {
        return eventService.companiesInEventWithEventId(eventId);
    }

    @PostMapping("/save")
    Event saveEvent(@RequestBody Event event){
        return eventService.saveEvent(event);
    }

    @PutMapping("/update={id}")
    Event updateEventById(@PathVariable("id") Long id, @RequestBody Event event) throws Exception {
        return eventService.updateEventById(id, event);
    }

    @PutMapping("/add/event={eventId}/person={personId}")
    void addPersonToEventByPersonIdAndEventId(@PathVariable("eventId") Long eventId, @PathVariable("personId") Long personId) throws Exception {
        eventService.addPersonToEventByPersonIdAndEventId(personId,eventId);
    }
    @PutMapping("/remove/event={eventId}/person={personId}")
    void removePersonFromEventByPersonIdAndEventId(@PathVariable("eventId") Long eventId, @PathVariable("personId") Long personId) throws Exception{
    eventService.removePersonFromEventByPersonIdAndEventId(personId, eventId);
    }

    @PutMapping("/add/event={eventId}/company={companyId}/participants={totalParticipants}")
    void addCompanyWithParticipantsToEventByCompanyIdAndEventId(@PathVariable("eventId") Long eventId, @PathVariable("companyId") Long companyId,
                                                @PathVariable("totalParticipants") Integer totalParticipants) throws Exception {
        eventService.addCompanyWithParticipantsToEventByCompanyIdAndEventId(companyId,eventId, totalParticipants);
    }
    @PutMapping("/remove/event={eventId}/company={companyId}")
    void removeCompanyFromEventByCompanyIdAndEventId(@PathVariable("eventId") Long eventId, @PathVariable("companyId") Long companyId) throws Exception {
        eventService.removeCompanyFromEventByCompanyIdAndEventId(companyId,eventId);
    }

    @DeleteMapping("/delete={id}")
    void deleteEventById(@PathVariable("id") Long id) throws EventNotFound {
        eventService.deleteEventById(id);
    }


}
