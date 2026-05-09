package com.familyhelpuae.exception;

public class UserNotFoundException extends RuntimeException {
    private String email;

    public UserNotFoundException(String email) {
        super("User not found with the email: " + email);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

}
