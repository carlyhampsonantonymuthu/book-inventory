package com.example.bookinventory.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BookAuthorId implements Serializable {

    private String isbn;
    private Integer authorId;

    public BookAuthorId() {}

    public BookAuthorId(String isbn, Integer authorId) {
        this.isbn = isbn;
        this.authorId = authorId;
    }

    // Getters and Setters
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public Integer getAuthorId() { return authorId; }
    public void setAuthorId(Integer authorId) { this.authorId = authorId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookAuthorId)) return false;
        BookAuthorId that = (BookAuthorId) o;
        return Objects.equals(isbn, that.isbn) && Objects.equals(authorId, that.authorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn, authorId);
    }
}

