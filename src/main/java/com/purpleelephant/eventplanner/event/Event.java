package com.purpleelephant.eventplanner.event;

import com.purpleelephant.eventplanner.person.Person;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.Set;

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
    @ManyToMany
    @JoinTable(name = "event_to_person", joinColumns = @JoinColumn(name = "event_id"), inverseJoinColumns = @JoinColumn(name = "person_id"))
    private Set<Person> people;
}
