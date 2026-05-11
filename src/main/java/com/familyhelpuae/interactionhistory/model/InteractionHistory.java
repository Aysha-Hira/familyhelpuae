/**
 * Section: 104
 * Group number: 4
 * Student IDs and names: 
 * Laisa Sanjida Isra: 1089635
 * Fatima Syed Wasti: 1095190
 * Aysha Hira: 1088000
 */

package com.familyhelpuae.interactionhistory.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
@Document(collection = "interaction_history")
public class InteractionHistory {

    @Id
    private String interactionId;

    @Field("helpingFamilyId")
    private String helpingFamilyId;

    @Field("helpedFamilyId")
    private String helpedFamilyId;

    @Field("interactionType")
    private String interactionType;

    @Field("description")
    private String description;

    @Field("status")
    private String status;

    @Field("ratingForHelpingFamily")
    private double ratingForHelpingFamily;

    @Field("ratingForHelpedFamily")
    private double ratingForHelpedFamily;

    @Field("requestId")
    private String requestId;

    @Field("offerId")
    private String offerId;

    @Field("createdAt")
    private String createdAt;

    @Field("updatedAt")
    private String updatedAt;

    // Getters
    public String getInteractionId() {
        return interactionId;
    }

    public String getHelpingFamilyId() {
        return helpingFamilyId;
    }

    public String getHelpedFamilyId() {
        return helpedFamilyId;
    }

    public String getInteractionType() {
        return interactionType;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public double getRatingForHelpingFamily() {
        return ratingForHelpingFamily;
    }

    public double getRatingForHelpedFamily() {
        return ratingForHelpedFamily;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getOfferId() {
        return offerId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    // Setters
    public void setInteractionId(String interactionId) {
        this.interactionId = interactionId;
    }

    public void setHelpingFamilyId(String helpingFamilyId) {
        this.helpingFamilyId = helpingFamilyId;
    }

    public void setHelpedFamilyId(String helpedFamilyId) {
        this.helpedFamilyId = helpedFamilyId;
    }

    public void setInteractionType(String interactionType) {
        this.interactionType = interactionType;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRatingForHelpingFamily(double ratingForHelpingFamily) {
        this.ratingForHelpingFamily = ratingForHelpingFamily;
    }

    public void setRatingForHelpedFamily(double ratingForHelpedFamily) {
        this.ratingForHelpedFamily = ratingForHelpedFamily;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}