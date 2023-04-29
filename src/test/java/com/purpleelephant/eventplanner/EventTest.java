package com.purpleelephant.eventplanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.purpleelephant.eventplanner.event.Event;
import com.purpleelephant.eventplanner.person.Person;

public class EventTest {
    private static Event event;

    @BeforeAll
    public static void setUp() {
        event = new Event();
        event.setId(1);
        event.setName("Test Event");
        event.setDescription("Test Event Description");
        event.setLocation("Test Event Location");
        event.setStartDate(LocalDateTime.of(2023, 5, 1, 12, 0));
        event.setEndDate(LocalDateTime.of(2023, 5, 1, 14, 0));
        event.setActive(true);
        event.setAgenda("Test Event Agenda");
    }

    @Test
    public void testGetId() {
        assertEquals(Integer.valueOf(1), event.getId());
    }

    @Test
    public void testGetName() {
        assertEquals("Test Event", event.getName());
    }

    @Test
    public void testGetDescription() {
        assertEquals("Test Event Description", event.getDescription());
    }

    @Test
    public void testGetLocation() {
        assertEquals("Test Event Location", event.getLocation());
    }

    @Test
    public void testGetStartDate() {
        assertEquals(LocalDateTime.of(2023, 5, 1, 12, 0), event.getStartDate());
    }

    @Test
    public void testGetEndDate() {
        assertEquals(LocalDateTime.of(2023, 5, 1, 14, 0), event.getEndDate());
    }

    @Test
    public void testIsActive() {
        assertEquals(true, event.getActive());
    }

    @Test
    public void testGetAgenda() {
        assertEquals("Test Event Agenda", event.getAgenda());
    }

    @Test
    public void testGetPeople() {
        // Mock the Person object
        Person person = mock(Person.class);
        when(person.getId()).thenReturn(1);
        when(person.getLastName()).thenReturn("Doe");
        when(person.getFirstName()).thenReturn("John");

        // Add the mocked Person object to the Event object
        Collection<Person> people = Arrays.asList(person);
        event.setPeople(people);

        // Test that the Event object returns the correct People objects
        assertEquals(people, event.getPeople());
    }
}
