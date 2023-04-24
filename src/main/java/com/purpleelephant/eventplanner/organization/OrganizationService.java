package com.purpleelephant.eventplanner.organization;

import com.purpleelephant.eventplanner.organization.model.AddModifyOrgRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrganizationService {
    private final OrganizationRepository organizationRepository;

    public Organization create(AddModifyOrgRequest request) {
        Organization org = new Organization(null, request.getTopLevel(), request.getName(), request.getActive(), request.getAddress(), request.getPeople(), new Organization(request.getParentOrg().getId()));
        if (org.getParent() == null) {
            org.setTopLevel(true);
        }
        if (org.getTopLevel()) {
            org.setParent(null);
        }
        log.info("Create new {} organization", request.getName());
        return organizationRepository.save(org);
    }

    public Organization modify(AddModifyOrgRequest request) {
        Organization parentOrg;
        if (request.getParentOrg().getId() == null) {
            request.setTopLevel(true);
            parentOrg = null;
        } else {
            request.setTopLevel(false);
            parentOrg = organizationRepository.findById(request.getParentOrg().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Parent organization not found"));
        }
        Organization org = new Organization(request.getId(), request.getTopLevel(), request.getName(), request.getActive(), request.getAddress(), request.getPeople(), parentOrg);
        log.info("Modify {} organization", request.getName());
        return organizationRepository.save(org);
    }

    public void delete(int id) {
        Organization organization = organizationRepository.findById(id).orElseThrow();
        if (organization.getPeople().size() > 0) {
            throw new RuntimeException("Cannot delete organization because it has people assigned to it");
        }
        organizationRepository.deleteById(organization.getId());
        log.info("{} organization deleted", organization.getName());
    }

    public Collection<Organization> getByPerson(Integer id) {
        return organizationRepository.findAll().stream().filter(organization -> organization.getPeople().stream().anyMatch(person -> person.getId().equals(id))).collect(Collectors.toList());
    }
}
