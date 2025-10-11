package com.example.bookinventory.controller;


import com.example.bookinventory.entity.Publisher;
import com.example.bookinventory.service.PublisherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Endpoints:
 * POST   /api/publisher/post
 * GET    /api/publishers
 * GET    /api/publisher/{publisherId}
 * GET    /api/publisher/name/{name}
 * GET    /api/publisher/city/{city}
 * GET    /api/publisher/state/{stateName}
 * PUT    /api/publisher/update/name/{publisherId}
 * PUT    /api/publisher/update/city/{publisherId}
 * PUT    /api/publisher/update/state/{publisherId}
 */


@RestController
@RequestMapping("/api/publisher")
public class PublisherController {

    private final PublisherService publisherService;
    public PublisherController(PublisherService publisherService) { this.publisherService = publisherService; }

    // Add publisher
    @PostMapping("/post")
    public ResponseEntity<Map<String,String>> addPublisher(@RequestBody Publisher publisher) {
        try {
            Publisher saved = publisherService.addPublisher(publisher);
            Map<String,String> body = Map.of(
                    "code", "POSTSUCCESS",
                    "message", "Publisher added successfully",
                    "publisherId", String.valueOf(saved.getPublisherId())
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(body);
        } catch (RuntimeException ex) {
            Map<String,String> body = Map.of(
                    "code","ADDFAILS",
                    "message", ex.getMessage()
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
        }
    }

    // Get all publishers
    @GetMapping("/all")
    public ResponseEntity<List<Publisher>> getAllPublishers() {
        return ResponseEntity.ok(publisherService.getAllPublishers());
    }

    // Get publisher by id
    @GetMapping("/{publisherId}")
    public ResponseEntity<?> getPublisherById(@PathVariable Integer publisherId) {
        return publisherService.getPublisherById(publisherId)
                .<ResponseEntity<?>>map(p -> ResponseEntity.ok(p))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("code","NOTFOUND","message","Publisher not found")));
    }

    // Get by name
    @GetMapping("/name/{name}")
    public ResponseEntity<?> getPublisherByName(@PathVariable String name) {
        return publisherService.getPublisherByName(name)
                .<ResponseEntity<?>>map(p -> ResponseEntity.ok(p))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("code","NOTFOUND","message","Publisher not found")));
    }

    // Get by city
    @GetMapping("/city/{city}")
    public ResponseEntity<List<Publisher>> getPublishersByCity(@PathVariable String city) {
        return ResponseEntity.ok(publisherService.getPublishersByCity(city));
    }

    // Get by state (by state name)
    @GetMapping("/state/{stateName}")
    public ResponseEntity<List<Publisher>> getPublishersByState(@PathVariable String stateName) {
        return ResponseEntity.ok(publisherService.getPublishersByState(stateName));
    }

    // Update name
    @PutMapping("/update/name/{publisherId}")
    public ResponseEntity<?> updateName(@PathVariable Integer publisherId, @RequestBody Map<String,String> body) {
        String name = body.get("name");
        if (name == null) return ResponseEntity.badRequest().body(Map.of("code","BADREQUEST","message","name is required"));
        try {
            Publisher updated = publisherService.updatePublisherName(publisherId, name);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("code","NOTFOUND","message",ex.getMessage()));
        }
    }

    // Update city
    @PutMapping("/update/city/{publisherId}")
    public ResponseEntity<?> updateCity(@PathVariable Integer publisherId, @RequestBody Map<String,String> body) {
        String city = body.get("city");
        if (city == null) return ResponseEntity.badRequest().body(Map.of("code","BADREQUEST","message","city is required"));
        try {
            Publisher updated = publisherService.updatePublisherCity(publisherId, city);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("code","NOTFOUND","message",ex.getMessage()));
        }
    }

    // Update state by state name
    @PutMapping("/update/state/{publisherId}")
    public ResponseEntity<?> updateState(@PathVariable Integer publisherId, @RequestBody Map<String,String> body) {
        String stateName = body.get("state");
        if (stateName == null) return ResponseEntity.badRequest().body(Map.of("code","BADREQUEST","message","state is required"));
        try {
            Publisher updated = publisherService.updatePublisherState(publisherId, stateName);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("code","NOTFOUND","message",ex.getMessage()));
        }
    }
}

