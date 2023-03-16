package com.purpleelephant.eventplanner.person;

import com.purpleelephant.eventplanner.organization.Organization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/v1/person")
@RequiredArgsConstructor
public class PersonController {
    private final PersonService personService;
    private final PersonRepository personRepository;

    @GetMapping("/all")
    public ResponseEntity<Collection<Person>> getAll() {
        return ResponseEntity.ok(personRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable String id) {
        return ResponseEntity.ok(personService.findById(id).orElseThrow());
    }

    @GetMapping
    public ResponseEntity<Collection<Person>> getPersonByFullName(@RequestBody Person person) {
        if (person.getFirstName().isEmpty() || person.getLastName().isEmpty()) {
            return ResponseEntity.badRequest().body(List.of(person));
        }
        return ResponseEntity.ok(personService.findByFullName(person.getFirstName(), person.getLastName()));
    }

    @GetMapping("/organization/{id}")
    public ResponseEntity<Collection<Person>> getPeopleByOrganization(@PathVariable String id) {
        return ResponseEntity.ok(personService.findByOrganizationId(id));
    }

    @PostMapping
    public ResponseEntity<Person> add(@RequestBody Person person) {
        return ResponseEntity.ok(personService.create(person.getFirstName(), person.getLastName(), person.getNotes(), person.getOrganization()));
    }

    @PutMapping
    public ResponseEntity<Person> modify(@RequestBody Person person) throws Exception {
        return ResponseEntity.ok(personService.modify(person));
    }

    @DeleteMapping
    public ResponseEntity<Person> delete(@RequestBody Person person) {
        personRepository.deleteById(person.getId());
        return ResponseEntity.ok(person);
    }
}
