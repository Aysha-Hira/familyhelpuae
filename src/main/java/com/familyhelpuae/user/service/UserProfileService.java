package com.familyhelpuae.user.service;

import java.util.List;

import com.familyhelpuae.user.model.User;

public interface UserProfileService {
    List<User> getFamilyMembers(String userId);
}