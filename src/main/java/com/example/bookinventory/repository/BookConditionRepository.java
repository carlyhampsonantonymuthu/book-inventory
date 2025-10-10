package com.example.bookinventory.repository;

import com.example.bookinventory.entity.BookCondition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookConditionRepository extends JpaRepository<BookCondition, Integer> { }