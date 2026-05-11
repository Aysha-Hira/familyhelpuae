/**
 * Section: 104
 * Group number: 4
 * Student IDs and names: 
 * Laisa Sanjida Isra: 1089635
 * Fatima Syed Wasti: 1095190
 * Aysha Hira: 1088000
 */

package com.familyhelpuae.family.service;

import java.util.List;

import com.familyhelpuae.family.model.Family;
import com.familyhelpuae.family.model.FamilyFeedback;
import com.familyhelpuae.family.model.FamilyMember;

public interface FamilyService {

	List<Family> getAllFamilies();

	// CRUD
	Family saveFamily(Family newFamily);

	Family getFamilyById(String familyId);

	Family updateFamily(Family newFamily, String familyId);

	void deleteFamily(String familyId);

	// Custom
	List<FamilyMember> getFamilyAdmins(String familyId);

	List<Family> getFamiliesByName(String familyName); // for user-side searching

	Family addMember(String familyId, FamilyMember member);

	Family removeMember(String familyId, FamilyMember FamilyMember);

	List<FamilyMember> getActiveMembers(String familyId);

	double getTrustScore(String familyId);

	void setTrustScore(String familyId, double score);

	void calculateTrustScore(String familyId);

	Family addFeedback(String familyId, FamilyFeedback feedback);

	List<FamilyFeedback> getFeedback(String familyId);

}
