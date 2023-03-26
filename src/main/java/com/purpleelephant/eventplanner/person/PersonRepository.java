package com.purpleelephant.eventplanner.person;

import com.purpleelephant.eventplanner.organization.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Integer> {
    Optional<Collection<Person>> findByFirstName(String firstName);
    Optional<Collection<Person>> findByOrganizations(Organization organization);
}
