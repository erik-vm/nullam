package com.example.nullam.event;

import com.example.nullam.company.Company;
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
    List<Event> showAllEvents() {
        return eventService.showAllEvents();
    }

    @GetMapping("/coming")
    List<Event> showAllComingEvents() {
        return  eventService.comingEvents();
    }

    @GetMapping("/coming/{results}")
    List<Event> showComingEventsResultsPerSearch(@PathVariable("results") int results){
        return eventService.upComingNextEvents(results);
    }

    @GetMapping("/past")
    List<Event> showAllPastEvents() {
        return eventService.pastEvents();
    }
    @GetMapping("/past/{results}")
    List<Event> showPastEventsResultsPerSearch(@PathVariable("results") int results){
        return eventService.latestPastEvents(results);
    }

    @GetMapping("/find={id}")
    Event findEventById(@PathVariable("id") Long id){
        return eventService.findEventById(id);
    }

    @GetMapping("/{eventId}/persons")
    List<Person> personsInEventWithEventId(@PathVariable("eventId") Long eventId){
        return eventService.personsInEventWithEventId(eventId);
    }
     @GetMapping("/{eventId}/companies")
    List<Company> companiesInEventWithEventId(@PathVariable("eventId") Long eventId){
        return eventService.companiesInEventWithEventId(eventId);
    }

    @PostMapping("/save")
    Event saveEvent(@RequestBody Event event){
        return eventService.saveEvent(event);
    }

    @PutMapping("/update={id}")
    Event updateEventById(@PathVariable("id") Long id, @RequestBody Event event){
        return eventService.updateEventById(id, event);
    }

    @PutMapping("/add/event={eventId}/person={personId}")
    void addPersonToEventByPersonIdAndEventId(@PathVariable("eventId") Long eventId, @PathVariable("personId") Long personId){
        eventService.addPersonToEventByPersonIdAndEventId(personId,eventId);
    }

    @PutMapping("/add/event={eventId}/company={companyId}")
    void addCompanyToEventByCompanyIdAndEventId(@PathVariable("eventId") Long eventId, @PathVariable("companyId") Long companyId){
        eventService.addCompanyToEventByCompanyIdAndEventId(companyId,eventId);
    }

    @DeleteMapping("/delete={id}")
    void deleteEventById(@PathVariable("id") Long id){
        eventService.deleteEventById(id);
    }


}
