package com.purpleelephant.eventplanner.person;

import com.purpleelephant.eventplanner.event.Event;
import com.purpleelephant.eventplanner.event.EventRepository;
import com.purpleelephant.eventplanner.organization.Organization;
import com.purpleelephant.eventplanner.organization.OrganizationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;
    private final OrganizationService organizationService;

    private final EventRepository eventRepository;

    public Person create(Person person) {
        log.info("Create new {} {} person", person.getFirstName(), person.getLastName());
        return personRepository.save(new Person(null, person.getFirstName(), person.getLastName(), person.getNotes(), person.getPersonalEmail(), person.getActive(), person.getOrganizations(), person.getEvents()));
    }

    public Collection<Person> findByFullName(String firstName, String lastName) {
        return personRepository.findByFirstName(firstName).orElseThrow().stream().filter(res -> res.getLastName().equals(lastName)).toList();
    }

    public Collection<Event> findEventsByPersonId(Integer id) {
        List<Event> events = eventRepository.findAll().stream().toList();
        List<Event> filteredEvent = new ArrayList<>();
        for (Event event : events) {
            if (event.getPeople().stream().anyMatch(person -> person.getId().equals(id))) {
                filteredEvent.add(event);
            }
        }
        return filteredEvent;
    }

    public Collection<Person> findByOrganizationId(Integer organizationId) {
        Organization organization = organizationService.findById(organizationId).orElseThrow();
        return personRepository.findByOrganizations(organization).orElseThrow();
    }

    public Optional<Person> findById(String id) {
        return personRepository.findById(Integer.valueOf(id));
    }

    public Person modify(Person person) throws Exception {
        if (person.getId() == null) {
            log.info("Person id missing");
            throw new Exception("Set person id for modification");
        }
        log.info("Modify {} {} person", person.getFirstName(), person.getLastName());
        return personRepository.save(person);
    }

    public void delete(int id) {
        Person person = personRepository.findById(id).orElseThrow();
        personRepository.deleteById(person.getId());
        log.info("{} {} person deleted", person.getFirstName(), person.getLastName());
    }
}
