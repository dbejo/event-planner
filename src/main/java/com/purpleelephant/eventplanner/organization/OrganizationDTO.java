package com.purpleelephant.eventplanner.organization;

import com.purpleelephant.eventplanner.person.Person;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationDTO {
    private Integer id;
    private Boolean topLevel;
    private String name;
    private Boolean active;
    private String address;
    private Integer parentId;
    private List<Integer> peopleIds;

    public static OrganizationDTO fromEntity(Organization organization) {
        OrganizationDTO dto = new OrganizationDTO();
        dto.setId(organization.getId());
        dto.setTopLevel(organization.getTopLevel());
        dto.setName(organization.getName());
        dto.setActive(organization.getActive());
        dto.setAddress(organization.getAddress());
        if (organization.getParent() != null) {
            dto.setParentId(organization.getParent().getId());
        }
        if (organization.getPeople() != null) {
            dto.setPeopleIds(organization.getPeople().stream()
                    .map(Person::getId)
                    .collect(Collectors.toList()));
        }
        return dto;
    }

    public Organization toEntity() {
        Organization organization = new Organization();
        organization.setId(id);
        organization.setTopLevel(topLevel);
        organization.setName(name);
        organization.setActive(active);
        organization.setAddress(address);
        if (parentId != null) {
            Organization parent = new Organization();
            parent.setId(parentId);
            organization.setParent(parent);
        }
        if (peopleIds != null) {
            organization.setPeople(peopleIds.stream()
                    .map(id -> {
                        Person person = new Person();
                        person.setId(id);
                        return person;
                    })
                    .collect(Collectors.toList()));
        }
        return organization;
    }

}
