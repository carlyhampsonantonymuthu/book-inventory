package com.example.bookinventory.repository;

import com.example.bookinventory.entity.Reviewer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewerRepository extends JpaRepository<Reviewer, Integer> {
    boolean existsByName(String name);
}