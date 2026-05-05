package com.familyhelpuae.users.model;

import lombok.Data;

@Data
public class UserFamily {
    private String familyId; // the id of the family
    private String role; // "admin" or "member"
}