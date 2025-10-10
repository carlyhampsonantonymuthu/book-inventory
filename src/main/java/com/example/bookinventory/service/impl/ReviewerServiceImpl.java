package com.example.bookinventory.service.impl;


import com.example.bookinventory.entity.Reviewer;
import com.example.bookinventory.repository.ReviewerRepository;
import com.example.bookinventory.service.ReviewerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReviewerServiceImpl implements ReviewerService {

    private final ReviewerRepository reviewerRepository;
    public ReviewerServiceImpl(ReviewerRepository reviewerRepository) { this.reviewerRepository = reviewerRepository; }

    @Override
    public Reviewer addReviewer(Reviewer reviewer) {
        if (reviewerRepository.existsByName(reviewer.getName())) {
            throw new RuntimeException("Reviewer already exists");
        }
        return reviewerRepository.save(reviewer);
    }

    @Override
    public java.util.Optional<Reviewer> getReviewerById(Integer reviewerId) {
        return reviewerRepository.findById(reviewerId);
    }

    @Override
    public Reviewer updateName(Integer reviewerId, String name) {
        Reviewer r = reviewerRepository.findById(reviewerId)
                .orElseThrow(() -> new RuntimeException("Reviewer not found"));
        r.setName(name);
        return reviewerRepository.save(r);
    }

    @Override
    public Reviewer updateEmployedBy(Integer reviewerId, String employedBy) {
        Reviewer r = reviewerRepository.findById(reviewerId)
                .orElseThrow(() -> new RuntimeException("Reviewer not found"));
        r.setEmployedBy(employedBy);
        return reviewerRepository.save(r);
    }
}
