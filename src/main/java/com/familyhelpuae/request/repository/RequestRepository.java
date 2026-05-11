/**
 * Section: 104
 * Group number: 4
 * Student IDs and names: 
 * Laisa Sanjida Isra: 1089635
 * Fatima Syed Wasti: 1095190
 * Aysha Hira: 1088000
 */

package com.familyhelpuae.request.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.familyhelpuae.request.model.Request;

public interface RequestRepository extends MongoRepository<Request, String> {
    List<Request> findByRequestingFamilyId(String familyId);

    List<Request> findByRequestStatus(String status);

    List<Request> findByRequestType(String requestType);

    List<Request> findByUrgencyLevel(String urgencyLevel);

    List<Request> findByRequestTitle(String title);
}
