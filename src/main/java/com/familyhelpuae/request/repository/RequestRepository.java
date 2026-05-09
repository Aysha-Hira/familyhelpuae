package com.familyhelpuae.request.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.familyhelpuae.request.model.Request;
import com.familyhelpuae.request.model.RequestStatus;
import com.familyhelpuae.request.model.RequestType;
import com.familyhelpuae.request.model.UrgencyLevel;

public interface RequestRepository extends MongoRepository<Request, String> {
    List<Request> findByRequestingFamilyId(String familyId); 
    List<Request> findByRequestStatus(RequestStatus status);
    List<Request> findByRequestType(RequestType requestType);
    List<Request> findByUrgencyLevel(UrgencyLevel urgencyLevel);
    List<Request> findByRequestTitle(String title);
}
