package com.example.bookinventory.exception;


/**
 * Thrown when attempting to create a record that already exists.
 */
public class DuplicateResourceException extends RuntimeException {

    public DuplicateResourceException(String message) {
        super(message);
    }
}
