package com.familyhelpuae.family.service;

import java.util.List;

import com.familyhelpuae.exception.DuplicateFamilyIDException;
import com.familyhelpuae.family.model.Family;
import com.familyhelpuae.family.model.FamilyMember;

public interface FamilyService {
	
	List<Family> getAllFamilies();
	
	//CRUD
	Family saveFamily(Family newFamily) throws DuplicateFamilyIDException;
	Family getFamilyById(String familyId);
	Family updateFamily(Family newFamily, String familyId);
	void deleteFamily(String familyId);
	
	//Custom
	List<FamilyMember> getFamilyAdmins(String familyId);
	List<Family> getFamiliesByName(String familyName); //for user-side searching
	
	Family addMember(String familyId, FamilyMember member);
	Family removeMember(String familyId, FamilyMember FamilyMember);
	List<FamilyMember> getActiveMembers(String familyId);
	//FamilyMember getFamilyMemberById(String familyMember);
	
	double getTrustScore(String familyId);
	void setTrustScore(String familyId, double score);
	
}
