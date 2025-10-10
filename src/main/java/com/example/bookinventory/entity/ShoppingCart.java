package com.example.bookinventory.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "shopping_cart")
public class ShoppingCart {

    @EmbeddedId
    private ShoppingCartId id;

    private Integer quantity = 1;
    private LocalDateTime addedAt = LocalDateTime.now();

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @MapsId("isbn")
    @JoinColumn(name = "isbn", nullable = false)
    private Book book;

    // Getters and Setters
    public ShoppingCartId getId() { return id; }
    public void setId(ShoppingCartId id) { this.id = id; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public LocalDateTime getAddedAt() { return addedAt; }
    public void setAddedAt(LocalDateTime addedAt) { this.addedAt = addedAt; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }
}
