package com.example.nullam.event;

import com.example.nullam.company.Company;
import com.example.nullam.company.CompanyService;
import com.example.nullam.exceptions.*;
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

    List<Event> showAllEvents() throws Exception {
        if (eventRepository.findAll().isEmpty()) {
            throw new Exception("No events in database.");
        }
        return eventRepository.findAll();
    }


    List<Event> comingEvents() throws Exception {
        List<Event> comingEvents = new ArrayList<>();
        for (Event event : eventRepository.findAll(Sort.by("time").descending())) {
            if (event.getTime().isAfter(LocalDateTime.now())) {
                comingEvents.add(event);
            }
        }
        if (comingEvents.isEmpty()) {
            throw new Exception("No coming events in database.");
        }
        return comingEvents;
    }

    List<Event> upComingNextEvents(int results) throws Exception {
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

    List<Event> pastEvents() throws Exception {
        List<Event> pastEvents = new ArrayList<>();
        for (Event event : eventRepository.findAll(Sort.by("time").descending())) {
            if (event.getTime().isBefore(LocalDateTime.now())) {
                pastEvents.add(event);
            }
        }
        if (pastEvents.isEmpty()) {
            throw new Exception("No past events in database.");
        }
        return pastEvents;
    }

    List<Event> latestPastEvents(int results) throws Exception {
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

    List<Person> personsInEventWithEventId(Long eventId) throws EventNotFound {
        Event event = findEventById(eventId);
        return event.getPersonParticipants();
    }

    List<Company> companiesInEventWithEventId(Long eventId) throws EventNotFound {
        Event event = findEventById(eventId);
        return event.getCompanyParticipants();
    }

    Event findEventById(Long id) throws EventNotFound {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isEmpty()) {
            throw new EventNotFound(id);
        }
        return optionalEvent.get();
    }

    Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    Event updateEventById(Long id, Event updatedEvent) throws Exception {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isEmpty()) {
            throw new EventNotFound(id);
        }
        Event existingEvent = optionalEvent.get();
        if (existingEvent.getTime().isBefore(LocalDateTime.now())) {
            throw new Exception("Cant make events before local time.");
        }
        existingEvent.setName(updatedEvent.getName());
        existingEvent.setLocation(updatedEvent.getLocation());
        existingEvent.setTime(updatedEvent.getTime());
        existingEvent.setDescription(updatedEvent.getDescription());

        return eventRepository.save(existingEvent);
    }

    void deleteEventById(Long id) throws EventNotFound {
        if (doesEventExistById(id)) {
            throw new EventNotFound(id);
        }
        eventRepository.deleteById(id);
    }

    void addPersonToEventByPersonIdAndEventId(Long personId, Long eventId) throws Exception {
        Person person = personService.findPersonById(personId);
        Event event = findEventById(eventId);
        if (event.getTime().isBefore(LocalDateTime.now())) {
            throw new Exception("Cant register to past events.");
        }
        if (event.getPersonParticipants().contains(person)) {
            throw new Exception("Person is already registered in event.");
        }
        event.getPersonParticipants().add(person);
        event.setTotalParticipants(event.getTotalParticipants() + 1);
        person.getEvents().add(event);
        personService.updatePersonEventList(personId, person);
        saveEvent(event);
    }
    void removePersonFromEventByPersonIdAndEventId(Long personId, Long eventId) throws Exception {
        Person person = personService.findPersonById(personId);
        Event event = findEventById(eventId);
        if (event.getTime().isBefore(LocalDateTime.now())) {
            throw new Exception("Cant unregister from past events.");
        }
        if (!event.getPersonParticipants().contains(person)){
            throw new Exception("Person not found in event.");
        }
        event.getPersonParticipants().remove(person);
        event.setTotalParticipants(event.getTotalParticipants() - 1);
        person.getEvents().remove(event);
        personService.updatePersonEventList(personId, person);
        saveEvent(event);
    }

    void addCompanyWithParticipantsToEventByCompanyIdAndEventId(Long companyId, Long eventId, Integer participants) throws Exception {
        Company company = companyService.findCompanyById(companyId);
        Event event = findEventById(eventId);
        if (event.getTime().isBefore(LocalDateTime.now())) {
            throw new Exception("Cant register to past events.");
        }
        if (event.getCompanyParticipants().contains(company)) {
            throw new Exception("Company is already registered in event.");
        }
        event.getCompanyParticipants().add(company);

        event.getCompanyParticipants().get(event.getCompanyParticipants().indexOf(company)).setParticipants(participants);
        event.setTotalParticipants(event.getTotalParticipants() + participants);
        company.getEvents().add(event);
        companyService.updateCompanyEventList(companyId, company);
        saveEvent(event);
    }

    void removeCompanyFromEventByCompanyIdAndEventId(Long companyId, Long eventId) throws Exception {
        Company company = companyService.findCompanyById(companyId);
        Event event = findEventById(eventId);
        if (event.getTime().isBefore(LocalDateTime.now())) {
            throw new Exception("Cant unregister from past events.");
        }
        if (!event.getCompanyParticipants().contains(company)){
            throw new Exception("Company not found in event.");
        }
        Integer companyParticipants = 0;
        for (Company com : event.getCompanyParticipants()){
            if (com.equals(company)){
                companyParticipants = com.getParticipants();
            }
        }
        event.getCompanyParticipants().remove(company);
        event.setTotalParticipants(event.getTotalParticipants() - companyParticipants );
        company.getEvents().remove(event);
        companyService.updateCompanyEventList(companyId, company);
        saveEvent(event);
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
