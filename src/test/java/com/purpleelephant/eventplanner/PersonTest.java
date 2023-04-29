package com.purpleelephant.eventplanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.purpleelephant.eventplanner.event.Event;
import com.purpleelephant.eventplanner.organization.Organization;
import com.purpleelephant.eventplanner.person.Person;

public class PersonTest {
    private static Person person;

    @BeforeAll
    public static void setUp() {
        person = new Person();
        person.setId(1);
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setNotes("Some notes");
        person.setPersonalEmail("john.doe@example.com");
        person.setActive(true);
    }

    @Test
    public void testGetId() {
        assertEquals(Integer.valueOf(1), person.getId());
    }

    @Test
    public void testGetFirstName() {
        assertEquals("John", person.getFirstName());
    }

    @Test
    public void testGetLastName() {
        assertEquals("Doe", person.getLastName());
    }

    @Test
    public void testGetNotes() {
        assertEquals("Some notes", person.getNotes());
    }

    @Test
    public void testGetPersonalEmail() {
        assertEquals("john.doe@example.com", person.getPersonalEmail());
    }

    @Test
    public void testIsActive() {
        assertEquals(true, person.getActive());
    }

    @Test
    public void testGetOrganizations() {
        // Mock the Organization objects
        Organization org1 = mock(Organization.class);
        org1.setId(1);
        org1.setName("Organization 1");

        Organization org2 = mock(Organization.class);
        org2.setId(2);
        org2.setName("Organization 2");

        // Add the mocked Organization objects to the Person object
        Collection<Organization> orgs = Arrays.asList(org1, org2);
        person.setOrganizations(orgs);

        // Test that the Person object returns the correct Organization objects
        assertEquals(orgs, person.getOrganizations());
    }

    @Test
    public void testGetEvents() {
        // Mock the Event objects
        Event event1 = mock(Event.class);
        event1.setId(1);
        event1.setName("Event 1");

        Event event2 = mock(Event.class);
        event2.setId(2);
        event2.setName("Event 2");

        // Add the mocked Event objects to the Person object
        Collection<Event> events = Arrays.asList(event1, event2);
        person.setEvents(events);

        // Test that the Person object returns the correct Event objects
        assertEquals(events, person.getEvents());
    }
}
