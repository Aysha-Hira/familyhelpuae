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