package com.example.bookinventory.entity;



import jakarta.persistence.*;

@Entity
@Table(name = "book_condition")
public class BookCondition {

    @Id
    private Integer ranks;

    private String description;
    private String fullDescription;
    private Double price;

    // Getters and Setters
    public Integer getRanks() { return ranks; }
    public void setRanks(Integer ranks) { this.ranks = ranks; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getFullDescription() { return fullDescription; }
    public void setFullDescription(String fullDescription) { this.fullDescription = fullDescription; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
}
