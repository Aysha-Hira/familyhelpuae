package com.familyhelpuae.user.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.familyhelpuae.exception.DuplucateEmailException;
import com.familyhelpuae.exception.ResourceNotFound;
import com.familyhelpuae.user.model.User;
import com.familyhelpuae.user.repository.UserRepository;
import com.familyhelpuae.user.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User addUser(User user) {
        // Check if email already exists
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DuplucateEmailException("Email already exists: " + user.getEmail());
        }

        // Set default values
        user.setVerified(false);
        user.setHasAccount(true);
        user.setNewlyRegistered(true);
        user.setTrustScore(0.0);
        user.setConsecutiveBadRatings(0);
        user.setTotalInteractions(0);

        // Initialize empty lists
        user.setFamilies(new ArrayList<>());
        user.setRelationships(new ArrayList<>());

        // Set timestamps
        LocalDateTime now = LocalDateTime.now();
        user.setCreatedAt(now);
        user.setUpdatedAt(now);

        return userRepository.save(user);
    }

    @Override
    public User getUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("User", "id", id));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Need to add the user families and relationships in the update method as well,
    // but for now we will
    @Override
    public User update(String id, User user) {
        User existingUser = getUserById(id);

        // Update only non-null fields
        if (user.getFirstName() != null)
            existingUser.setFirstName(user.getFirstName());

        if (user.getLastName() != null)
            existingUser.setLastName(user.getLastName());

        if (userRepository.existsByEmail(user.getEmail()))
            throw new DuplucateEmailException("Email already exists: " + user.getEmail());

        if (user.getEmail() != null && !existingUser.getEmail().equals(user.getEmail()))
            existingUser.setEmail(user.getEmail());

        if (user.getPhone() != null)
            existingUser.setPhone(user.getPhone());

        if (user.getLanguagesSpoken() != null)
            existingUser.setLanguagesSpoken(user.getLanguagesSpoken());

        if (user.getNationality() != null)
            existingUser.setNationality(user.getNationality());

        if (user.getProfilePictureUrl() != null)
            existingUser.setProfilePictureUrl(user.getProfilePictureUrl());

        existingUser.setUpdatedAt(LocalDateTime.now());

        return userRepository.save(existingUser);
    }

    @Override
    public void delete(String id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFound("User", "id", id);
        }
        userRepository.deleteById(id);
    }
}