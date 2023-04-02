package com.purpleelephant.eventplanner.organization;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrganizationService {
    private final OrganizationRepository organizationRepository;

    public Organization create(Organization organization) {
        organization.setId(null);
        if (organization.getParent() == null) {
            organization.setTopLevel(true);
        }
        if (organization.getTopLevel()) {
            organization.setParent(null);
        }
        log.info("Create new {} organization", organization.getName());
        return organizationRepository.save(organization);
    }

    public Organization modify(Organization organization) throws Exception {
        if (organization.getId() == null) {
            log.info("Event id missing");
            throw new Exception("Set organization id for modification");
        }
        log.info("Modify {} organization", organization.getName());
        return organizationRepository.save(organization);
    }

    public Optional<Organization> findById(Integer id) {
        return organizationRepository.findById(id);
    }

    public void delete(int id) {
        Organization organization = organizationRepository.findById(id).orElseThrow();
        organizationRepository.deleteById(organization.getId());
        log.info("{} organization deleted", organization.getName());
    }
}
