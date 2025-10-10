package com.example.bookinventory.service.impl;

import com.example.bookinventory.entity.BookReview;
import com.example.bookinventory.entity.BookReviewId;
import com.example.bookinventory.repository.BookRepository;
import com.example.bookinventory.repository.BookReviewRepository;
import com.example.bookinventory.repository.ReviewerRepository;
import com.example.bookinventory.service.BookReviewService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class BookReviewServiceImpl implements BookReviewService {

    private final BookReviewRepository bookReviewRepository;
    private final BookRepository bookRepository;
    private final ReviewerRepository reviewerRepository;

    public BookReviewServiceImpl(BookReviewRepository bookReviewRepository,
                                 BookRepository bookRepository,
                                 ReviewerRepository reviewerRepository) {
        this.bookReviewRepository = bookReviewRepository;
        this.bookRepository = bookRepository;
        this.reviewerRepository = reviewerRepository;
    }

    @Override
    public BookReview addBookReview(BookReview review) {
        // Validate book exists
        if (review.getBook() == null || review.getBook().getIsbn() == null)
            throw new IllegalArgumentException("Book ISBN is required");

        // Validate reviewer exists
        if (review.getReviewer() == null || review.getReviewer().getReviewerId() == null)
            throw new IllegalArgumentException("Reviewer ID is required");

        // Check if already exists
        BookReviewId id = new BookReviewId(
                review.getBook().getIsbn(),
                review.getReviewer().getReviewerId()
        );
        if (bookReviewRepository.existsById(id))
            throw new RuntimeException("Review already exists for this reviewer and book");

        // Save review
        return bookReviewRepository.save(review);
    }

    @Override
    public List<BookReview> getBookReviewsByIsbn(String isbn) {
        List<BookReview> reviews = bookReviewRepository.findByBook_Isbn(isbn);
        if (reviews.isEmpty()) {
            throw new RuntimeException("No reviews found for ISBN: " + isbn);
        }
        return reviews;
    }

    @Override
    public BookReview updateRating(String isbn, Integer reviewerId, Double rating) {
        BookReviewId id = new BookReviewId(isbn, reviewerId);
        BookReview review = bookReviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found for given ISBN and reviewer"));

        review.setRating(rating);
        return bookReviewRepository.save(review);
    }

    @Override
    public BookReview updateComments(String isbn, Integer reviewerId, String comments) {
        BookReviewId id = new BookReviewId(isbn, reviewerId);
        BookReview review = bookReviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found for given ISBN and reviewer"));

        review.setComments(comments);
        return bookReviewRepository.save(review);
    }
}
