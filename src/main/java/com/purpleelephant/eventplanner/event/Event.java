package com.purpleelephant.eventplanner.event;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.purpleelephant.eventplanner.person.Person;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Event {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String description;
    private String location;
    private Date date;
    private Boolean active;

    @JsonManagedReference
    @ManyToMany(fetch = FetchType.LAZY)
    private Collection<Person> people;
}
