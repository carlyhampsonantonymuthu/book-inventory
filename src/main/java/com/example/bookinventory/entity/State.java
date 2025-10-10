package com.example.bookinventory.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "state_tbl")
public class State {

    @Id
    @Column(length = 2, nullable = false)
    private String stateCode;

    @Column(nullable = false)
    private String stateName;

    @JsonIgnore
    @OneToMany(mappedBy = "state", cascade = CascadeType.ALL)
    private Set<Publisher> publishers = new HashSet<>();

    // Getters and Setters
    public String getStateCode() { return stateCode; }
    public void setStateCode(String stateCode) { this.stateCode = stateCode; }

    public String getStateName() { return stateName; }
    public void setStateName(String stateName) { this.stateName = stateName; }

    public Set<Publisher> getPublishers() { return publishers; }
    public void setPublishers(Set<Publisher> publishers) { this.publishers = publishers; }
}
