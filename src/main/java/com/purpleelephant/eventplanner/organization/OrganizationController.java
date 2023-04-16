package com.purpleelephant.eventplanner.organization;

import com.purpleelephant.eventplanner.Response;
import com.purpleelephant.eventplanner.organization.model.AddModifyOrgRequest;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public Response getAll() {
        return Response.builder().timeStamp(LocalDateTime.now()).data(Map.of("organizations", organizationRepository.findAll())).build();
    }

    @Operation(
            summary = "Add an organization",
            tags = {"organization"}
    )
    @PostMapping
    public Response add(@RequestBody AddModifyOrgRequest request) {
        return Response.builder().timeStamp(LocalDateTime.now()).data(Map.of("organization", organizationService.create(request))).build();
    }

    @Operation(
            summary = "Modify an organization",
            tags = {"organization"}
    )
    @PutMapping
    public Response modify(@RequestBody AddModifyOrgRequest request) {
        return Response.builder().timeStamp(LocalDateTime.now()).data(Map.of("organization", organizationService.modify(request))).build();
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
