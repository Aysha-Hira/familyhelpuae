/**
 * Section: 104
 * Group number: 4
 * Student IDs and names: 
 * Laisa Sanjida Isra: 1089635
 * Fatima Syed Wasti: 1095190
 * Aysha Hira: 1088000
 */

package com.familyhelpuae.auth.service;

import com.familyhelpuae.DTO.Register;
import com.familyhelpuae.user.model.User;

public interface AuthService {
    public User buildUserFromRegister(Register pending);

    public User completeVerifiedRegistration(Register pending);

    public User completeRegistrationWithoutRelationship(Register pending);

    public User addRelationship(User savedUser, Register dto);

    public User login(String email, String password);

    public boolean isEmailExisting(String email);

    public Register addFamilies(Register dto, Register dto2);

}
