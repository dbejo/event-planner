package com.purpleelephant.eventplanner.organization;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1/organization")
@RequiredArgsConstructor
public class OrganizationController {
    private final OrganizationService organizationService;
    private final OrganizationRepository organizationRepository;

    @GetMapping("/all")
    public ResponseEntity<Collection<Organization>> getAll() {
        return ResponseEntity.ok(organizationRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Organization> getOrganizationById(@PathVariable String id) {

        return ResponseEntity.ok(organizationService.findById(id).orElseThrow());
    }

    @PostMapping
    public ResponseEntity<Organization> add(@RequestBody Organization organization) {
        return ResponseEntity.ok(organizationService.create(organization));
    }

    @PutMapping
    public ResponseEntity<Organization> modify(@RequestBody Organization organization) throws Exception {
        return ResponseEntity.ok(organizationService.modify(organization));
    }

    @DeleteMapping
    public ResponseEntity<Organization> delete(@RequestBody Organization organization) {
        organizationRepository.deleteById(organization.getId());
        return ResponseEntity.ok(organization);
    }
}
