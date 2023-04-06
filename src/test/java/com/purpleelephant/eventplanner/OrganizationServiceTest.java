package com.purpleelephant.eventplanner;

import com.purpleelephant.eventplanner.organization.Organization;
import com.purpleelephant.eventplanner.organization.OrganizationRepository;
import com.purpleelephant.eventplanner.organization.OrganizationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class OrganizationServiceTest {

    private OrganizationService organizationService;

    @Mock
    private OrganizationRepository organizationRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        organizationService = new OrganizationService(organizationRepository);
    }

    @Test
    public void create_shouldSetTopLevelIfParentIsNull() {
        Organization organization = new Organization();
        organization.setName("Test Organization");
        organization.setParent(null);

        when(organizationRepository.save(any(Organization.class)))
                .thenAnswer(invocation -> {
                    Organization org = invocation.getArgument(0);
                    org.setId(1);
                    return org;
                });

        Organization result = organizationService.create(organization);

        assertEquals(1, result.getId());
        assertEquals("Test Organization", result.getName());
        assertEquals(true, result.getTopLevel());
        assertEquals(null, result.getParent());

        verify(organizationRepository, times(1)).save(any(Organization.class));
    }

    @Test
    public void create_shouldSetParentNullIfTopLevel() {
        Organization organization = new Organization();
        organization.setName("Test Organization");
        organization.setTopLevel(true);

        when(organizationRepository.save(any(Organization.class)))
                .thenAnswer(invocation -> {
                    Organization org = invocation.getArgument(0);
                    org.setId(1);
                    return org;
                });

        Organization result = organizationService.create(organization);

        assertEquals(1, result.getId());
        assertEquals("Test Organization", result.getName());
        assertEquals(true, result.getTopLevel());
        assertEquals(null, result.getParent());

        verify(organizationRepository, times(1)).save(any(Organization.class));
    }


    @Test
    public void modify_shouldSaveOrganization() throws Exception {
        Organization organization = new Organization();
        organization.setId(1);
        organization.setName("Test Organization");

        when(organizationRepository.save(any(Organization.class)))
                .thenReturn(organization);

        Organization result = organizationService.modify(organization);

        assertEquals(1, result.getId());
        assertEquals("Test Organization", result.getName());

        verify(organizationRepository, times(1)).save(any(Organization.class));
    }

    @Test
    public void findById_shouldReturnOptionalEmptyIfOrganizationNotFound() {
        when(organizationRepository.findById(1))
                .thenReturn(Optional.empty());

        Optional<Organization> result = organizationService.findById(1);

        assertEquals(Optional.empty(), result);
    }

    @Test
    public void findById_shouldReturnOptionalWithOrganization() {
        Organization organization = new Organization();
        organization.setId(1);
        organization.setName("Test Organization");

        when(organizationRepository.findById(1))
                .thenReturn(Optional.of(organization));

        Optional<Organization> result = organizationService.findById(1);

        assertEquals(Optional.of(organization), result);
    }

    @Test
    public void delete_shouldDeleteOrganization() {
        Organization organization = new Organization();
        organization.setId(1);
        organization.setName("Test Organization");

        when(organizationRepository.findById(1))
                .thenReturn(Optional.of(organization));

        doNothing().when(organizationRepository).deleteById(1);

        organizationService.delete(1);

        verify(organizationRepository, times(1)).findById(1);
        verify(organizationRepository, times(1)).deleteById(1);
    }
}
