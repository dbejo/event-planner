package com.purpleelephant.eventplanner;

import com.purpleelephant.eventplanner.event.Event;
import com.purpleelephant.eventplanner.event.EventRepository;
import com.purpleelephant.eventplanner.organization.Organization;
import com.purpleelephant.eventplanner.organization.OrganizationService;
import com.purpleelephant.eventplanner.person.Person;
import com.purpleelephant.eventplanner.person.PersonRepository;
import com.purpleelephant.eventplanner.person.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class PersonServiceTest {

    private PersonService personService;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private OrganizationService organizationService;

    @Mock
    private EventRepository eventRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        personService = new PersonService(personRepository, organizationService, eventRepository);
    }

    @Test
    void testCreate() {
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setNotes("Test notes");
        person.setPersonalEmail("test@example.com");
        person.setActive(true);
        person.setOrganizations(new ArrayList<>());
        person.setEvents(new ArrayList<>());

        when(personRepository.save(person)).thenReturn(person);

        assertEquals(person, personService.create(person));
    }

    @Test
    void testFindByFullName() {
        List<Person> people = new ArrayList<>();
        Person person1 = new Person();
        person1.setFirstName("John");
        person1.setLastName("Doe");
        people.add(person1);

        when(personRepository.findByFirstName("John")).thenReturn(Optional.of(people));

        Collection<Person> result = personService.findByFullName("John", "Doe");

        assertEquals(1, result.size());
        assertEquals(person1, result.iterator().next());
    }

    @Test
    void testFindEventsByPersonId() {
        Person person = new Person();
        person.setId(1);

        Event event = Event.builder()
                .name("Test Event")
                .description("This is a test event.")
                .location("Test Location")
                .date(new Date(System.currentTimeMillis()))
                .active(true)
                .people(new ArrayList<>())
                .build();

        event.getPeople().add(person);

        List<Event> events = new ArrayList<>();
        events.add(event);

        when(eventRepository.findAll()).thenReturn(events);

        Collection<Event> result = personService.findEventsByPersonId(1);

        assertEquals(1, result.size());
        assertEquals(event, result.iterator().next());
    }

    @Test
    void testFindByOrganizationId() {
        List<Person> people = new ArrayList<>();
        Person person1 = new Person();
        person1.setFirstName("John");
        person1.setLastName("Doe");
        people.add(person1);

        Organization organization = new Organization();
        organization.setId(1);

        when(organizationService.findById(1)).thenReturn(Optional.of(organization));
        when(personRepository.findByOrganizations(organization)).thenReturn(Optional.of(people));
    }
}
