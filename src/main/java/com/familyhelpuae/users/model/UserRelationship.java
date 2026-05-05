package com.familyhelpuae.users.model;

import lombok.Data;

@Data
public class UserRelationship {

    // For Registered Users: This will store the userID of the related user and the
    // type of relationship (e.g., "father", "brother", "mother", "sister").
    private String userId; // the id of the user this relationship is with
    private String relationshipType; // e.g., "father", "brother", "mother", "sister"

    // For Non-Registered Users: This will store the name and number of the related
    // person and the type of relationship (e.g., "father", "brother", "mother",
    // "sister").
    private String name = null; // the name of the related person
    private String email = null; // the email number of the related person

    public UserRelationship(String anotherUserID, String relationshipType) {
        this.userId = anotherUserID;
        this.relationshipType = relationshipType;
    }

    public UserRelationship(String name, String email, String relationshipType) {
        this.name = name;
        this.email = email;
        this.relationshipType = relationshipType;
    }

    public boolean isRegistered() {
        return userId != null;
    }

    public boolean isNonRegistered() {
        return name != null && email != null;
    }

}
