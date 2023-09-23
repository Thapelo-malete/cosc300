package com.cosc300.suicidal.registration.exceptions;

public class TokenNotFoundException extends RuntimeException {
    public TokenNotFoundException(String message)  {
        super(message);
    }
}
