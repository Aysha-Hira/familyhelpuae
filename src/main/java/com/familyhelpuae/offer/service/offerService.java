/**
 * Section: 104
 * Group number: 4
 * Student IDs and names: 
 * Laisa Sanjida Isra: 1089635
 * Fatima Syed Wasti: 1095190
 * Aysha Hira: 1088000
 */

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