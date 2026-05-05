package com.familyhelpuae.interactionhistory.model;

public class InteractionHistoryModel {
    String interactionId; // requestId + OfferID
    String helpingFamilyId; // the family that helped in the interaction
    String helpedFamilyId; // the family that recieved help in the interaction
    String interactionType; // e.g., "help_request", "help_provided", "feedback_given"
    String description; // details about the interaction
    String status; // e.g., "pending", "completed", "cancelled"
    double ratingForHelpingFamily; // rating given for the helping family (1-6)
    double ratingForHelpedFamily; // rating given for the helped family (1-6)
    String createdAt; // when the interaction occurred
    String updatedAt; // when the interaction record was last updated
}
