package com.example.reservation.exception;

public class GuestLimitExceededException extends RuntimeException {
    public GuestLimitExceededException(String message) {
        super(message);
    }

    public GuestLimitExceededException(String message, Throwable cause) {
        super(message, cause);
    }
}
