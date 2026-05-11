/**
 * Section: 104
 * Group number: 4
 * Student IDs and names: 
 * Laisa Sanjida Isra: 1089635
 * Fatima Syed Wasti: 1095190
 * Aysha Hira: 1088000
 */

package com.familyhelpuae.family.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.familyhelpuae.exception.ResourceNotFound;
import com.familyhelpuae.family.model.Family;
import com.familyhelpuae.family.model.FamilyFeedback;
import com.familyhelpuae.family.model.FamilyMember;
import com.familyhelpuae.family.repository.FamilyRepository;
import com.familyhelpuae.family.service.FamilyService;
import com.familyhelpuae.user.service.UserFamilyService;
import com.familyhelpuae.request.model.Request;
import com.familyhelpuae.request.repository.RequestRepository;
import com.familyhelpuae.offer.model.Offer;
import com.familyhelpuae.offer.repository.offerRepository;
import com.familyhelpuae.interactionhistory.model.InteractionHistory;
import com.familyhelpuae.interactionhistory.repository.InteractionHistoryRepository;
import java.util.stream.Stream;

@Service
public class FamilyServiceImpl implements FamilyService {

	private final FamilyRepository familyRepo;
	private final UserFamilyService userFamilyService;
	private final RequestRepository requestRepo;
	private final offerRepository offerRepository;
	private final InteractionHistoryRepository interactionHistoryRepo;

	public FamilyServiceImpl(FamilyRepository familyRepo,
			UserFamilyService userFamilyService,
			RequestRepository requestRepo,
			offerRepository offerRepository,
			InteractionHistoryRepository interactionHistoryRepo) {
		this.familyRepo = familyRepo;
		this.userFamilyService = userFamilyService;
		this.requestRepo = requestRepo;
		this.offerRepository = offerRepository;
		this.interactionHistoryRepo = interactionHistoryRepo;
	}

	@Override
	public List<Family> getAllFamilies() {
		return familyRepo.findAll();
	}

	@Override
	public Family saveFamily(Family newFamily) {
		newFamily.setFamilyId(null);
		newFamily.setFamilyTrustScore(0.0);
		newFamily.setMembers(new ArrayList<>());

		LocalDateTime now = LocalDateTime.now();
		newFamily.setCreatedAt(now);
		newFamily.setUpdatedAt(now);

		return familyRepo.save(newFamily);
	}

	@Override
	public Family getFamilyById(String familyId) {
		return familyRepo.findById(familyId)
				.orElseThrow(() -> new ResourceNotFound("Family", "familyId", familyId));
	}

	@Override
	public Family updateFamily(Family newFamily, String familyId) {
		Family existing = getFamilyById(familyId);

		if (newFamily.getFamilyName() != null)
			existing.setFamilyName(newFamily.getFamilyName());
		if (newFamily.getCountry() != null)
			existing.setCountry(newFamily.getCountry());
		if (newFamily.getState() != null)
			existing.setState(newFamily.getState());
		if (newFamily.getCity() != null)
			existing.setCity(newFamily.getCity());
		if (newFamily.getMembers() != null)
			existing.setMembers(newFamily.getMembers());

		existing.setUpdatedAt(LocalDateTime.now());

		return familyRepo.save(existing);
	}

	@Override
	public void deleteFamily(String familyId) {
		if (!familyRepo.existsById(familyId)) {
			throw new ResourceNotFound("Family", "familyId", familyId);
		}

		// Delete all requests belonging to this family
		List<Request> familyRequests = requestRepo.findByRequestingFamilyId(familyId);
		requestRepo.deleteAll(familyRequests);

		// Delete all offers belonging to this family
		List<Offer> familyOffers = offerRepository.findByOfferingFamilyId(familyId);
		offerRepository.deleteAll(familyOffers);

		familyRepo.deleteById(familyId);
	}

	@Override
	public List<Family> getFamiliesByName(String familyName) {
		return familyRepo.findByFamilyName(familyName);
	}

