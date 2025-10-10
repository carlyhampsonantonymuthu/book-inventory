package com.example.bookinventory.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer inventoryId;

    private Integer purchased;

    @ManyToOne
    @JoinColumn(name = "isbn")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "ranks")
    private BookCondition condition;

    // Getters and Setters
    public Integer getInventoryId() { return inventoryId; }
    public void setInventoryId(Integer inventoryId) { this.inventoryId = inventoryId; }

    public Integer getPurchased() { return purchased; }
    public void setPurchased(Integer purchased) { this.purchased = purchased; }

    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }

    public BookCondition getCondition() { return condition; }
    public void setCondition(BookCondition condition) { this.condition = condition; }
}
