package com.familyhelpuae.interactionhistory.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import com.familyhelpuae.exception.ResourceNotFound;
import com.familyhelpuae.family.model.Family;
import com.familyhelpuae.family.model.FamilyMember;
import com.familyhelpuae.family.service.FamilyService;
import com.familyhelpuae.interactionhistory.model.InteractionHistory;
import com.familyhelpuae.interactionhistory.repository.InteractionHistoryRepository;
import com.familyhelpuae.interactionhistory.service.interactionHistoryService;
import com.familyhelpuae.user.service.UserService;

@Service
public class interactionHistoryServiceImpl implements interactionHistoryService {

    private final InteractionHistoryRepository interactionRepository;
    private final FamilyService familyService;
    private final UserService UserService;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public interactionHistoryServiceImpl(InteractionHistoryRepository interactionRepository,
            FamilyService familyService, UserService UserService) {
        this.interactionRepository = interactionRepository;
        this.familyService = familyService;
        this.UserService = UserService;
    }

    @Override
    public InteractionHistory recordInteraction(String helpingFamilyId, String helpedFamilyId,
            String interactionType, String description,
            String requestId, String offerId) {
        InteractionHistory interaction = new InteractionHistory();
        interaction.setHelpingFamilyId(helpingFamilyId);
        interaction.setHelpedFamilyId(helpedFamilyId);
        interaction.setInteractionType(interactionType);
        interaction.setDescription(description);
        interaction.setRequestId(requestId);
        interaction.setOfferId(offerId);
        interaction.setStatus("pending");
        interaction.setRatingForHelpingFamily(0.0);
        interaction.setRatingForHelpedFamily(0.0);
        String now = LocalDateTime.now().format(formatter);
        interaction.setCreatedAt(now);
        interaction.setUpdatedAt(now);
        return interactionRepository.save(interaction);
    }

    @Override
    public InteractionHistory getInteractionById(String interactionId) {
        return interactionRepository.findById(interactionId)
                .orElseThrow(() -> new ResourceNotFound("InteractionHistory", "interactionId", interactionId));
    }

    @Override
    public List<InteractionHistory> getAllInteractions() {
        return interactionRepository.findAll();
    }

    @Override
    public List<InteractionHistory> getInteractionsByFamily(String familyId) {
        return interactionRepository.findAllInteractionsForFamily(familyId);
    }

    @Override
    public List<InteractionHistory> getInteractionsByHelpingFamily(String familyId) {
        return interactionRepository.findByHelpingFamilyId(familyId);
    }

    @Override
    public List<InteractionHistory> getInteractionsByHelpedFamily(String familyId) {
        return interactionRepository.findByHelpedFamilyId(familyId);
    }

    @Override
    public InteractionHistory updateInteraction(String interactionId, InteractionHistory updatedInteraction) {
        InteractionHistory existing = getInteractionById(interactionId);

        if (updatedInteraction.getDescription() != null)
            existing.setDescription(updatedInteraction.getDescription());
        if (updatedInteraction.getStatus() != null)
            existing.setStatus(updatedInteraction.getStatus());
        if (updatedInteraction.getRatingForHelpingFamily() > 0)
            existing.setRatingForHelpingFamily(updatedInteraction.getRatingForHelpingFamily());
        if (updatedInteraction.getRatingForHelpedFamily() > 0)
            existing.setRatingForHelpedFamily(updatedInteraction.getRatingForHelpedFamily());

        existing.setUpdatedAt(LocalDateTime.now().format(formatter));

        // If both ratings submitted, mark as completed and update trust scores
        if (existing.getRatingForHelpingFamily() > 0 && existing.getRatingForHelpedFamily() > 0
                && "pending".equals(existing.getStatus())) {
            existing.setStatus("completed");
            updateTrustScores(existing);
        }

        return interactionRepository.save(existing);
    }

    @Override
    public InteractionHistory updateStatus(String interactionId, String status) {
        InteractionHistory existing = getInteractionById(interactionId);
        existing.setStatus(status);
        existing.setUpdatedAt(LocalDateTime.now().format(formatter));
        return interactionRepository.save(existing);
    }

    @Override
    public InteractionHistory addRating(String interactionId, String role, double rating) {
        if (rating < 1 || rating > 6) {
            throw new IllegalArgumentException("Rating must be between 1 and 6");
        }

        InteractionHistory existing = getInteractionById(interactionId);

        if ("helping".equals(role)) {
            existing.setRatingForHelpingFamily(rating);
        } else if ("helped".equals(role)) {
            existing.setRatingForHelpedFamily(rating);
        } else {
            throw new IllegalArgumentException("Role must be 'helping' or 'helped'");
        }

        existing.setUpdatedAt(LocalDateTime.now().format(formatter));

        // If both ratings submitted, mark as completed and update trust scores
        if (existing.getRatingForHelpingFamily() > 0 && existing.getRatingForHelpedFamily() > 0
                && "pending".equals(existing.getStatus())) {
            existing.setStatus("completed");
            updateTrustScores(existing);
        }

        return interactionRepository.save(existing);
    }

    private void updateTrustScores(InteractionHistory interaction) {
        // Formula: (rating - 3.5) * 2 (range: -5 to +5 per interaction)
        double helpingFamilyChange = (interaction.getRatingForHelpingFamily() - 3.5) * 2;
        double helpedFamilyChange = (interaction.getRatingForHelpedFamily() - 3.5) * 2;

        double currentHelpingScore = familyService.getTrustScore(interaction.getHelpingFamilyId());
        double currentHelpedScore = familyService.getTrustScore(interaction.getHelpedFamilyId());

        familyService.setTrustScore(interaction.getHelpingFamilyId(),
                Math.max(0, Math.min(6, currentHelpingScore + helpingFamilyChange)));
        familyService.setTrustScore(interaction.getHelpedFamilyId(),
                Math.max(0, Math.min(6, currentHelpedScore + helpedFamilyChange)));

        // Update individual user trust scores for all active members of each family
        updateMemberTrustScores(interaction.getHelpingFamilyId(), interaction.getRatingForHelpingFamily());
        updateMemberTrustScores(interaction.getHelpedFamilyId(), interaction.getRatingForHelpedFamily());
    }

    private void updateMemberTrustScores(String familyId, double rating) {
        Family family = familyService.getFamilyById(familyId);
        if (family.getMembers() == null)
            return;
        for (FamilyMember member : family.getMembers()) {
            if (Boolean.TRUE.equals(member.getIsUser())) {
                try {
                    UserService.updateTrustScore(member.getFamilyMemberId(), rating);
                } catch (Exception e) {
                    // Member may no longer exist — skip silently
                }
            }
        }
    }

    @Override
    public void deleteInteraction(String interactionId) {
        if (!interactionRepository.existsById(interactionId)) {
            throw new ResourceNotFound("InteractionHistory", "interactionId", interactionId);
        }
        interactionRepository.deleteById(interactionId);
    }
}