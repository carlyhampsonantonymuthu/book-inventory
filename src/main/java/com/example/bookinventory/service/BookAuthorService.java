package com.example.bookinventory.service;

import com.example.bookinventory.entity.BookAuthor;
import java.util.List;

public interface BookAuthorService {
    BookAuthor addBookAuthor(String isbn, Integer authorId, boolean primaryAuthor);
    List<BookAuthor> getAuthorsByBook(String isbn);
    List<BookAuthor> getBooksByAuthor(Integer authorId);
}
