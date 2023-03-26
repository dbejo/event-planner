package com.purpleelephant.eventplanner.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;

    public Event create(Event event) {
        log.info("Create new {} event", event.getName());
        return eventRepository.save(new Event(null, event.getName(), event.getDescription(), event.getLocation(), event.getDate(), event.getActive(), event.getPeople()));
    }

    public Event modify(Event event) throws Exception {
        if (event.getId() == null) {
            log.info("Event id missing");
            throw new Exception("Set event id for modification");
        }
        log.info("Modify {} event", event.getName());
        return eventRepository.save(event);
    }

    public Collection<Event> getEvents() {
        log.info("List all events");
        return eventRepository.findAll();
    }

    public Event delete(Event event) throws Exception {
        if (event.getId() == null) {
            log.info("Event id missing");
            throw new Exception("Set event id for modification");
        }
        log.info("Delete {} event", event.getName());
        eventRepository.deleteById(event.getId());
        return event;
    }
}
