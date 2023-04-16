package com.purpleelephant.eventplanner.organization;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    public Organization(Integer id) {
        this.id = id;
    }

    @JsonIgnoreProperties({"organizations", "events"})
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable
    private Collection<Person> people;

    @JsonIgnoreProperties("people")
    @ManyToOne(fetch = FetchType.EAGER)
    private Organization parent;
}
