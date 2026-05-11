/**
 * Section: 104
 * Group number: 4
 * Student IDs and names: 
 * Laisa Sanjida Isra: 1089635
 * Fatima Syed Wasti: 1095190
 * Aysha Hira: 1088000
 */

package com.familyhelpuae.family.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.familyhelpuae.family.model.Family;

public interface FamilyRepository extends MongoRepository<Family, String> {

	List<Family> findByFamilyName(String familyName);

}
