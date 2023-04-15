package com.purpleelephant.eventplanner.event;

import com.purpleelephant.eventplanner.Response;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public Response getAll() {
        return Response.builder().timeStamp(LocalDateTime.now()).data(Map.of("events", eventService.getEvents())).build();
    }

    @Operation(
            summary = "Add an event",
            tags = {"event"}
    )
    @PostMapping
    public Response add(@RequestBody Event event) {
        return Response.builder().timeStamp(LocalDateTime.now()).data(Map.of("event", eventService.create(event))).build();
    }

    @Operation(
            summary = "Modify an event",
            tags = {"event"}
    )
    @PutMapping
    public Response modify(@RequestBody Event event) {
        return Response.builder().timeStamp(LocalDateTime.now()).data(Map.of("event", eventService.modify(event))).build();
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
