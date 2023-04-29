package com.purpleelephant.eventplanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.purpleelephant.eventplanner.organization.Organization;
import com.purpleelephant.eventplanner.person.Person;

public class OrganizationTest {
    private static Organization organization;

    @BeforeAll
    public static void setUp() {
        organization = new Organization(1);
        organization.setTopLevel(true);
        organization.setName("Test Organization");
        organization.setActive(true);
        organization.setAddress("Test Address");
    }

    @Test
    public void testGetId() {
        assertEquals(Integer.valueOf(1), organization.getId());
    }

    @Test
    public void testIsTopLevel() {
        assertEquals(true, organization.getTopLevel());
    }

    @Test
    public void testGetName() {
        assertEquals("Test Organization", organization.getName());
    }

    @Test
    public void testIsActive() {
        assertEquals(true, organization.getActive());
    }

    @Test
    public void testGetAddress() {
        assertEquals("Test Address", organization.getAddress());
    }

    @Test
    public void testGetPeople() {
        // Mock the Person objects
        Person person1 = mock(Person.class);
        when(person1.getId()).thenReturn(1);
        // when(person1.getName()).thenReturn("John Doe");
        when(person1.getFirstName()).thenReturn("John");
        when(person1.getLastName()).thenReturn("Doe");

        Person person2 = mock(Person.class);
        when(person2.getId()).thenReturn(2);
        // when(person2.getName()).thenReturn("Jane Doe");
        when(person1.getFirstName()).thenReturn("Jane");
        when(person1.getLastName()).thenReturn("Doe");

        // Add the mocked Person objects to the Organization object
        Collection<Person> people = Arrays.asList(person1, person2);
        organization.setPeople(people);

        // Test that the Organization object returns the correct People objects
        assertEquals(people, organization.getPeople());
    }

    @Test
    public void testGetParent() {
        // Mock the parent Organization object
        Organization parent = mock(Organization.class);
        when(parent.getId()).thenReturn(2);
        when(parent.getName()).thenReturn("Test Parent Organization");

        // Set the parent Organization object on the Organization object
        organization.setParent(parent);

        // Test that the Organization object returns the correct parent Organization
        // object
        assertEquals(parent, organization.getParent());
    }
}
