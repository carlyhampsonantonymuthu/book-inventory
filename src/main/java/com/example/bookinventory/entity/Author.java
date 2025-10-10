package com.example.bookinventory.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer authorId;

    private String firstName;
    private String lastName;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BookAuthor> bookAuthors = new HashSet<>();

    // Constructors
    public Author() {}

    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Getters and Setters
    public Integer getAuthorId() { return authorId; }
    public void setAuthorId(Integer authorId) { this.authorId = authorId; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public Set<BookAuthor> getBookAuthors() { return bookAuthors; }
    public void setBookAuthors(Set<BookAuthor> bookAuthors) { this.bookAuthors = bookAuthors; }

    // Helper methods to manage bidirectional relationship
    public void addBook(Book book, boolean primary) {
        BookAuthor bookAuthor = new BookAuthor(book, this, primary);
        bookAuthors.add(bookAuthor);
        book.getBookAuthors().add(bookAuthor);
    }

    public void removeBook(Book book) {
        bookAuthors.removeIf(ba -> ba.getBook().equals(book) && ba.getAuthor().equals(this));
        book.getBookAuthors().removeIf(ba -> ba.getBook().equals(book) && ba.getAuthor().equals(this));
    }
}

