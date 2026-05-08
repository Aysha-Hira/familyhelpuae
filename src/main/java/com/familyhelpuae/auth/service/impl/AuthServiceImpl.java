package com.familyhelpuae.auth.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.familyhelpuae.auth.model.Register;
import com.familyhelpuae.auth.service.AuthService;
import com.familyhelpuae.user.model.User;
import com.familyhelpuae.user.repository.UserRepository;
import com.familyhelpuae.user.service.impl.UserRelationshipServiceImpl;
import com.familyhelpuae.user.service.impl.UserServiceImpl;

@Service
@Validated
public class AuthServiceImpl implements AuthService {

    UserServiceImpl UserServiceImpl;
    UserRelationshipServiceImpl UserRelationshipService;
    UserRepository UserRepository;

    public AuthServiceImpl(UserServiceImpl UserServiceImpl, UserRelationshipServiceImpl UserRelationshipService,
            UserRepository UserRepository) {
        this.UserServiceImpl = UserServiceImpl;
        this.UserRelationshipService = UserRelationshipService;
        this.UserRepository = UserRepository;
    }

    @Override
    @Transactional
    public User buildUserFromRegister(Register pending) {
        User user = new User();
        user.setFirstName(pending.getFirstName());
        user.setLastName(pending.getLastName());
        user.setPhone(pending.getPhone());
        user.setEmail(pending.getEmail());
        user.setPassword(pending.getPassword());
        return user;
    }

    @Transactional
    @Override
    public User completeRegistrationWithoutRelationship(Register pending) {
        return buildUserFromRegister(pending);
    }

    @Override
    public User addRelationship(User savedUser, Register relationship) {
        // Check if the related person is already registered
        Optional<User> relatedUser = UserRepository.findByEmail(relationship.getRelatedEmail());

        if (relatedUser.isPresent()) {
            // Both are registered users
            UserRelationshipService.addRelationship(
                    savedUser.getUserID(),
                    relatedUser.get().getUserID(),
                    relationship.getRelationshipType());
        } else if (relationship.getRelatedName() != null && relationship.getRelatedEmail() != null) {
            // Related person is not registered yet - store as pending relationship
            UserRelationshipService.addRelationship(
                    savedUser.getUserID(),
                    relationship.getRelatedName(),
                    relationship.getRelatedEmail(),
                    relationship.getRelationshipType());
        }
        return savedUser;
    }

    private boolean hasRelationshipData(Register dto) {
        return dto.getRelatedEmail() != null && !dto.getRelatedEmail().isEmpty();
    }

    // @Override
    @Override
    @Transactional
    public User completeVerifiedRegistration(Register pending) {

        // Build user
        User user = buildUserFromRegister(pending);

        // Mark verified
        user.setVerified(true);

        // Save user ONCE
        User savedUser = UserServiceImpl.addUser(user);

        // Add relationship if exists
        if (hasRelationshipData(pending)) {
            addRelationship(savedUser, pending);
        }

        return savedUser;
    }

}