	@Override
	public Family addMember(String familyId, FamilyMember member) {
		Family f = getFamilyById(familyId);
		List<FamilyMember> members = f.getMembers();

		// Prevent duplicate members
		boolean alreadyExists = members.stream()
				.anyMatch(m -> m.getFamilyMemberId().equals(member.getFamilyMemberId()));
		if (alreadyExists) {
			throw new IllegalArgumentException("Member already exists in this family");
		}

		// Add member to family
		members.add(member);
		f.setMembers(members);
		Family updated = updateFamily(f, familyId);

		// Sync with user side — add this family to the user's family list
		String role = Boolean.TRUE.equals(member.getIsAdmin()) ? "admin" : "member";
		try {
			userFamilyService.addFamily(member.getFamilyMemberId(), familyId, role);
		} catch (IllegalArgumentException e) {
			// User already has this family on their side — that's fine, continue
		}

		return updated;
	}

	@Override
	public Family removeMember(String familyId, FamilyMember member) {
		Family f = getFamilyById(familyId);
		List<FamilyMember> members = f.getMembers();
		members.remove(member);
		f.setMembers(members);
		Family updated = updateFamily(f, familyId);

		try {
			userFamilyService.removeFamily(member.getFamilyMemberId(), familyId);
		} catch (IllegalArgumentException e) {
		}

		return updated;
	}

	@Override
	public List<FamilyMember> getActiveMembers(String familyId) {
		Family f = getFamilyById(familyId);
		List<FamilyMember> members = f.getMembers();

		List<FamilyMember> users = new ArrayList<>();

		for (FamilyMember member : members) {
			if (member.getIsUser())
				users.add(member);
		}

		return users;
	}

	@Override
	public List<FamilyMember> getFamilyAdmins(String familyId) {
		Family f = getFamilyById(familyId);
		List<FamilyMember> members = f.getMembers();

		List<FamilyMember> admins = new ArrayList<>();

		for (FamilyMember member : members) {
			if (member.getIsAdmin())
				admins.add(member);
		}

		return admins;
	}

	@Override
	public double getTrustScore(String familyId) {
		return getFamilyById(familyId).getFamilyTrustScore();
	}

	@Override
	public void setTrustScore(String familyId, double score) {
		Family f = getFamilyById(familyId);
		f.setFamilyTrustScore(score);

		updateFamily(f, familyId);
	}

	@Override
	public void calculateTrustScore(String familyId) {
		Family f = getFamilyById(familyId);
		List<FamilyMember> members = f.getMembers();

		// Base score + member bonuses
		double base = 1.0;
		double memberBonus = Math.min(members.size() * 0.2, 1.0);
		double activeBonus = members.stream().filter(FamilyMember::getIsUser).count() * 0.1;

		List<InteractionHistory> asHelper = interactionHistoryRepo.findByHelpingFamilyId(familyId);
		List<InteractionHistory> asHelped = interactionHistoryRepo.findByHelpedFamilyId(familyId);

		long completedCount = Stream.concat(asHelper.stream(), asHelped.stream())
				.filter(i -> "completed".equalsIgnoreCase(i.getStatus()))
				.count();

		double interactionBonus = Math.min(completedCount * 0.5, 2.0);

		double avgRating = f.getFeedbacks() == null || f.getFeedbacks().isEmpty() ? 0.0
				: f.getFeedbacks().stream()
						.mapToDouble(FamilyFeedback::getRating)
						.average()
						.orElse(0.0);

		double ratingBonus = (avgRating / 6.0) * 0.3;

		double score = Math.min(base + memberBonus + activeBonus + interactionBonus + ratingBonus, 5.0);

		f.setFamilyTrustScore(score);
		familyRepo.save(f);
	}

	@Override
	public Family addFeedback(String familyId, FamilyFeedback feedback) {
		Family f = getFamilyById(familyId);
		if (f.getFeedbacks() == null)
			f.setFeedbacks(new ArrayList<>());
		feedback.setCreatedAt(LocalDateTime.now().toString());
		f.getFeedbacks().add(feedback);
		Family updated = familyRepo.save(f);
		calculateTrustScore(familyId);
		return updated;
	}

	@Override
	public List<FamilyFeedback> getFeedback(String familyId) {
		return getFamilyById(familyId).getFeedbacks();
	}

}
