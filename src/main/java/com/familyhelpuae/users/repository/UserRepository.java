package com.familyhelpuae.users.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.familyhelpuae.users.model.User;

public interface UserRepository extends MongoRepository<User, String> {

    boolean existsByEmail(String email);

}
