package com.familyhelpuae.exception;

public class DuplucateEmailException extends RuntimeException {
    private String email;

    public DuplucateEmailException(String email) {
        super("Email already exists: " + email);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
