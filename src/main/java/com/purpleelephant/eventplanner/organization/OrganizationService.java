package com.purpleelephant.eventplanner.organization;

import com.purpleelephant.eventplanner.person.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public OrganizationDTO getOrganizationById(Integer id) {
        Organization organization = organizationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Organization not found"));
        List<Integer> peopleIds = new ArrayList<>();
        if (organization.getPeople() != null) {
            peopleIds = organization.getPeople().stream().map(Person::getId).collect(Collectors.toList());
        }
        Integer parentId = null;
        if (organization.getParent() != null) {
            parentId = organization.getParent().getId();
        }
        return OrganizationDTO.builder()
                .id(organization.getId())
                .name(organization.getName())
                .parentId(parentId)
                .topLevel(organization.getTopLevel())
                .address(organization.getAddress())
                .peopleIds(peopleIds)
                .active(organization.getActive())
                .build();
    }

    public Organization modify(Organization organization) {
        log.info("Modify {} organization", organization.getName());
        return organizationRepository.save(organization);
    }

    public void delete(int id) {
        Organization organization = organizationRepository.findById(id).orElseThrow();
        organizationRepository.deleteById(organization.getId());
        log.info("{} organization deleted", organization.getName());
    }
}
