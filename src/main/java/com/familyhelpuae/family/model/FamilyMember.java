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
}