package com.example.bookinventory.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "book")
public class Book {

    @Id
    private String isbn;

    private String title;
    private String description;
    private String edition;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BookAuthor> bookAuthors = new HashSet<>();

    // Constructors
    public Book() {}
    public Book(String isbn, String title) {
        this.isbn = isbn;
        this.title = title;
    }

    // Getters and Setters
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getEdition() { return edition; }
    public void setEdition(String edition) { this.edition = edition; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public Publisher getPublisher() { return publisher; }
    public void setPublisher(Publisher publisher) { this.publisher = publisher; }

    public Set<BookAuthor> getBookAuthors() { return bookAuthors; }
    public void setBookAuthors(Set<BookAuthor> bookAuthors) { this.bookAuthors = bookAuthors; }

    // Helper methods
    public void addAuthor(Author author, boolean primary) {
        BookAuthor bookAuthor = new BookAuthor(this, author, primary);
        bookAuthors.add(bookAuthor);
        author.getBookAuthors().add(bookAuthor);
    }

    public void removeAuthor(Author author) {
        bookAuthors.removeIf(ba -> ba.getAuthor().equals(author) && ba.getBook().equals(this));
        author.getBookAuthors().removeIf(ba -> ba.getAuthor().equals(author) && ba.getBook().equals(this));
    }
}
