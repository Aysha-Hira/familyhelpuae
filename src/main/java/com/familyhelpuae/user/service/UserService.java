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

public interface UserService {
    User addUser(User user);

    User getUserById(String id);

    List<User> getAllUsers();

    User update(String id, User user);

    void delete(String id);

    boolean authenticate(String email, String password);

    void updateTrustScore(String userId, double rating);
}
