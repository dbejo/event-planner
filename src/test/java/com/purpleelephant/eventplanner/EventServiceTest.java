package com.purpleelephant.eventplanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.purpleelephant.eventplanner.event.Event;
import com.purpleelephant.eventplanner.event.EventRepository;
import com.purpleelephant.eventplanner.event.EventService;

public class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    @BeforeEach
    void setUp() {
        eventRepository = Mockito.mock(EventRepository.class);
        eventService = new EventService(eventRepository);
    }

    @Test
    public void testCreateEvent() {
        Event event = new Event();
        event.setName("Test Event");
        event.setDescription("This is a test event");
        when(eventRepository.save(event)).thenReturn(event);

        Event createdEvent = eventService.create(event);

        verify(eventRepository, times(1)).save(event);
        assertNotNull(createdEvent);
        assertEquals(event.getName(), createdEvent.getName());
        assertEquals(event.getDescription(), createdEvent.getDescription());
    }

    @Test
    public void testModifyEvent() {
        Event event = new Event();
        event.setId(1);
        event.setName("Test Event");
        event.setDescription("This is a test event");

        when(eventRepository.save(event)).thenReturn(event);

        Event modifiedEvent = eventService.modify(event);

        verify(eventRepository, times(1)).save(event);
        assertNotNull(modifiedEvent);
        assertEquals(event.getName(), modifiedEvent.getName());
        assertEquals(event.getDescription(), modifiedEvent.getDescription());
    }

    @Test
    public void testGetEvents() {
        List<Event> events = new ArrayList<>();
        events.add(new Event(1, "Test Event 1", "This is a test event 1", null, null, null, null, null, null));
        events.add(new Event(2, "Test Event 2", "This is a test event 2", null, null, null, null, null, null));

        when(eventRepository.findAll()).thenReturn(events);

        Collection<Event> allEvents = eventService.getEvents();

        verify(eventRepository, times(1)).findAll();
        assertNotNull(allEvents);
        assertEquals(events.size(), allEvents.size());
        assertEquals(events.get(0).getName(), allEvents.iterator().next().getName());
    }

    @Test
    public void testDeleteEvent() {
        Event event = new Event();
        event.setId(1);
        event.setName("Test Event");
        event.setDescription("This is a test event");

        when(eventRepository.findById(1)).thenReturn(Optional.of(event));

        eventService.delete(1);

        verify(eventRepository, times(1)).findById(1);
        verify(eventRepository, times(1)).deleteById(1);
    }
}
