package com.purpleelephant.eventplanner.person;

import com.purpleelephant.eventplanner.organization.Organization;
import com.purpleelephant.eventplanner.organization.OrganizationRepository;
import com.purpleelephant.eventplanner.organization.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;
    private final OrganizationService organizationService;

    public Person create(String firstName, String lastName, String notes, Organization organization) {
        Person person = new Person(null, firstName, lastName, notes, organization);
        return personRepository.save(person);
    }

    public Collection<Person> findByFullName(String firstName, String lastName) {
        return personRepository.findByFirstName(firstName).orElseThrow().stream().filter(res -> res.getLastName().equals(lastName)).toList();
    }

    public Collection<Person> findByOrganizationId(String organizationId) {
        Organization organization = organizationService.findById(organizationId).orElseThrow();
        return personRepository.findByOrganization(organization).orElseThrow();
    }

    public Optional<Person> findById(String id) {
        return personRepository.findById(Integer.valueOf(id));
    }

    public Person modify(Person person) throws Exception {
        if (person.getId() == null) {
            throw new Exception("Set person id for modification");
        }
        return personRepository.save(person);
    }
}
