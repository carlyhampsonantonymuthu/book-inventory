package com.example.bookinventory.service;

import com.example.bookinventory.entity.Publisher;

import java.util.List;
import java.util.Optional;

public interface PublisherService {
    Publisher addPublisher(Publisher publisher);
    List<Publisher> getAllPublishers();
    Optional<Publisher> getPublisherById(Integer id);
    Optional<Publisher> getPublisherByName(String name);
    List<Publisher> getPublishersByCity(String city);
    List<Publisher> getPublishersByState(String stateName);
    Publisher updatePublisherCity(Integer publisherId, String city);
    Publisher updatePublisherName(Integer publisherId, String name);
    Publisher updatePublisherState(Integer publisherId, String stateName);
}
