package com.familyhelpuae.family.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.familyhelpuae.family.model.Family;

public interface FamilyRepository extends MongoRepository<Family, String> {

}
