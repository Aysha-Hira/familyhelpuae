package com.familyhelpuae.user.service;

import java.util.List;

import com.familyhelpuae.user.model.User;
import com.familyhelpuae.user.model.UserFamily;

public interface UserFamilyService {
    List<UserFamily> getAllFamilies(String userId);

    User addFamily(String userId, String familyId, String role);

    User updateFamily(String userId, String familyId, String role);

    void removeFamily(String userId, String familyId);

    boolean isRelatedToFamily(String userId, String familyId);

}