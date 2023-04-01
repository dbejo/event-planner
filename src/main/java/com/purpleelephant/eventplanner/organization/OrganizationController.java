package com.purpleelephant.eventplanner.organization;

import com.purpleelephant.eventplanner.Response;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/organization")
@RequiredArgsConstructor
public class OrganizationController {
    private final OrganizationService organizationService;
    private final OrganizationRepository organizationRepository;

    @Operation(
            summary = "Get all organization",
            tags = {"organization"}
    )
    @GetMapping("/all")
    public ResponseEntity<Response> getAll() {
        return ResponseEntity.ok(Response.builder().timeStamp(LocalDateTime.now()).data(Map.of("organizations", organizationRepository.findAll())).build());
    }

    @Operation(
            summary = "Get an organization by ID",
            tags = {"organization"}
    )
    @GetMapping("/{id}")
    public ResponseEntity<Organization> getOrganizationById(@PathVariable Integer id) {
        //TODO
        return ResponseEntity.ok(organizationService.findById(id).orElseThrow());
    }

    @Operation(
            summary = "Add an organization",
            tags = {"organization"}
    )
    @PostMapping
    public ResponseEntity<Organization> add(@RequestBody Organization organization) {
        //TODO
        return ResponseEntity.ok(organizationService.create(organization));
    }

    @Operation(
            summary = "Modify an organization",
            tags = {"organization"}
    )
    @PutMapping
    public ResponseEntity<Organization> modify(@RequestBody Organization organization) {
        //TODO
        try {
            return ResponseEntity.ok(organizationService.modify(organization));
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.badRequest().body(organization);
        }
    }

    @Operation(
            summary = "Delete an organization",
            tags = {"organization"}
    )
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        organizationService.delete(id);
    }
}
