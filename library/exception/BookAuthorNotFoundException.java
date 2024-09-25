package com.project.library.exception;

public class BookAuthorNotFoundException extends RuntimeException {
    public BookAuthorNotFoundException(String message) {
        super(message);
    }
}