package com.familyhelpuae.family.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.familyhelpuae.exception.DuplicateFamilyIDException;
import com.familyhelpuae.exception.ResourceNotFound;
import com.familyhelpuae.family.model.Family;
import com.familyhelpuae.family.model.FamilyMember;
import com.familyhelpuae.family.repository.FamilyRepository;
import com.familyhelpuae.family.service.FamilyService;

@Service
public class FamilyServiceImpl implements FamilyService {
	
	private final FamilyRepository familyRepo;
	
	public FamilyServiceImpl(FamilyRepository familyRepo) {
		this.familyRepo = familyRepo;
	}

	@Override
	public List<Family> getAllFamilies() {
		return familyRepo.findAll();
	}

	@Override
	public Family saveFamily(Family newFamily) {
		newFamily.setFamilyId(null);
		newFamily.setFamilyTrustScore(0.0);
		
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
		
		if(newFamily.getFamilyName() != null) existing.setFamilyName(newFamily.getFamilyName());
		if(newFamily.getCountry() != null) existing.setCountry(newFamily.getCountry());
		if(newFamily.getState() != null) existing.setState(newFamily.getState());
		if(newFamily.getMembers() != null) existing.setMembers(newFamily.getMembers());
		
		existing.setUpdatedAt(LocalDateTime.now());
		
		return familyRepo.save(existing);
	}

	// TODO: when offers/requests are implemented - should also delete or archive all offers/requests belonging to this family
	@Override
	public void deleteFamily(String familyId) {
		if (!familyRepo.existsById(familyId)) {
            throw new ResourceNotFound("Family", "familyId", familyId);
        }
		familyRepo.deleteById(familyId);
		
	}


	@Override
	public List<Family> getFamiliesByName(String familyName) {
		//TODO: Implement in Controller
		return familyRepo.findByFamilyName(familyName);
	}
	
	// TODO: Add authorization check - only family admin should be able to add members
	// TODO: Implement join request flow (user sends request, admin approves)
	// TODO: when partner completes UserFamilyService - also call userFamilyService.addFamily(member.getFamilyMemberId(), familyId, role)
	// TODO: check if member already exists in family before adding (prevent duplicates)
	@Override
	public Family addMember(String familyId, FamilyMember member) {
		Family f = getFamilyById(familyId);
		List<FamilyMember> members = f.getMembers();
		members.add(member);
		
		f.setMembers(members);
		
		return updateFamily(f, familyId);
		
	}
	
	// TODO: when partner completes UserFamilyService - also call userFamilyService.removeFamily(member.getFamilyMemberId(), familyId)
	@Override
	public Family removeMember(String familyId, FamilyMember member) {
		Family f = getFamilyById(familyId);
		List<FamilyMember> m = f.getMembers();
		m.remove(member);
		
		f.setMembers(m);
		
		return updateFamily(f, familyId);
	}
	
	@Override
	public List<FamilyMember> getActiveMembers(String familyId) {
		Family f = getFamilyById(familyId);
		List<FamilyMember> members = f.getMembers();
		
		List<FamilyMember> users = new ArrayList<>();
		
		for(FamilyMember member : members) {
			if(member.getIsUser()) users.add(member);
		}
		
		return users;
	}
	
	@Override
	public List<FamilyMember> getFamilyAdmins(String familyId) {
		 Family f = getFamilyById(familyId);
		 List<FamilyMember> members = f.getMembers();
		 
		 List<FamilyMember> admins = new ArrayList<>();
		 
		 for(FamilyMember member : members) {
			 if(member.getIsAdmin()) admins.add(member);
		 }
		 
		 return admins;
	}

	@Override
	public double getTrustScore(String familyId) {
		return getFamilyById(familyId).getFamilyTrustScore();
	}
	
	// TODO: called by feedback/interaction service once implemented - do not expose as endpoint
	@Override
	public void setTrustScore(String familyId, double score) {
		Family f = getFamilyById(familyId);
		f.setFamilyTrustScore(score);
		
		updateFamily(f, familyId);
	}

	
	

}
