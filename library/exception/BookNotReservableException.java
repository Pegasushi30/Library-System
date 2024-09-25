package com.project.library.exception;

public class BookNotReservableException extends RuntimeException {
    public BookNotReservableException(String message) {
        super(message);
    }
}