package com.example.bookinventory.util;



/**
 * Centralized constants for the application.
 * Helps avoid string duplication in controllers and services.
 */
public class Constants {

    private Constants() {}

    // --- Response Codes ---
    public static final String POST_SUCCESS = "POSTSUCCESS";
    public static final String ADD_FAILS = "ADDFAILS";
    public static final String NOT_FOUND = "NOTFOUND";
    public static final String BAD_REQUEST = "BADREQUEST";
    public static final String INTERNAL_ERROR = "INTERNAL_ERROR";

    // --- Messages ---
    public static final String AUTHOR_EXISTS = "Author already exists";
    public static final String BOOK_EXISTS = "Book already exists";
    public static final String PUBLISHER_EXISTS = "Publisher already exists";

    public static final String AUTHOR_NOT_FOUND = "Author not found";
    public static final String BOOK_NOT_FOUND = "Book not found";
    public static final String PUBLISHER_NOT_FOUND = "Publisher not found";

    // --- Swagger Info ---
    public static final String API_TITLE = "Book Inventory Management API";
    public static final String API_VERSION = "1.0.0";

    // --- Misc ---
    public static final String DEFAULT_CATEGORY = "General";
}

