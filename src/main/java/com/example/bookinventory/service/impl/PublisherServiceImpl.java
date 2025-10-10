package com.example.bookinventory.service.impl;


import com.example.bookinventory.entity.Publisher;
import com.example.bookinventory.entity.State;
import com.example.bookinventory.repository.PublisherRepository;
import com.example.bookinventory.repository.StateRepository;
import com.example.bookinventory.service.PublisherService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
@Transactional
public class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository publisherRepository;
    private final StateRepository stateRepository;

    public PublisherServiceImpl(PublisherRepository publisherRepository, StateRepository stateRepository) {
        this.publisherRepository = publisherRepository;
        this.stateRepository = stateRepository;
    }

    @Override
    public Publisher addPublisher(Publisher publisher) {
        if (publisherRepository.existsByName(publisher.getName())) {
            throw new RuntimeException("Publisher already exists");
        }
        return publisherRepository.save(publisher);
    }

    @Override
    public List<Publisher> getAllPublishers() { return publisherRepository.findAll(); }

    @Override
    public Optional<Publisher> getPublisherById(Integer id) { return publisherRepository.findById(id); }

    @Override
    public Optional<Publisher> getPublisherByName(String name) { return publisherRepository.findByName(name); }

    @Override
    public List<Publisher> getPublishersByCity(String city) { return publisherRepository.findByCity(city); }

    @Override
    public List<Publisher> getPublishersByState(String stateName) {
        Optional<State> state = stateRepository.findAll().stream()
                .filter(s -> s.getStateName().equalsIgnoreCase(stateName))
                .findFirst();
        return state.map(publisherRepository::findByState).orElse(Collections.emptyList());
    }

    @Override
    public Publisher updatePublisherCity(Integer publisherId, String city) {
        Publisher p = publisherRepository.findById(publisherId)
                .orElseThrow(() -> new RuntimeException("Publisher not found"));
        p.setCity(city);
        return publisherRepository.save(p);
    }

    @Override
    public Publisher updatePublisherName(Integer publisherId, String name) {
        Publisher p = publisherRepository.findById(publisherId)
                .orElseThrow(() -> new RuntimeException("Publisher not found"));
        p.setName(name);
        return publisherRepository.save(p);
    }

    @Override
    public Publisher updatePublisherState(Integer publisherId, String stateName) {
        Publisher p = publisherRepository.findById(publisherId)
                .orElseThrow(() -> new RuntimeException("Publisher not found"));
        State state = stateRepository.findAll().stream()
                .filter(s -> s.getStateName().equalsIgnoreCase(stateName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("State not found"));
        p.setState(state);
        return publisherRepository.save(p);
    }
}

