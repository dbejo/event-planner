package com.purpleelephant.eventplanner.organization;

import com.purpleelephant.eventplanner.person.Person;
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
public class Organization {

    @Id
    @GeneratedValue
    private Integer id;
    private Boolean topLevel;
    private String name;

    private Boolean active;
    private String address;
    @ManyToMany(mappedBy = "organizations")
    private Set<Person> people;
    @ManyToOne
    private Organization parent;
}
