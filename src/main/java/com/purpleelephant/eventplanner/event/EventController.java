package com.purpleelephant.eventplanner.event;

import com.purpleelephant.eventplanner.Response;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

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
    public ResponseEntity<Response> getAll() {
        return ResponseEntity.ok(Response.builder().timeStamp(LocalDateTime.now()).data(Map.of("events", eventService.getEvents())).build());
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
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        log.info("Delete event with {} id", id);
        eventService.delete(id);
    }
}
