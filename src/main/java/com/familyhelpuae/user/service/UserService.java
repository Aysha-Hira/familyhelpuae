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
