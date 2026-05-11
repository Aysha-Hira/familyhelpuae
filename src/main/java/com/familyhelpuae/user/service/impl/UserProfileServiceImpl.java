/**
 * Section: 104
 * Group number: 4
 * Student IDs and names: 
 * Laisa Sanjida Isra: 1089635
 * Fatima Syed Wasti: 1095190
 * Aysha Hira: 1088000
 */

package com.familyhelpuae.user.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.familyhelpuae.exception.ResourceNotFound;
import com.familyhelpuae.family.model.Family;
import com.familyhelpuae.family.model.FamilyMember;
import com.familyhelpuae.family.repository.FamilyRepository;
import com.familyhelpuae.user.model.User;
import com.familyhelpuae.user.model.UserFamily;
import com.familyhelpuae.user.repository.UserRepository;
import com.familyhelpuae.user.service.UserProfileService;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    private final FamilyRepository familyRepository;
    private final UserRepository userRepository;

    public UserProfileServiceImpl(FamilyRepository familyRepository, UserRepository userRepository) {
        this.familyRepository = familyRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getFamilyMembers(String userId) {
        // 1. Load the current user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFound("User", "userId", userId));

        // 2. Collect all familyIds the user belongs to
        List<String> familyIds = user.getFamilies().stream()
                .map(UserFamily::getFamilyId)
                .collect(Collectors.toList());

        // 3. Load all those families
        List<Family> families = familyRepository.findAllById(familyIds);

        // 4. Collect all member IDs across all families, excluding the current user
        List<String> memberIds = families.stream()
                .flatMap(fam -> fam.getMembers().stream())
                .map(FamilyMember::getFamilyMemberId)
                .filter(id -> !id.equals(userId))
                .distinct()
                .collect(Collectors.toList());

        // 5. Look up those users by ID
        return userRepository.findAllById(memberIds);
    }
}