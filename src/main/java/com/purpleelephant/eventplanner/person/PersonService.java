package com.purpleelephant.eventplanner.person;

import com.purpleelephant.eventplanner.event.Event;
import com.purpleelephant.eventplanner.event.EventRepository;
import com.purpleelephant.eventplanner.organization.Organization;
import com.purpleelephant.eventplanner.organization.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;
    private final OrganizationRepository organizationRepository;
    private final EventRepository eventRepository;

    public Person create(Person person) {
        log.info("Create new {} {} person", person.getFirstName(), person.getLastName());
        Person newPerson = personRepository.save(new Person(null, person.getFirstName(), person.getLastName(), person.getNotes(), person.getPersonalEmail(), person.getActive(), null, null));
        if (person.getOrganizations() != null) {
            addPersonToOrganizations(newPerson, person.getOrganizations());
        }
        if (person.getEvents() != null) {
            addPersonToEvents(newPerson, person.getEvents());
        }
        return personRepository.findById(newPerson.getId()).orElseThrow();
    }

    public Person modify(Person person) {
        log.info("Modify {} {} person", person.getFirstName(), person.getLastName());
        Person newPerson = personRepository.save(person);
        if (person.getOrganizations() != null) {
            addPersonToOrganizations(newPerson, person.getOrganizations());
        }
        if (person.getEvents() != null) {
            addPersonToEvents(newPerson, person.getEvents());
        }
        return personRepository.findById(newPerson.getId()).orElseThrow();
    }

    public void delete(int id) {
        Person person = personRepository.findById(id).orElseThrow();
        eventRepository.findAll().forEach(e -> {
            if (e.getPeople().contains(person))
                throw new RuntimeException("Cannot delete person because they are assigned to an event");
        });
        organizationRepository.findAll().forEach(o -> {
            if (o.getPeople().contains(person))
                throw new RuntimeException("Cannot delete person because they are assigned to an organization");
        });
        personRepository.deleteById(person.getId());
        log.info("{} {} person deleted", person.getFirstName(), person.getLastName());
    }

    public void addPersonToOrganizations(Person person, Collection<Organization> organizations) {
        List<Organization> orgsToRemoveFrom = new ArrayList<>();
        organizationRepository.findAll().forEach(org -> {
            if (org.getPeople() != null) {
                Iterator<Person> iterator = org.getPeople().iterator();
                while (iterator.hasNext()) {
                    Person p = iterator.next();
                    if (p.getId().equals(person.getId())) {
                        iterator.remove();
                        orgsToRemoveFrom.add(org);
                    }
                }
            }
        });
        orgsToRemoveFrom.forEach(organizationRepository::save);
        for (Organization org : organizations) {
            organizationRepository.findById(org.getId()).ifPresent(organization -> {
                Collection<Person> orgPeople = organization.getPeople();
                if (!orgPeople.contains(person)) {
                    orgPeople.add(person);
                }
                organizationRepository.save(organization);
            });
        }
    }

    public void addPersonToEvents(Person person, Collection<Event> events) {
        List<Event> eventsToRemoveFrom = new ArrayList<>();
        eventRepository.findAll().forEach(e -> {
            if (e.getPeople() != null) {
                Iterator<Person> iterator = e.getPeople().iterator();
                while (iterator.hasNext()) {
                    Person p = iterator.next();
                    if (p.getId().equals(person.getId())) {
                        iterator.remove();
                        eventsToRemoveFrom.add(e);
                    }
                }
            }
        });
        eventsToRemoveFrom.forEach(eventRepository::save);
        for (Event event : events) {
            eventRepository.findById(event.getId()).ifPresent(e -> {
                Collection<Person> eventPeople = e.getPeople();
                if (!eventPeople.contains(person)) {
                    eventPeople.add(person);
                }
                eventRepository.save(e);
            });
        }
    }
}
