package com.purpleelephant.eventplanner.person;

import com.purpleelephant.eventplanner.organization.Organization;
import com.purpleelephant.eventplanner.organization.OrganizationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;
    private final OrganizationService organizationService;

    public Person create(Person person) {
        log.info("Create new {} {} person", person.getFirstName(), person.getLastName());
        return personRepository.save(new Person(null, person.getFirstName(), person.getLastName(), person.getNotes(), person.getPersonalEmail(), person.getActive(), person.getOrganizations(), person.getEvents()));
    }

    public Collection<Person> findByFullName(String firstName, String lastName) {
        return personRepository.findByFirstName(firstName).orElseThrow().stream().filter(res -> res.getLastName().equals(lastName)).toList();
    }

    public Collection<Person> findByOrganizationId(String organizationId) {
        Organization organization = organizationService.findById(organizationId).orElseThrow();
        return personRepository.findByOrganizations(organization).orElseThrow();
    }

    public Optional<Person> findById(String id) {
        return personRepository.findById(Integer.valueOf(id));
    }

    public Person modify(Person person) throws Exception {
        if (person.getId() == null) {
            log.info("Person id missing");
            throw new Exception("Set person id for modification");
        }
        log.info("Modify {} {} person", person.getFirstName(), person.getLastName());
        return personRepository.save(person);
    }
}
