package com.example.bookinventory.service;

import com.example.bookinventory.entity.Book;
import java.util.List;
import java.util.Optional;

public interface BookService {
    Book addBook(Book book);
    List<Book> getAllBooks();
    Optional<Book> getBookByIsbn(String isbn);
    Optional<Book> getBookByTitle(String title);
    List<Book> getBooksByCategory(String categoryName);
    List<Book> getBooksByPublisher(Integer publisherId);

    Book updateBookTitle(String isbn, String title);
    Book updateBookDescription(String isbn, String desc);
    Book updateBookCategory(String isbn, String categoryName);
    Book updateBookEdition(String isbn, String edition);
    Book updateBookPublisher(String isbn, Integer publisherId);
}

