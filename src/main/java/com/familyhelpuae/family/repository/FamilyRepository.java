package com.familyhelpuae.family.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.familyhelpuae.family.model.Family;

public interface FamilyRepository extends MongoRepository<Family, String> {

	List<Family> findByFamilyName(String familyName);
	
}
