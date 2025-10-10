package com.example.bookinventory.service;

import com.example.bookinventory.entity.BookCondition;

import java.util.Optional;

public interface BookConditionService {
    BookCondition addBookCondition(BookCondition condition);
    Optional<BookCondition> getBookConditionByRank(Integer ranks);
    BookCondition updatePrice(Integer ranks, Double price);
    BookCondition updateDescription(Integer ranks, String desc);
    BookCondition updateFullDescription(Integer ranks, String fullDesc);
}
