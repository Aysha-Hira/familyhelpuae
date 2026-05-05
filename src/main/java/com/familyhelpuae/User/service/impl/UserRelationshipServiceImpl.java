package com.familyhelpuae.user.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.familyhelpuae.exception.ResourceNotFound;
import com.familyhelpuae.user.model.User;
import com.familyhelpuae.user.model.UserRelationship;
import com.familyhelpuae.user.repository.UserRepository;
import com.familyhelpuae.user.service.UserRelationshipService;

@Service
public class UserRelationshipServiceImpl implements UserRelationshipService {

    private final UserRepository userRepository;

    public UserRelationshipServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserRelationship> getAllRelationships(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFound("User", "id", userId));

        return user.getRelationships();
    }

    @Override
    public List<UserRelationship> getRelationshipsByType(String userId, String relationshipType) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFound("User", "id", userId));

        return user.getRelationshipsByType(relationshipType);
    }

    public boolean hasRelationshipWithRegisteredUser(String userId, String anotherUserId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFound("User", "id", userId));
        return user.hasRelationship(anotherUserId);
    }

    public boolean hasRelationshipWithNonRegisteredUser(String userId, String name, String email) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFound("User", "id", userId));
        return user.hasRelationship(name, email);
    }

    @Override
    public User addRelationship(String userId, String anotherUserID, String relationshipType) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFound("User", "id", userId));

        userRepository.findById(anotherUserID)
                .orElseThrow(() -> new ResourceNotFound("User", "id", anotherUserID));

        if (user.hasRelationship(anotherUserID))
            throw new IllegalArgumentException(
                    "Relationship already exists between user " + userId + " and user " + anotherUserID);

        // add relationship to user
        user.getRelationships().add(new UserRelationship(anotherUserID, relationshipType));

        // save updated user
        return userRepository.save(user);
    }

    public User addRelationship(String userId, String name, String email, String relationshipType) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFound("User", "id", userId));

        if (user.hasRelationship(name, email))
            throw new IllegalArgumentException(
                    "Relationship already exists between user " + userId + " and non-registered user with name "
                            + name + " and email " + email);

        // add relationship to user
        user.getRelationships().add(new UserRelationship(name, email, relationshipType));

        // save updated user
        return userRepository.save(user);
    }

    @Override
    public User updateRelationship(String userId, String anotherUserID, String relationshipType) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFound("User", "id", userId));

        userRepository.findById(anotherUserID)
                .orElseThrow(() -> new ResourceNotFound("User", "id", anotherUserID));

        if (!user.hasRelationship(anotherUserID))
            throw new IllegalArgumentException(
                    "Relationship does not exist between user " + userId + " and user " + anotherUserID);

        // update relationship
        user.updateRelationship(anotherUserID, relationshipType);
        // save updated user
        return userRepository.save(user);

    }

    public User updateRelationship(String userId, String name, String email, String relationshipType) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFound("User", "id", userId));

        if (!user.hasRelationship(name, email))
            throw new IllegalArgumentException(
                    "Relationship does not exist between user " + userId + " and non-registered user with name "
                            + name + " and email " + email);

        // update relationship
        user.updateRelationship(name, email, relationshipType);
        // save updated user
        return userRepository.save(user);

    }

    @Override
    public void removeRelationship(String userId, String anotherUserID) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFound("User", "id", userId));

        if (!user.hasRelationship(anotherUserID)) {
            throw new IllegalArgumentException(
                    "Relationship does not exist between user " + userId + " and user " + anotherUserID);
        }

        user.deleteRelationship(anotherUserID);

        userRepository.save(user);
    }

    public void removeRelationship(String userId, String name, String email) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFound("User", "id", userId));

        if (!user.hasRelationship(name, email)) {
            throw new IllegalArgumentException(
                    "Relationship does not exist between user " + userId + " and non-registered user with name "
                            + name + " and email " + email);
        }

        user.deleteRelationship(name, email);

        userRepository.save(user);
    }

}
