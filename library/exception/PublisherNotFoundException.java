package com.project.library.exception;

public class PublisherNotFoundException extends RuntimeException {
    public PublisherNotFoundException(String message) {
        super(message);
    }
}
