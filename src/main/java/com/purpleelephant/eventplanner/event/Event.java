package com.purpleelephant.eventplanner.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.purpleelephant.eventplanner.person.Person;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
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
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean active;
    private String agenda;

    @JsonIgnoreProperties({"events", "organizations"})
    @ManyToMany(fetch = FetchType.LAZY)
    private Collection<Person> people;
}
