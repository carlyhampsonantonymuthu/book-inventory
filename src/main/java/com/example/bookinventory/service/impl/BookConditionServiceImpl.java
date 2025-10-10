package com.example.bookinventory.service.impl;

import com.example.bookinventory.entity.BookCondition;
import com.example.bookinventory.repository.BookConditionRepository;
import com.example.bookinventory.service.BookConditionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BookConditionServiceImpl implements BookConditionService {

    private final BookConditionRepository bookConditionRepository;
    public BookConditionServiceImpl(BookConditionRepository repo) { this.bookConditionRepository = repo; }

    @Override
    public BookCondition addBookCondition(BookCondition condition) {
        return bookConditionRepository.save(condition);
    }

    @Override
    public java.util.Optional<BookCondition> getBookConditionByRank(Integer ranks) {
        return bookConditionRepository.findById(ranks);
    }

    @Override
    public BookCondition updatePrice(Integer ranks, Double price) {
        BookCondition bc = bookConditionRepository.findById(ranks)
                .orElseThrow(() -> new RuntimeException("Condition not found"));
        bc.setPrice(price);
        return bookConditionRepository.save(bc);
    }

    @Override
    public BookCondition updateDescription(Integer ranks, String desc) {
        BookCondition bc = bookConditionRepository.findById(ranks)
                .orElseThrow(() -> new RuntimeException("Condition not found"));
        bc.setDescription(desc);
        return bookConditionRepository.save(bc);
    }

    @Override
    public BookCondition updateFullDescription(Integer ranks, String fullDesc) {
        BookCondition bc = bookConditionRepository.findById(ranks)
                .orElseThrow(() -> new RuntimeException("Condition not found"));
        bc.setFullDescription(fullDesc);
        return bookConditionRepository.save(bc);
    }
}
