package com.familyhelpuae.auth.model;

import lombok.Data;

@Data
public class Relationship {
    private String relatedUserId; // if the person is already on the platform
    private String relatedName; // if not registered
    private String relatedEmail; // if not registered
    private String relationshipType; // "father", "mother", "brother", "sister" etc.
}
