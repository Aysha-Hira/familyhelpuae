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