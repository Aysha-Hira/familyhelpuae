/**
 * Section: 104
 * Group number: 4
 * Student IDs and names: 
 * Laisa Sanjida Isra: 1089635
 * Fatima Syed Wasti: 1095190
 * Aysha Hira: 1088000
 */

package com.familyhelpuae.family.model;

import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
public class FamilyMember {

	String familyMemberId; // the id of the user who is a member of the family

	@Field(name = "isAdmin")
	Boolean isAdmin; // can be either admin or member

	@Field(name = "isUser")
	Boolean isUser; // searchable/interactable if true

	@Field(name = "memberTrustScore")
	double memberTrustScore; // individual trust score for this member, updated after each interaction

	@Field(name = "completedInteractions")
	int completedInteractions; // how many interactions this member has completed

	@Field(name = "totalRating")
	double totalRating; // sum of all ratings received, used to calculate average
}