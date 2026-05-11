/**
 * Section: 104
 * Group number: 4
 * Student IDs and names: 
 * Laisa Sanjida Isra: 1089635
 * Fatima Syed Wasti: 1095190
 * Aysha Hira: 1088000
 */

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