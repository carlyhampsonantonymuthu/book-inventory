package com.example.bookinventory.entity;



import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "reviewer")
public class Reviewer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reviewerId;

    private String name;
    private String employedBy;

    @JsonIgnore
    @OneToMany(mappedBy = "reviewer", cascade = CascadeType.ALL)
    private Set<BookReview> reviews = new HashSet<>();

    // Getters and Setters
    public Integer getReviewerId() { return reviewerId; }
    public void setReviewerId(Integer reviewerId) { this.reviewerId = reviewerId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmployedBy() { return employedBy; }
    public void setEmployedBy(String employedBy) { this.employedBy = employedBy; }

    public Set<BookReview> getReviews() { return reviews; }
    public void setReviews(Set<BookReview> reviews) { this.reviews = reviews; }
}
