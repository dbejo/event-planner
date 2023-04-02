package com.purpleelephant.eventplanner;

import com.purpleelephant.eventplanner.event.Event;
import com.purpleelephant.eventplanner.event.EventRepository;
import com.purpleelephant.eventplanner.event.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateEvent() {
        // Arrange
        Event event = Event.builder()
                .name("Test Event")
                .description("This is a test event.")
                .location("Test Location")
                .date(new Date(System.currentTimeMillis()))
                .active(true)
                .people(new ArrayList<>())
                .build();
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        // Act
        Event createdEvent = eventService.create(event);

        // Assert
        assertNotNull(createdEvent);
        assertEquals(event.getName(), createdEvent.getName());
        assertEquals(event.getDescription(), createdEvent.getDescription());
        assertEquals(event.getLocation(), createdEvent.getLocation());
        assertEquals(event.getDate(), createdEvent.getDate());
        assertEquals(event.getActive(), createdEvent.getActive());
        assertEquals(event.getPeople(), createdEvent.getPeople());
    }

    @Test
    public void testModifyEvent() throws Exception {
        // Arrange
        Event event = Event.builder()
                .id(1)
                .name("Test Event")
                .description("This is a test event.")
                .location("Test Location")
                .date(new Date(System.currentTimeMillis()))
                .active(true)
                .people(new ArrayList<>())
                .build();
        when(eventRepository.save(any(Event.class))).thenReturn(event);
        when(eventRepository.findById(event.getId())).thenReturn(java.util.Optional.of(event));

        // Act
        Event modifiedEvent = eventService.modify(event);

        // Assert
        assertNotNull(modifiedEvent);
        assertEquals(event.getName(), modifiedEvent.getName());
        assertEquals(event.getDescription(), modifiedEvent.getDescription());
        assertEquals(event.getLocation(), modifiedEvent.getLocation());
        assertEquals(event.getDate(), modifiedEvent.getDate());
        assertEquals(event.getActive(), modifiedEvent.getActive());
        assertEquals(event.getPeople(), modifiedEvent.getPeople());
    }

    @Test
    public void testGetEvents() {
        // Arrange
        List<Event> events = new ArrayList<>();
        events.add(Event.builder().id(1).name("Event 1").build());
        events.add(Event.builder().id(2).name("Event 2").build());
        when(eventRepository.findAll()).thenReturn(events);

        // Act
        Collection<Event> result = eventService.getEvents();

        // Assert
        assertNotNull(result);
        assertEquals(events.size(), result.size());
        assertTrue(result.containsAll(events));
    }

    /*@Test
    public void testDeleteEvent() throws Exception {
        // Arrange
        Event event = Event.builder().id(1).name("Test Event").build();
        when(eventRepository.findById(event.getId())).thenReturn(java.util.Optional.of(event));

        // Act
        Event deletedEvent = eventService.delete(event);

        // Assert
        assertNotNull(deletedEvent);
        assertEquals(event.getName(), deletedEvent.getName());
        verify(eventRepository, times(1)).deleteById(event.getId());
    }*/

    /*@Test
    public void testDeleteEvent_WithMissingId() {
        // Arrange
        Event event = Event.builder().name("Test Event").build();

        // Act and Assert
        Exception exception = assertThrows(Exception.class, () -> eventService.delete(event));
        assertEquals("Set event id for modification", exception.getMessage());

        verify(eventRepository, times(0)).deleteById(anyInt());
    }*/
}