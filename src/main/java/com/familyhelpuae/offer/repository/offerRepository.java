/**
 * Section: 104
 * Group number: 4
 * Student IDs and names: 
 * Laisa Sanjida Isra: 1089635
 * Fatima Syed Wasti: 1095190
 * Aysha Hira: 1088000
 */

package com.familyhelpuae.offer.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.familyhelpuae.offer.model.Offer;

public interface offerRepository extends MongoRepository<Offer, String> {

    List<Offer> findByOfferingFamilyId(String offeringFamilyId);

    List<Offer> findByOfferingUserId(String offeringUserId);

    List<Offer> findByOfferStatus(String offerStatus);

    List<Offer> findByOfferType(String offerType);

    List<Offer> findByOfferLocationContaining(String offerLocation);
}