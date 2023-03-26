package com.purpleelephant.eventplanner.person;

import com.purpleelephant.eventplanner.event.Event;
import com.purpleelephant.eventplanner.organization.Organization;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Person {
    @Id
    @GeneratedValue
    private Integer id;
    private String firstName;
    private String lastName;
    private String notes;
    private String personalEmail;
    private Boolean active;
    @ManyToMany
    @JoinTable(name = "person_to_organization", joinColumns = @JoinColumn(name = "person_id"), inverseJoinColumns = @JoinColumn(name = "organization_id"))
    private Set<Organization> organizations;
    @ManyToMany(mappedBy = "people")
    private Set<Event> events;
}
