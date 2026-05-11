/**
 * Section: 104
 * Group number: 4
 * Student IDs and names: 
 * Laisa Sanjida Isra: 1089635
 * Fatima Syed Wasti: 1095190
 * Aysha Hira: 1088000
 */

package com.familyhelpuae.user.service;

import java.util.List;

import com.familyhelpuae.user.model.User;
import com.familyhelpuae.user.model.UserRelationship;

public interface UserRelationshipService {
    List<UserRelationship> getAllRelationships(String userId);

    List<UserRelationship> getRelationshipsByType(String userId, String relationshipType); // do we need this??

    boolean hasRelationshipWithRegisteredUser(String userId, String anotherUserID);

    boolean hasRelationshipWithNonRegisteredUser(String userId, String name, String email);

    User addRelationship(String userId, String anotherUserID, String relationshipType);

    User addRelationship(String userId, String name, String email, String relationshipType);

    User updateRelationship(String userId, String anotherUserID, String relationshipType);

    User updateRelationship(String userId, String name, String email, String relationshipType);

    void removeRelationship(String userId, String anotherUserID);

    void removeRelationship(String userId, String name, String email);

}
