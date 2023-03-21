package com.purpleelephant.eventplanner.organization;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        return organizationRepository.save(organization);
    }

    public Organization modify(Organization organization) throws Exception {
        if (organization.getId() == null) {
            throw new Exception("Set organization id for modification");
        }
        return organizationRepository.save(organization);
    }

    public Optional<Organization> findById(String id) {
        return organizationRepository.findById(Integer.valueOf(id));
    }
}
