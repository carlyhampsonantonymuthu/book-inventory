package com.example.bookinventory.service;

import com.example.bookinventory.entity.Reviewer;

import java.util.Optional;

public interface ReviewerService {
    Reviewer addReviewer(Reviewer reviewer);
    Optional<Reviewer> getReviewerById(Integer reviewerId);
    Reviewer updateName(Integer reviewerId, String name);
    Reviewer updateEmployedBy(Integer reviewerId, String employedBy);
}