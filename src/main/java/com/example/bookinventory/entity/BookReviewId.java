package com.example.bookinventory.entity;


import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BookReviewId implements Serializable {
    private String isbn;
    private Integer reviewerId;

    public BookReviewId() {}
    public BookReviewId(String isbn, Integer reviewerId) {
        this.isbn = isbn;
        this.reviewerId = reviewerId;
    }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public Integer getReviewerId() { return reviewerId; }
    public void setReviewerId(Integer reviewerId) { this.reviewerId = reviewerId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookReviewId)) return false;
        BookReviewId that = (BookReviewId) o;
        return Objects.equals(isbn, that.isbn) &&
                Objects.equals(reviewerId, that.reviewerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn, reviewerId);
    }
}
