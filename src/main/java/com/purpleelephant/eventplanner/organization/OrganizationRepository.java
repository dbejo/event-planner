package com.purpleelephant.eventplanner.organization;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrganizationRepository extends JpaRepository<Organization, Integer> {
    Optional<Organization> findByParent(Organization organization);
    Optional<Organization> findByName(String name);
}
