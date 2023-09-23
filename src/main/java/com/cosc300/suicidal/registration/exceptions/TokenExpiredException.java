package com.cosc300.suicidal.registration.exceptions;


public class TokenExpiredException extends RuntimeException {
    public TokenExpiredException(String message) {
        super(message);
    }
}
