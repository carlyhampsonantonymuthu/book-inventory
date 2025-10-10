package com.example.bookinventory.service;



import com.example.bookinventory.entity.BookReview;
import java.util.List;

public interface BookReviewService {
    BookReview addBookReview(BookReview review);
    List<BookReview> getBookReviewsByIsbn(String isbn);
    BookReview updateRating(String isbn, Integer reviewerId, Double rating);
    BookReview updateComments(String isbn, Integer reviewerId, String comments);
}
