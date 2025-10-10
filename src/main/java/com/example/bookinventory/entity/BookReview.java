package com.example.bookinventory.entity;



import jakarta.persistence.*;

@Entity
@Table(name = "book_review")
public class BookReview {

    @EmbeddedId
    private BookReviewId id;

    @ManyToOne
    @MapsId("isbn")
    @JoinColumn(name = "isbn")
    private Book book;

    @ManyToOne
    @MapsId("reviewerId")
    @JoinColumn(name = "reviewer_id")
    private Reviewer reviewer;

    private Double rating;

    private String comments;

    // Getters and Setters
    public BookReviewId getId() { return id; }
    public void setId(BookReviewId id) { this.id = id; }

    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }

    public Reviewer getReviewer() { return reviewer; }
    public void setReviewer(Reviewer reviewer) { this.reviewer = reviewer; }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }


    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }
}

