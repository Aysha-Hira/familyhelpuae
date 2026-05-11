/**
 * Section: 104
 * Group number: 4
 * Student IDs and names: 
 * Laisa Sanjida Isra: 1089635
 * Fatima Syed Wasti: 1095190
 * Aysha Hira: 1088000
 */

package com.familyhelpuae.user.model;

import lombok.Data;

@Data
public class UserFamily {
    public UserFamily(String familyId, String role) {
        this.familyId = familyId;
        this.role = role;
    }

    private String familyId; // the id of the family
    private String role; // "admin" or "member"
}