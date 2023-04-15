package com.purpleelephant.eventplanner.organization.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.purpleelephant.eventplanner.organization.Organization;
import com.purpleelephant.eventplanner.person.Person;
import lombok.Data;

import java.util.List;

@Data
public class AddModifyOrgRequest {
    private Integer id;
    private String name;
    private Integer parentId;
    private Boolean topLevel;
    private String address;
    private Boolean active;
    @JsonIgnoreProperties({"people", "parent"})
    private Organization parentOrg;
    @JsonIgnoreProperties({"organizations", "events"})
    private List<Person> people;
}
