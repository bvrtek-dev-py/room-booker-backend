package com.example.reservation.exception;

public class ApartmentNotAvailableException extends RuntimeException {
    public ApartmentNotAvailableException(String message) {
        super(message);
    }

    public ApartmentNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
