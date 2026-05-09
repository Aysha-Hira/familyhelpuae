package com.familyhelpuae.request.service;

import java.util.List;

import com.familyhelpuae.request.model.Request;
import com.familyhelpuae.request.model.RequestStatus;
import com.familyhelpuae.request.model.RequestType;
import com.familyhelpuae.request.model.UrgencyLevel;

public interface RequestService {
	
	//CRUD
	Request createRequest(Request request);
    Request getRequestById(String requestId);
    List<Request> getAllRequests();
    Request updateRequest(Request newRequest, String requestId);
    void deleteRequest(String requestId);
    
    //Custom
    List<Request> getRequestsByFamily(String familyId);
    
    List<Request> getRequestsByStatus(RequestStatus status);
    Request updateStatus(String requestId, RequestStatus status);
    
    List<Request> getRequestsByType(RequestType requestType);
    
    List<Request> getRequestsByUrgency(UrgencyLevel urgencyLevel);
    
    List<Request> getRequestsByTitle(String title);
    
    
    // TODO: when offers are implemented - call this to link an offer to a request
    // Request matchOffer(String requestId, String offerId);
    
    // TODO: when interaction history implemented - on completion call InteractionHistoryService.save()
    // TODO: when trust score implemented - on completion call FamilyService.setTrustScore()
    
    

}
