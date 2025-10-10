package com.example.bookinventory.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "book_author")
public class BookAuthor {

    @EmbeddedId
    private BookAuthorId id = new BookAuthorId();

    @ManyToOne
    @MapsId("isbn")
    @JoinColumn(name = "book_isbn")
    private Book book;

    @ManyToOne
    @MapsId("authorId")
    @JoinColumn(name = "author_id")
    private Author author;

    private boolean primaryAuthor;

    // Constructors
    public BookAuthor() {}

    public BookAuthor(Book book, Author author, boolean primaryAuthor) {
        this.book = book;
        this.author = author;
        this.primaryAuthor = primaryAuthor;
        this.id = new BookAuthorId(book.getIsbn(), author.getAuthorId());
    }

    // Getters and Setters
    public BookAuthorId getId() { return id; }
    public void setId(BookAuthorId id) { this.id = id; }

    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }

    public Author getAuthor() { return author; }
    public void setAuthor(Author author) { this.author = author; }

    public boolean isPrimaryAuthor() { return primaryAuthor; }
    public void setPrimaryAuthor(boolean primaryAuthor) { this.primaryAuthor = primaryAuthor; }
}


