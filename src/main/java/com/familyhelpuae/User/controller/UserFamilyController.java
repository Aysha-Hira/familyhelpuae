package com.familyhelpuae.user.controller;

import lombok.Data;

@Data
public class UserFamilyController {
    private String familyId; // the id of the family
    private String role; // "admin" or "member"
}