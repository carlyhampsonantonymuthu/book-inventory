package com.example.bookinventory.repository;

import com.example.bookinventory.entity.BookAuthor;
import com.example.bookinventory.entity.BookAuthorId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookAuthorRepository extends JpaRepository<BookAuthor, BookAuthorId> {
    List<BookAuthor> findByBookIsbn(String isbn);
    List<BookAuthor> findByAuthorAuthorId(Integer authorId);
}
