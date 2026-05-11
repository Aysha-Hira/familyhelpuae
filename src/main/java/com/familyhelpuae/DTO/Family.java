/**
 * Section: 104
 * Group number: 4
 * Student IDs and names: 
 * Laisa Sanjida Isra: 1089635
 * Fatima Syed Wasti: 1095190
 * Aysha Hira: 1088000
 */

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
