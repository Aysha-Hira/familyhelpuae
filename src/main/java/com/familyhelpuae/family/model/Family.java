/**
 * Section: 104
 * Group number: 4
 * Student IDs and names: 
 * Laisa Sanjida Isra: 1089635
 * Fatima Syed Wasti: 1095190
 * Aysha Hira: 1088000
 */

package com.familyhelpuae.family.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
@Document(collection = "family")
public class Family {
    @Id
    String familyId;

    @Field(name = "familyName")
    String familyName;

    @Field(name = "country")
    String country;

    @Field(name = "state")
    String state;

    @Field(name = "city")
    String city;

    @Field(name = "familyTrustScore")
    double familyTrustScore;
    // this is for the family members, and their member roles

    @Field(name = "members")
    List<FamilyMember> members;

    // TODO: Need to add controller + service for this
    @Field(name = "familycode")
    int FamilyCode;

    @Field(name = "createdAt")
    LocalDateTime createdAt;

    @Field(name = "updatedAt")
    LocalDateTime updatedAt;

    @Field(name = "feedbacks")
    List<FamilyFeedback> feedbacks; // list of feedback entries left by other families
}
