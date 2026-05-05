package com.familyhelpuae.user.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.familyhelpuae.user.model.User;

public interface UserRepository extends MongoRepository<User, String> {

    boolean existsByEmail(String email);

}
