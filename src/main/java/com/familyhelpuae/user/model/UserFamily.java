package com.familyhelpuae.user.model;

import lombok.Data;

@Data
public class UserFamily {
    public UserFamily(String familyId2, String role2) {
        //TODO Auto-generated constructor stub
    }
    private String familyId; // the id of the family
    private String role; // "admin" or "member"
}