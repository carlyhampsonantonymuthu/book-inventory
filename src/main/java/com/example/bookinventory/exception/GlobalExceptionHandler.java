package com.example.bookinventory.exception;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

/**
 * Global Exception Handler for the entire application.
 * Catches custom exceptions and returns standardized JSON responses.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle ResourceNotFoundException (404)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleResourceNotFound(ResourceNotFoundException ex) {
        Map<String, String> body = Map.of(
                "code", "NOTFOUND",
                "message", ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    // Handle DuplicateResourceException (400)
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<Map<String, String>> handleDuplicateResource(DuplicateResourceException ex) {
        Map<String, String> body = Map.of(
                "code", "ADDFAILS",
                "message", ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    // Handle any generic runtime exceptions (500)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntime(RuntimeException ex) {
        Map<String, String> body = Map.of(
                "code", "INTERNAL_ERROR",
                "message", ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    // Handle unexpected exceptions (505)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneric(Exception ex) {
        Map<String, String> body = Map.of(
                "code", "HTTP_VERSION_NOT_SUPPORTED",
                "message", ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.HTTP_VERSION_NOT_SUPPORTED).body(body);
    }
}
