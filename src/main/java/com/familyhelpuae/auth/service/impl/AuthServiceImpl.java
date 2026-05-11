package com.familyhelpuae.auth.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.familyhelpuae.DTO.Family;
import com.familyhelpuae.DTO.Register;
import com.familyhelpuae.DTO.Relationship;
import com.familyhelpuae.auth.service.AuthService;
import com.familyhelpuae.exception.UserNotFoundException;
import com.familyhelpuae.family.service.FamilyService;
import com.familyhelpuae.user.model.User;
import com.familyhelpuae.user.repository.UserRepository;
import com.familyhelpuae.user.service.UserRelationshipService;
import com.familyhelpuae.user.service.UserService;

@Service
@Validated
public class AuthServiceImpl implements AuthService {
    UserService UserService;
    UserRelationshipService UserRelationshipService;
    UserRepository UserRepository;
    FamilyService familyService;

    public AuthServiceImpl(UserService UserService, UserRelationshipService UserRelationshipService,
            UserRepository UserRepository, FamilyService familyService) {
        this.UserService = UserService;
        this.UserRelationshipService = UserRelationshipService;
        this.UserRepository = UserRepository;
        this.familyService = familyService;
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

        return UserService.addUser(user);
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

            }
        }

        return savedUser;
    }

    private boolean hasRelationshipData(Register dto) {
        return dto.getRelationships() != null && !dto.getRelationships().isEmpty();
    }

    // @Override
    // field
    // import from your family package

    @Override
    public User login(String email, String password) {
        if (UserService.authenticate(email, password)) {
            return UserRepository.findByEmail(email)
                    .orElseThrow(() -> new UserNotFoundException(email));
        }

        return null;
    }

    @Override
    public boolean isEmailExisting(String email) {
        if (UserRepository.existsByEmail(email))
            return true;

        return false;
    }

    @Override
    public Register addFamilies(Register pendingUser, Register newDatawithFamilies) {
        boolean isjoiningafamily = false;
        for (Family family : newDatawithFamilies.getFamilies()) {
            if (family.isCreateFamily()) {
                // User is creating a new family — store it and move on
                pendingUser.setCreateFamily(true);
                pendingUser.getFamilies().add(family);

            } else if (family.getFamilyCode() != null && !family.getFamilyCode().isEmpty()) {
                // User wants to join an existing family by code
                pendingUser.getFamilies().add(family);
                isjoiningafamily = true;
            }
        }

        if (isjoiningafamily)
            pendingUser.setCreateFamily(false);

        return pendingUser;
    }

    @Override
    @Transactional
    public User completeVerifiedRegistration(Register pending) {

        User user = buildUserFromRegister(pending);
        user.setVerified(true);

        User savedUser = UserService.addUser(user);

        if (hasRelationshipData(pending)) {
            addRelationship(savedUser, pending);
        }

        if (pending.isCreateFamily() && pending.getFamilies() != null) {
            for (Family familyDTO : pending.getFamilies()) {
                if (familyDTO.isCreateFamily()) {

                    // 1. Build the Family model from the DTO
                    com.familyhelpuae.family.model.Family newFamily = new com.familyhelpuae.family.model.Family();
                    newFamily.setFamilyName(familyDTO.getFamilyName());

                    // 2. Save it
                    com.familyhelpuae.family.model.Family savedFamily = null;
                    savedFamily = familyService.saveFamily(newFamily);

                    // 3. Add the registering user as admin
                    com.familyhelpuae.family.model.FamilyMember member = new com.familyhelpuae.family.model.FamilyMember();
                    member.setFamilyMemberId(savedUser.getUserID());
                    member.setIsAdmin(true);
                    member.setIsUser(true);

                    familyService.addMember(savedFamily.getFamilyId(), member);
                }
            }
        }

        return savedUser;
    }
}
