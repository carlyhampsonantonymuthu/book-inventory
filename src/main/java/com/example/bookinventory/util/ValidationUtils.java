package com.example.bookinventory.util;


import org.springframework.util.StringUtils;

/**
 * Utility class for input validation.
 * Helps check common field constraints before saving entities.
 */
public class ValidationUtils {

    private ValidationUtils() {}

    /**
     * Validates that a string is not null or empty.
     */
    public static void requireNonEmpty(String field, String fieldName) {
        if (!StringUtils.hasText(field)) {
            throw new IllegalArgumentException(fieldName + " cannot be null or empty");
        }
    }

    /**
     * Validates that an object is not null.
     */
    public static void requireNonNull(Object obj, String fieldName) {
        if (obj == null) {
            throw new IllegalArgumentException(fieldName + " cannot be null");
        }
    }

    /**
     * Validates numeric values are non-negative.
     */
    public static void requireNonNegative(Number num, String fieldName) {
        if (num == null || num.doubleValue() < 0) {
            throw new IllegalArgumentException(fieldName + " must be non-negative");
        }
    }
}
