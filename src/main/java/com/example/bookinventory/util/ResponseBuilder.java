package com.example.bookinventory.util;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Utility class for building standardized API responses.
 * Makes it easy to send consistent JSON responses across all controllers.
 */
public class ResponseBuilder {

    private ResponseBuilder() {}

    /**
     * Build a success response with custom message and data.
     */
    public static ResponseEntity<Map<String, Object>> success(String message, Object data) {
        Map<String, Object> body = Map.of(
                "timestamp", LocalDateTime.now().toString(),
                "code", "SUCCESS",
                "message", message,
                "data", data
        );
        return ResponseEntity.ok(body);
    }

    /**
     * Build a custom error response.
     */
    public static ResponseEntity<Map<String, Object>> error(HttpStatus status, String code, String message) {
        Map<String, Object> body = Map.of(
                "timestamp", LocalDateTime.now().toString(),
                "code", code,
                "message", message,
                "status", status.value()
        );
        return ResponseEntity.status(status).body(body);
    }

    /**
     * Build a success response without additional data.
     */
    public static ResponseEntity<Map<String, Object>> message(String message) {
        Map<String, Object> body = Map.of(
                "timestamp", LocalDateTime.now().toString(),
                "code", "SUCCESS",
                "message", message
        );
        return ResponseEntity.ok(body);
    }
}
