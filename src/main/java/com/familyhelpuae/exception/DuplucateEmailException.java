/**
 * Section: 104
 * Group number: 4
 * Student IDs and names: 
 * Laisa Sanjida Isra: 1089635
 * Fatima Syed Wasti: 1095190
 * Aysha Hira: 1088000
 */

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
