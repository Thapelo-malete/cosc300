package com.cosc300.suicidal.registration.exceptions;

public class UserExistsException extends RuntimeException {
    private final String email;
    
    public UserExistsException(String message, String email) {
        super(message);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
