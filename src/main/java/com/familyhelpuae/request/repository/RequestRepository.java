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
