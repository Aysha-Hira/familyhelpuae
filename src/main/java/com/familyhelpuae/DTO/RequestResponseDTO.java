/**
 * Section: 104
 * Group number: 4
 * Student IDs and names: 
 * Laisa Sanjida Isra: 1089635
 * Fatima Syed Wasti: 1095190
 * Aysha Hira: 1088000
 */

package com.familyhelpuae.DTO;

import java.util.List;

import lombok.Data;

@Data
public class RequestResponseDTO {
    // All request fields
    String requestId;
    String requestTitle;
    String requestDescription;
    String requestType;
    String urgencyLevel;
    String requestStatus;
    String location;
    String createdAt;
    List<String> linkedOfferIds;

    // Enriched user info
    String requestingUserId;
    String requestingUserName; // firstName + lastName

    // Enriched family info
    String requestingFamilyId;
    String requestingFamilyName;
    double familyTrustScore;

}
