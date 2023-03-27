package com.purpleelephant.eventplanner.organization;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.purpleelephant.eventplanner.person.Person;
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
public class Organization {

    @Id
    @GeneratedValue
    private Integer id;
    private Boolean topLevel;
    private String name;

    private Boolean active;
    private String address;
    @JsonManagedReference
    @ManyToMany(mappedBy = "organizations", fetch = FetchType.LAZY)
    private Collection<Person> people;

    @ManyToOne(fetch = FetchType.LAZY)
    private Organization parent;
}
