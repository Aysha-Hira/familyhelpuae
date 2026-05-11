/**
 * Section: 104
 * Group number: 4
 * Student IDs and names: 
 * Laisa Sanjida Isra: 1089635
 * Fatima Syed Wasti: 1095190
 * Aysha Hira: 1088000
 */

package com.familyhelpuae.family.model;

import org.springframework.data.mongodb.core.mapping.Field;
import lombok.Data;

@Data
public class FamilyFeedback {

    @Field("fromFamilyId")
    String fromFamilyId; // who left the feedback

    @Field("fromUserId")
    String fromUserId; // which user left it

    @Field("interactionId")
    String interactionId; // which interaction this feedback is for

    @Field("rating")
    double rating; // 1-6

    @Field("comment")
    String comment; // optional written feedback

    @Field("createdAt")
    String createdAt;
}