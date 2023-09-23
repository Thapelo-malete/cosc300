package com.cosc300.suicidal.registration.exceptions;

public class EmailTakenException extends RuntimeException {
    public EmailTakenException(String message) {
        super(message);
    }
}
