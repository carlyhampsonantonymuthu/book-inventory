package com.example.bookinventory.controller;

import com.example.bookinventory.entity.State;
import com.example.bookinventory.service.StateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/state")
@CrossOrigin(origins = "*")


public class StateController {

    private final StateService stateService;

    public StateController(StateService stateService) {
        this.stateService = stateService;
    }

    /**
     * ➕ Add new state
     */
    @PostMapping("/post")
    public ResponseEntity<?> addState(@RequestBody State state) {
        try {
            State saved = stateService.addState(state);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "code", "POSTSUCCESS",
                    "message", "State added successfully",
                    "stateCode", saved.getStateCode()
            ));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "code", "ADDFAILS",
                    "message", ex.getMessage()
            ));
        }
    }

    /**
     * 🔍 Get all states
     */
    @GetMapping("/all")
    public ResponseEntity<?> getAllStates() {
        List<State> states = stateService.getAllStates();
        if (states.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("code", "NOTFOUND", "message", "No states found"));
        }
        return ResponseEntity.ok(states);
    }

    /**
     * 🔍 Get a single state by code
     */
    @GetMapping("/{stateCode}")
    public ResponseEntity<?> getStateByCode(@PathVariable String stateCode) {
        return stateService.getStateByCode(stateCode)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("code", "NOTFOUND", "message", "State not found")));
    }

    /**
     * ✏️ Update state name by code
     */
    @PutMapping("/{stateCode}")
    public ResponseEntity<?> updateStateName(@PathVariable String stateCode, @RequestBody Map<String, String> body) {
        String newName = body.get("stateName");
        if (newName == null || newName.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("code", "BADREQUEST", "message", "stateName is required"));
        }

        try {
            State updated = stateService.updateStateName(stateCode, newName);
            return ResponseEntity.ok(Map.of(
                    "code", "UPDATESUCCESS",
                    "message", "State name updated successfully",
                    "updatedState", updated
            ));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("code", "NOTFOUND", "message", ex.getMessage()));
        }
    }
}
