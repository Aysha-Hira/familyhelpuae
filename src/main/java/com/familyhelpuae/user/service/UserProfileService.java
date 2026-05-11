/**
 * Section: 104
 * Group number: 4
 * Student IDs and names: 
 * Laisa Sanjida Isra: 1089635
 * Fatima Syed Wasti: 1095190
 * Aysha Hira: 1088000
 */

package com.familyhelpuae.user.service;

import java.util.List;

import com.familyhelpuae.user.model.User;

public interface UserProfileService {
    List<User> getFamilyMembers(String userId);
}