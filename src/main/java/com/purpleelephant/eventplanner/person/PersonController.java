package com.purpleelephant.eventplanner.person;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

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
    public ResponseEntity<Collection<Person>> getAll() {
        return ResponseEntity.ok(personRepository.findAll());
    }

    @Operation(
            summary = "Get a person by ID",
            tags = {"person"}
    )
    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable String id) {
        return ResponseEntity.ok(personService.findById(id).orElseThrow());
    }

    @Operation(
            summary = "Get a person by full name",
            tags = {"person"}
    )
    @GetMapping
    public ResponseEntity<Collection<Person>> getPersonByFullName(@RequestBody Person person) {
        if (person.getFirstName().isEmpty() || person.getLastName().isEmpty()) {
            return ResponseEntity.badRequest().body(List.of(person));
        }
        return ResponseEntity.ok(personService.findByFullName(person.getFirstName(), person.getLastName()));
    }

    @Operation(
            summary = "Get all people by organization ID",
            tags = {"person"}
    )
    @GetMapping("/organization/{id}")
    public ResponseEntity<Collection<Person>> getPeopleByOrganization(@PathVariable String id) {
        return ResponseEntity.ok(personService.findByOrganizationId(id));
    }

    @Operation(
            summary = "Add a person",
            tags = {"person"}
    )
    @PostMapping
    public ResponseEntity<Person> add(@RequestBody Person person) {
        return ResponseEntity.ok(personService.create(person));
    }

    @Operation(
            summary = "Modify a person",
            tags = {"person"}
    )
    @PutMapping
    public ResponseEntity<Person> modify(@RequestBody Person person) {
        try {
            return ResponseEntity.ok(personService.modify(person));
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.badRequest().body(person);
        }
    }

    @Operation(
            summary = "Delete a person",
            tags = {"person"}
    )
    @DeleteMapping
    public ResponseEntity<Person> delete(@RequestBody Person person) {
        personRepository.deleteById(person.getId());
        return ResponseEntity.ok(person);
    }
}
