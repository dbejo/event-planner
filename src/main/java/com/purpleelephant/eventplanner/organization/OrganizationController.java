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

    @GetMapping("/{id}")
    public ResponseEntity<Response> get(@PathVariable Integer id) {
        OrganizationDTO organizationDTO = organizationService.getOrganizationById(id);
        return ResponseEntity.ok(Response.builder().timeStamp(LocalDateTime.now())
                .data(Map.of("organization", organizationDTO)).build());
    }

    @Operation(
            summary = "Add an organization",
            tags = {"organization"}
    )
    @PostMapping
    public ResponseEntity<Response> add(@RequestBody Organization organization) {
        return ResponseEntity.ok(Response.builder().timeStamp(LocalDateTime.now()).data(Map.of("organization", organizationService.create(organization))).build());
    }

    @Operation(
            summary = "Modify an organization",
            tags = {"organization"}
    )
    @PutMapping
    public ResponseEntity<Response> modify(@RequestBody Organization organization) {
        return ResponseEntity.ok(Response.builder().timeStamp(LocalDateTime.now()).data(Map.of("organization", organizationService.modify(organization))).build());
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
