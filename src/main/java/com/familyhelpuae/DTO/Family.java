package com.familyhelpuae.DTO;

import lombok.Data;

@Data
public class Family {
    private String familyName; // the name of the family
    private String emirate; // the emirate of the family;
    private String role; // "admin" or "member"
    private boolean createFamily;
    private String familyCode; // the cod of the family (only filled if joining existing)
    // if joining by code, the familyCode will be used to identify the family
}
