package com.example.nullam.event;

import com.example.nullam.company.Company;
import com.example.nullam.company.CompanyService;
import com.example.nullam.person.Person;
import com.example.nullam.person.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final PersonService personService;
    private final CompanyService companyService;

    List<Event> showAllEvents() {
        if (eventRepository.findAll().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
        return eventRepository.findAll();
    }


    List<Event> comingEvents() {
        List<Event> comingEvents = new ArrayList<>();
        for (Event event : eventRepository.findAll(Sort.by("time").descending())) {
            if (event.getTime().isAfter(LocalDateTime.now())) {
                comingEvents.add(event);
            }
        }
        return comingEvents;
    }

    List<Event> upComingNextEvents(int results) {
        List<Event> upComingNextFiveEvents = new ArrayList<>();

        if (comingEvents().size() < results) {
            for (int i = 0; i <= comingEvents().size(); i++) {
                upComingNextFiveEvents.add(comingEvents().get(i));
            }
        } else {
            for (int i = 0; i < results; i++) {
                upComingNextFiveEvents.add(comingEvents().get(i));
            }
        }
        return upComingNextFiveEvents;
    }

    List<Event> pastEvents() {
        List<Event> pastEvents = new ArrayList<>();
        for (Event event : eventRepository.findAll(Sort.by("time").descending())) {
            if (event.getTime().isBefore(LocalDateTime.now())) {
                pastEvents.add(event);
            }
        }
        return pastEvents;
    }

    List<Event> latestPastEvents(int results) {
        List<Event> latestPastEvents = new ArrayList<>();

        if (pastEvents().size() < results) {
            for (int i = 0; i <= pastEvents().size(); i++) {
                latestPastEvents.add(pastEvents().get(i));
            }
        } else {
            for (int i = 0; i < results; i++) {
                latestPastEvents.add(pastEvents().get(i));
            }
        }
        return latestPastEvents;
    }

    List<Person> personsInEventWithEventId(Long eventId){
        Event event = findEventById(eventId);
        return event.getPersonParticipants();
    }

    List<Company> companiesInEventWithEventId(Long eventId){
        Event event = findEventById(eventId);
        return event.getCompanyParticipants();
    }

    Event findEventById(Long id) {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return optionalEvent.get();
    }

    Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    Event updateEventById(Long id, Event updatedEvent) {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Event existingEvent = optionalEvent.get();
        if (existingEvent.getTime().isBefore(LocalDateTime.now())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        existingEvent.setName(updatedEvent.getName());
        existingEvent.setLocation(updatedEvent.getLocation());
        existingEvent.setTime(updatedEvent.getTime());
        existingEvent.setDescription(updatedEvent.getDescription());

        return eventRepository.save(existingEvent);
    }

    void deleteEventById(Long id) {
        if (doesEventExistById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        eventRepository.deleteById(id);
    }

    void addPersonToEventByPersonIdAndEventId(Long personId, Long eventId) {
        Person person = personService.findPersonById(personId);
        if (!doesEventExistById(eventId)) {
            Event event = findEventById(eventId);
            if (event.getTime().isBefore(LocalDateTime.now())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            event.getPersonParticipants().add(person);
            person.getEvents().add(event);
            personService.savePerson(person);
            saveEvent(event);
        }
    }

    void addCompanyToEventByCompanyIdAndEventId(Long companyId, Long eventId) {
        Company company = companyService.findCompanyById(companyId);
        if (!doesEventExistById(eventId)) {
            Event event = findEventById(eventId);
            if (event.getTime().isBefore(LocalDateTime.now())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            event.getCompanyParticipants().add(company);
            company.getEvents().add(event);
            companyService.saveCompany(company);
            saveEvent(event);
        }
    }

    private boolean doesEventExistById(Long id) {
        List<Event> eventList = new ArrayList<>();
        for (Event event : eventRepository.findAll()) {
            if (event.getId().equals(id)) {
                eventList.add(event);
            }
        }
        return eventList.isEmpty();
    }


}
