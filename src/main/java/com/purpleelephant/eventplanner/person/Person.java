package com.purpleelephant.eventplanner.person;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.purpleelephant.eventplanner.event.Event;
import com.purpleelephant.eventplanner.organization.Organization;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

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

    @JsonBackReference
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable
    private Collection<Organization> organizations;

    @JsonBackReference
    @ManyToMany(mappedBy = "people", fetch = FetchType.LAZY)
    private Collection<Event> events;
}
