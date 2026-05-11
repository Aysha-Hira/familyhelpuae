/**
 * Section: 104
 * Group number: 4
 * Student IDs and names: 
 * Laisa Sanjida Isra: 1089635
 * Fatima Syed Wasti: 1095190
 * Aysha Hira: 1088000
 */

package com.familyhelpuae.exception;

public class DuplicateFamilyIDException extends Exception {
    private String familyId;

    public DuplicateFamilyIDException(String familyId) {
        super("Email already exists");
        this.familyId = familyId;
    }

    public String getFamilyId() {
        return familyId;
    }

}
