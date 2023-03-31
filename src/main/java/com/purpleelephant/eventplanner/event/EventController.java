package com.purpleelephant.eventplanner.event;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/api/v1/event")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @Operation(
            summary = "Get all event",
            tags = {"event"}
    )
    @GetMapping("/all")
    public ResponseEntity<Collection<Event>> getAll() {
        return ResponseEntity.ok(eventService.getEvents());
    }

    @Operation(
            summary = "Add an event",
            tags = {"event"}
    )
    @PostMapping
    public ResponseEntity<Event> add(@RequestBody Event event) {
        return ResponseEntity.ok(eventService.create(event));
    }

    @Operation(
            summary = "Modify an event",
            tags = {"event"}
    )
    @PutMapping
    public ResponseEntity<Event> modify(@RequestBody Event event) {
        try {
            return ResponseEntity.ok(eventService.modify(event));
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.badRequest().body(event);
        }
    }

    @Operation(
            summary = "Delete an event",
            tags = {"event"}
    )
    @DeleteMapping
    public ResponseEntity<Event> delete(@RequestBody Event event) {
        try {
            return ResponseEntity.ok(eventService.delete(event));
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.badRequest().body(event);
        }
    }
}
