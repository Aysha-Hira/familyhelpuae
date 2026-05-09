package com.familyhelpuae.exception;

public class DuplicateFamilyIDException extends Exception {
	private String familyId;
	
	public DuplicateFamilyIDException(String familyId) {
        super("Email already exists");
        this.familyId = familyId;
    }

    public String getFamilyId() {
        return familyId;
    }

}
