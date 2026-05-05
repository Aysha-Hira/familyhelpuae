package com.familyhelpuae.users.service.impl;

import lombok.Data;

@Data
public class UserFamilyServiceImpl {
    private String familyId; // the id of the family
    private String role; // "admin" or "member"
}