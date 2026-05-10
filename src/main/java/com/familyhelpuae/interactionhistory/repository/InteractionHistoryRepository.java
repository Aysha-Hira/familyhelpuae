package com.familyhelpuae.interactionhistory.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.familyhelpuae.interactionhistory.model.InteractionHistory;

public interface InteractionHistoryRepository extends MongoRepository<InteractionHistory, String> {
    
    List<InteractionHistory> findByHelpingFamilyId(String familyId);
    
    List<InteractionHistory> findByHelpedFamilyId(String familyId);
    
    List<InteractionHistory> findByStatus(String status);
    
    @Query("{ $or: [ { 'helpingFamilyId': ?0 }, { 'helpedFamilyId': ?0 } ] }")
    List<InteractionHistory> findAllInteractionsForFamily(String familyId);
}