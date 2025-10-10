package com.example.bookinventory.service;

import com.example.bookinventory.entity.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    Author addAuthor(Author author);
    Optional<Author> getAuthorById(Integer authorId);
    Optional<Author> getAuthorByFirstName(String firstName);
    Optional<Author> getAuthorByLastName(String lastName);
    Author updateFirstName(Integer authorId, String newFirstName);
    Author updateLastName(Integer authorId, String newLastName);
    List<Author> getAllAuthors();
    List<?> getBooksByAuthor(Integer authorId);
}