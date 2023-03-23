package com.purpleelephant.eventplanner.organization;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.models.annotations.OpenAPI30;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1/organization")
@RequiredArgsConstructor
public class OrganizationController {
    private final OrganizationService organizationService;
    private final OrganizationRepository organizationRepository;

    @Operation(
            summary = "Get all organization",
            tags = { "organization" },
            security = {@SecurityRequirement(name="BearerJWT")}
    )
    @GetMapping("/all")
    public ResponseEntity<Collection<Organization>> getAll() {
        return ResponseEntity.ok(organizationRepository.findAll());
    }

    @Operation(
        summary = "Get an organization by ID",
        tags = { "organization" },
        security = {@SecurityRequirement(name="BearerJWT")}
    )
    @GetMapping("/{id}")
    public ResponseEntity<Organization> getOrganizationById(@PathVariable String id) {

        return ResponseEntity.ok(organizationService.findById(id).orElseThrow());
    }

    @Operation(
            summary = "Add an organization",
            tags = { "organization" },
            security = {@SecurityRequirement(name="BearerJWT")}
    )
    @PostMapping
    public ResponseEntity<Organization> add(@RequestBody Organization organization) {
        return ResponseEntity.ok(organizationService.create(organization));
    }

    @Operation(
            summary = "Modify an organization",
            tags = { "organization" },
            security = {@SecurityRequirement(name="BearerJWT")}
    )
    @PutMapping
    public ResponseEntity<Organization> modify(@RequestBody Organization organization) throws Exception {
        return ResponseEntity.ok(organizationService.modify(organization));
    }

    @Operation(
        summary = "Delete an organization",
        tags = { "organization" },
        security = {@SecurityRequirement(name="BearerJWT")}
    )
    @DeleteMapping
    public ResponseEntity<Organization> delete(@RequestBody Organization organization) {
        organizationRepository.deleteById(organization.getId());
        return ResponseEntity.ok(organization);
    }
}
