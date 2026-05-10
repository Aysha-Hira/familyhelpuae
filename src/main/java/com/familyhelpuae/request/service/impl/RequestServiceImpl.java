package com.familyhelpuae.request.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.familyhelpuae.exception.ResourceNotFound;
import com.familyhelpuae.request.model.*;
import com.familyhelpuae.request.repository.RequestRepository;
import com.familyhelpuae.request.service.RequestService;

@Service
public class RequestServiceImpl implements RequestService {
	
	private final RequestRepository requestRepo;
	
	public RequestServiceImpl(RequestRepository requestRepo) {
		this.requestRepo = requestRepo;
	}

	@Override
	public Request createRequest(Request request) {
		// TODO: when auth is integrated - validate that requestingFamilyId belongs to the logged-in user
		request.setRequestStatus("OPEN");
		LocalDateTime now = LocalDateTime.now();
		request.setCreatedAt(now);
		request.setUpdatedAt(now);
		return requestRepo.save(request);
	}

	@Override
	public Request getRequestById(String requestId) {
		return requestRepo.findById(requestId)
				.orElseThrow(() -> new ResourceNotFound("Request", "requestId", requestId));
	}

	@Override
	public List<Request> getAllRequests() {
		return requestRepo.findAll();
	}

	@Override
	public Request updateRequest(Request newRequest, String requestId) {
		Request existing = getRequestById(requestId);
		
		if(newRequest.getRequestDescription() != null) existing.setRequestDescription(newRequest.getRequestDescription());
		if(newRequest.getLocation() != null) existing.setLocation(newRequest.getLocation());
		if(newRequest.getRequestType() != null) existing.setRequestType(newRequest.getRequestType());
		if(newRequest.getRequestStatus() != null) existing.setRequestStatus(newRequest.getRequestStatus());
		if(newRequest.getUrgencyLevel() != null) existing.setUrgencyLevel(newRequest.getUrgencyLevel());
		
		existing.setUpdatedAt(LocalDateTime.now());
		
		return requestRepo.save(existing);
	}

	@Override
	public void deleteRequest(String requestId) {
		// TODO: check if request exists before deleting, throw ResourceNotFound if not
		// (currently silently does nothing if ID doesn't exist)
		requestRepo.deleteById(requestId);
		
	}

	@Override
	public Request updateStatus(String requestId, String status) {
		Request existing = getRequestById(requestId);
		
        existing.setRequestStatus(status);
        existing.setUpdatedAt(LocalDateTime.now());
        // TODO: if status == "completed" -> call InteractionHistoryService.save()
        // TODO: if status == "completed" -> call FamilyService.setTrustScore() for both families
        return requestRepo.save(existing);
	}

	@Override
	public List<Request> getRequestsByFamily(String familyId) {
		return requestRepo.findByRequestingFamilyId(familyId);
	}

	@Override
	public List<Request> getRequestsByStatus(String status) {
	    return requestRepo.findByRequestStatus(status);
	}

	@Override
	public List<Request> getRequestsByType(String requestType) {
	    return requestRepo.findByRequestType(requestType);
	}

	@Override
	public List<Request> getRequestsByUrgency(String urgencyLevel) {
	    return requestRepo.findByUrgencyLevel(urgencyLevel);
	}

	@Override
	public List<Request> getRequestsByTitle(String title) {
		return requestRepo.findByRequestTitle(title);
	}

}
