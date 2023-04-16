package com.purpleelephant.eventplanner.person;

import com.purpleelephant.eventplanner.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/person")
@RequiredArgsConstructor
public class PersonController {
    private final PersonService personService;
    private final PersonRepository personRepository;

    @Operation(
            summary = "Get all people",
            tags = {"person"}
    )
    @ApiResponse(responseCode = "200", description = "Get all people")
    @GetMapping("/all")
    public Response getAll() {
        return Response.builder().timeStamp(LocalDateTime.now()).data(Map.of("people", personRepository.findAll())).build();
    }

    @Operation(
            summary = "Add a person",
            tags = {"person"}
    )
    @PostMapping
    public Response add(@RequestBody Person person) {
        return Response.builder().timeStamp(LocalDateTime.now()).data(Map.of("person", personService.create(person))).build();
    }

    @Operation(
            summary = "Modify a person",
            tags = {"person"}
    )
    @PutMapping
    public Response modify(@RequestBody Person person) {
        return Response.builder().timeStamp(LocalDateTime.now()).data(Map.of("person", personService.modify(person))).build();
    }

    @Operation(
            summary = "Delete a person",
            tags = {"person"}
    )
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        personService.delete(id);
    }
}
