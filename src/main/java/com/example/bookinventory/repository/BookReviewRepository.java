package com.example.bookinventory.repository;


import com.example.bookinventory.entity.BookReview;
import com.example.bookinventory.entity.BookReviewId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookReviewRepository extends JpaRepository<BookReview, BookReviewId> {
    List<BookReview> findByBook_Isbn(String isbn);
}