/**
 * Section: 104
 * Group number: 4
 * Student IDs and names: 
 * Laisa Sanjida Isra: 1089635
 * Fatima Syed Wasti: 1095190
 * Aysha Hira: 1088000
 */

package com.familyhelpuae.interactionhistory.service;

import java.util.List;

import com.familyhelpuae.interactionhistory.model.InteractionHistory;

public interface interactionHistoryService {

    // Create
    InteractionHistory recordInteraction(String helpingFamilyId, String helpedFamilyId,
            String interactionType, String description,
            String requestId, String offerId);

    // Read
    InteractionHistory getInteractionById(String interactionId);

    List<InteractionHistory> getAllInteractions();

    List<InteractionHistory> getInteractionsByFamily(String familyId);

    List<InteractionHistory> getInteractionsByHelpingFamily(String familyId);

    List<InteractionHistory> getInteractionsByHelpedFamily(String familyId);

    // Update
    InteractionHistory updateInteraction(String interactionId, InteractionHistory interaction);

    InteractionHistory updateStatus(String interactionId, String status);

    InteractionHistory addRating(String interactionId, String role, double rating);

    // Delete
    void deleteInteraction(String interactionId);
}