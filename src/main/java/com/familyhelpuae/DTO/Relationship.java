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
public class Relationship {
    private String relatedUserId; // if the person is already on the platform
    private String relatedName; // if not registered
    private String relatedEmail; // if not registered
    private String relationshipType; // "father", "mother", "brother", "sister" etc.
}
