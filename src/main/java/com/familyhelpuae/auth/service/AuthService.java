package com.familyhelpuae.auth.service;

import com.familyhelpuae.auth.model.Register;
import com.familyhelpuae.user.model.User;

public interface AuthService {
    public User buildUserFromRegister(Register pending);

    public User completeVerifiedRegistration(Register pending);

    public User completeRegistrationWithoutRelationship(Register pending);

    public User addRelationship(User savedUser, Register dto);

}
