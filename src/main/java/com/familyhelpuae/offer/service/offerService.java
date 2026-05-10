package com.familyhelpuae.offer.service;

import java.util.List;

import com.familyhelpuae.offer.model.Offer;
import com.mongodb.lang.NonNull;

public interface offerService {
    
    Offer createOffer(Offer offer);
    Offer getOfferById(@NonNull String offerId);
    List<Offer> getAllOffers();
    Offer updateOffer(Offer updatedOffer, @NonNull String offerId);
    void deleteOffer(@NonNull String offerId);
    
    List<Offer> getOffersByFamily(@NonNull String familyId);
    List<Offer> getOffersByUser(@NonNull String userId);
    List<Offer> getOffersByStatus(@NonNull String status);
    List<Offer> getOffersByType(@NonNull String offerType);
    
    Offer linkRequest(String offerId, String requestId);
    Offer unlinkRequest(String offerId, String requestId);
    
    Offer updateStatus(@NonNull String offerId, @NonNull String status);
}