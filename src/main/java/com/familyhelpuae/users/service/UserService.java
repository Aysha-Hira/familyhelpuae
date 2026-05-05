package com.familyhelpuae.users.service;

import java.util.List;

import com.familyhelpuae.users.model.User;

public interface UserService {
    User addUser(User user);

    User getUserById(String id);

    List<User> getAllUsers();

    User update(String id, User user);

    void delete(String id);
}
