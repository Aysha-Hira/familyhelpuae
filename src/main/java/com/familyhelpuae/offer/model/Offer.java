package com.familyhelpuae.offer.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "offer")
public class Offer {
    
    @Id
    private String offerId;
    
    @Field("offeringFamilyId")
    private String offeringFamilyId;
    
    @Field("offeringUserId")
    private String offeringUserId;
    
    @Field("offerType")
    private String offerType;
    
    @Field("offerLocation")
    private String offerLocation;
    
    @Field("availability")
    private String availability;
    
    @Field("offerDescription")
    private String offerDescription;
    
    @Field("offerStatus")
    private String offerStatus;
    
    @Field("createdAt")
    private String createdAt;
    
    @Field("updatedAt")
    private String updatedAt;

    // ========== Getters ==========
    public String getOfferId() {
        return offerId;
    }

    public String getOfferingFamilyId() {
        return offeringFamilyId;
    }

    public String getOfferingUserId() {
        return offeringUserId;
    }

    public String getOfferType() {
        return offerType;
    }

    public String getOfferLocation() {
        return offerLocation;
    }

    public String getAvailability() {
        return availability;
    }

    public String getOfferDescription() {
        return offerDescription;
    }

    public String getOfferStatus() {
        return offerStatus;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    // ========== Setters ==========
    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public void setOfferingFamilyId(String offeringFamilyId) {
        this.offeringFamilyId = offeringFamilyId;
    }

    public void setOfferingUserId(String offeringUserId) {
        this.offeringUserId = offeringUserId;
    }

    public void setOfferType(String offerType) {
        this.offerType = offerType;
    }

    public void setOfferLocation(String offerLocation) {
        this.offerLocation = offerLocation;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public void setOfferDescription(String offerDescription) {
        this.offerDescription = offerDescription;
    }

    public void setOfferStatus(String offerStatus) {
        this.offerStatus = offerStatus;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}