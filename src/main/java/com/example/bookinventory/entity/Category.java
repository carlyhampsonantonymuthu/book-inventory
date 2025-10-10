package com.example.bookinventory.entity;



import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer catId;

    private String catDescription;

    @JsonIgnore
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private Set<Book> books = new HashSet<>();

    // Getters and Setters
    public Integer getCatId() { return catId; }
    public void setCatId(Integer catId) { this.catId = catId; }

    public String getCatDescription() { return catDescription; }
    public void setCatDescription(String catDescription) { this.catDescription = catDescription; }

    public Set<Book> getBooks() { return books; }
    public void setBooks(Set<Book> books) { this.books = books; }
}
