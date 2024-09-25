package com.project.library.exception;

public class BookPublisherNotFoundException extends RuntimeException {
    public BookPublisherNotFoundException(String message) {
        super(message);
    }
}
