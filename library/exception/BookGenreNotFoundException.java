package com.project.library.exception;

public class BookGenreNotFoundException extends RuntimeException {
    public BookGenreNotFoundException(String message) {
        super(message);
    }
}