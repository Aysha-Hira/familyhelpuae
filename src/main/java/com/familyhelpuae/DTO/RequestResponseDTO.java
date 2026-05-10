package com.familyhelpuae.DTO;

import java.util.List;

import com.familyhelpuae.request.model.Request;
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
    String requestingUserName;   // firstName + lastName

    // Enriched family info
    String requestingFamilyId;
    String requestingFamilyName;
    double familyTrustScore;
    
}
