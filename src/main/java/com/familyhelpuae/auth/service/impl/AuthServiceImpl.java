package com.familyhelpuae.auth.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.familyhelpuae.DTO.Register;
import com.familyhelpuae.DTO.Relationship;
import com.familyhelpuae.auth.service.AuthService;
import com.familyhelpuae.exception.UserNotFoundException;
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

    @Override
    @Transactional
    public User completeRegistrationWithoutRelationship(Register pending) {

        User user = buildUserFromRegister(pending);

        user.setVerified(false);

        return UserServiceImpl.addUser(user);
    }

    @Override
    public User addRelationship(User savedUser, Register pending) {

        if (pending.getRelationships() == null) {
            return savedUser;
        }

        for (Relationship relationship : pending.getRelationships()) {

            Optional<User> relatedUser = Optional.empty();

            // 1. check by userId
            if (relationship.getRelatedUserId() != null && !relationship.getRelatedUserId().isBlank()) {
                relatedUser = UserRepository.findById(relationship.getRelatedUserId());
            }

            // 2. check by email
            else if (relationship.getRelatedEmail() != null && !relationship.getRelatedEmail().isBlank()) {
                relatedUser = UserRepository.findByEmail(relationship.getRelatedEmail());
            }

            // 3. registered user
            if (relatedUser.isPresent()) {

                UserRelationshipService.addRelationship(
                        savedUser.getUserID(),
                        relatedUser.get().getUserID(),
                        relationship.getRelationshipType());

            }
            // 4. non-registered user
            else if (relationship.getRelatedName() != null && !relationship.getRelatedName().isBlank()
                    && relationship.getRelatedEmail() != null && !relationship.getRelatedEmail().isBlank()) {

                UserRelationshipService.addRelationship(
                        savedUser.getUserID(),
                        relationship.getRelatedName(),
                        relationship.getRelatedEmail(),
                        relationship.getRelationshipType());

            } else {
                throw new IllegalArgumentException("Invalid relationship data");
            }
        }

        return savedUser;
    }

    private boolean hasRelationshipData(Register dto) {
        return dto.getRelationships() != null && !dto.getRelationships().isEmpty();
    }

    // @Override
    @Override
    @Transactional
    public User completeVerifiedRegistration(Register pending) {

        User user = buildUserFromRegister(pending);
        user.setVerified(true);

        User savedUser = UserServiceImpl.addUser(user);

        if (hasRelationshipData(pending)) {
            addRelationship(savedUser, pending);
        }

        return savedUser;
    }

    @Override
    public User login(String email, String password) {
        if (UserServiceImpl.authenticate(email, password)) {
            return UserRepository.findByEmail(email)
                    .orElseThrow(() -> new UserNotFoundException(email));
        }

        return null;
    }

}
