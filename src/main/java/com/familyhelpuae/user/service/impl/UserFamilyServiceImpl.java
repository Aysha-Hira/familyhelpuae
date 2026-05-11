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

import org.springframework.stereotype.Service;

import com.familyhelpuae.exception.ResourceNotFound;
import com.familyhelpuae.family.model.Family;
import com.familyhelpuae.family.repository.FamilyRepository;
import com.familyhelpuae.user.model.User;
import com.familyhelpuae.user.model.UserFamily;
import com.familyhelpuae.user.repository.UserRepository;
import com.familyhelpuae.user.service.UserFamilyService;

@Service
public class UserFamilyServiceImpl implements UserFamilyService {

    private final UserRepository userRepository;
    private final FamilyRepository familyRepository;

    public UserFamilyServiceImpl(UserRepository userRepository, FamilyRepository familyRepository) {
        this.userRepository = userRepository;
        this.familyRepository = familyRepository;
    }

    @Override
    public List<UserFamily> getAllFamilies(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFound("User", "id", userId));

        return user.getFamilies();
    }

    @Override
    public User addFamily(String userId, String familyId, String role) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFound("User", "id", userId));

        familyRepository.findById(familyId)
                .orElseThrow(() -> new ResourceNotFound("Family", "id", familyId));

        if (user.hasFamily(familyId))
            throw new IllegalArgumentException("User " + userId + " is already a member of family " + familyId);

        // add family to user
        user.getFamilies().add(new UserFamily(familyId, role));

        // save updated user
        return userRepository.save(user);
    }

    @Override
    public User updateFamily(String userId, String familyId, String role) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFound("User", "id", userId));

        familyRepository.findById(familyId).orElseThrow(() -> new ResourceNotFound("Family", "id", familyId));

        if (!user.hasFamily(familyId))
            throw new IllegalArgumentException(
                    "User " + userId + " is not a member of family " + familyId);

        // update relationship
        user.updateFamily(familyId, role);

        // save updated user
        return userRepository.save(user);

    }

    @Override
    public void removeFamily(String userId, String familyId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFound("User", "id", userId));

        if (!user.hasFamily(familyId)) {
            throw new IllegalArgumentException(
                    "User " + userId + " is not a member of family " + familyId);
        }

        user.deleteFamily(familyId);

        userRepository.save(user);
    }

    @Override
    public boolean isRelatedToFamily(String userId, String familyId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFound("User", "id", userId));

        Family family = familyRepository.findById(familyId)
                .orElseThrow(() -> new ResourceNotFound("Family", "id", familyId));

        return user.hasFamily(familyId);

    }
}
