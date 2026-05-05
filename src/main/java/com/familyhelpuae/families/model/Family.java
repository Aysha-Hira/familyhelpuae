package com.familyhelpuae.families.model;

import java.util.List;

public class Family {
    String id;
    String familyName;
    String country;
    String state;
    String city;
    double familyTrustScore;
    // this is for the family members, and their member roles
    List<FamilyMember> members;
    String createdAt;
    String updatedAt;
}

class FamilyMember {
    String familyMemberId; // the id of the user who is a member of the family
    String role; // "admin" or "member"
}
