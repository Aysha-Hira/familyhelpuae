package com.familyhelpuae.request.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.familyhelpuae.interactionhistory.service.interactionHistoryService;

import org.springframework.stereotype.Service;

import com.familyhelpuae.DTO.RequestResponseDTO;
import com.familyhelpuae.exception.ResourceNotFound;
import com.familyhelpuae.family.model.Family;
import com.familyhelpuae.family.service.FamilyService;
import com.familyhelpuae.request.model.*;
import com.familyhelpuae.request.repository.RequestRepository;
import com.familyhelpuae.request.service.RequestService;
import com.familyhelpuae.user.model.User;
import com.familyhelpuae.user.service.UserService;

@Service
public class RequestServiceImpl implements RequestService {
	
	private final RequestRepository requestRepo;
    private final UserService userService;
    private final FamilyService familyService;
    private final interactionHistoryService interactionHistoryService;

    public RequestServiceImpl(RequestRepository requestRepo,
                               UserService userService,
                               FamilyService familyService,
                               interactionHistoryService interactionHistoryService) {
        this.requestRepo = requestRepo;
        this.userService = userService;
        this.familyService = familyService;
        this.interactionHistoryService = interactionHistoryService;
    }

	@Override
	public Request createRequest(Request request) {
		// TODO: when auth is integrated - validate that requestingFamilyId belongs to the logged-in user
		request.setRequestId(null);
		request.setRequestStatus("open");
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
		if(newRequest.getUrgencyLevel() != null) existing.setUrgencyLevel(newRequest.getUrgencyLevel());
		
		existing.setUpdatedAt(LocalDateTime.now());
		
		return requestRepo.save(existing);
	}

	@Override
	public void deleteRequest(String requestId) {
	    if (!requestRepo.existsById(requestId)) {
	        throw new ResourceNotFound("Request", "requestId", requestId);
	    }
	    requestRepo.deleteById(requestId);
	}

	@Override
	public Request updateStatus(String requestId, String status) {
	    Request existing = getRequestById(requestId);
	    existing.setRequestStatus(status);
	    existing.setUpdatedAt(LocalDateTime.now());

	    if ("completed".equalsIgnoreCase(status)) {
	        String helpingFamilyId = (existing.getLinkedOfferIds() != null
	                && !existing.getLinkedOfferIds().isEmpty())
	                ? existing.getLinkedOfferIds().get(0) // this is offerId, not familyId
	                : null;

	        interactionHistoryService.recordInteraction(
	        	    helpingFamilyId != null ? helpingFamilyId : "unknown",
	        	    existing.getRequestingFamilyId(),
	        	    existing.getRequestType(),
	        	    "Completed request: " + existing.getRequestTitle(),
	        	    existing.getRequestId(),
	        	    existing.getLinkedOfferIds() != null && !existing.getLinkedOfferIds().isEmpty()
	        	        ? existing.getLinkedOfferIds().get(0) : null
	        	);

	        // Recalculate trust score for the requesting family
	        familyService.calculateTrustScore(existing.getRequestingFamilyId());
	    }

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
	
	@Override
	public Request linkOffer(String requestId, String offerId) {
	    Request request = getRequestById(requestId);
	    if (request.getLinkedOfferIds() == null) request.setLinkedOfferIds(new ArrayList<>());
	    if (!request.getLinkedOfferIds().contains(offerId)) {
	        request.getLinkedOfferIds().add(offerId);
	    }
	    request.setUpdatedAt(LocalDateTime.now());
	    return requestRepo.save(request);
	}

	@Override
	public Request unlinkOffer(String requestId, String offerId) {
	    Request request = getRequestById(requestId);
	    if (request.getLinkedOfferIds() != null) {
	        request.getLinkedOfferIds().remove(offerId);
	    }
	    request.setUpdatedAt(LocalDateTime.now());
	    return requestRepo.save(request);
	}
	
	@Override
	public List<RequestResponseDTO> getOpenRequestsEnriched() {
	    return requestRepo.findByRequestStatus("open").stream().map(this::toDTO).collect(Collectors.toList());
	}

	@Override
	public RequestResponseDTO getRequestEnriched(String requestId) {
	    return toDTO(getRequestById(requestId));
	}

	@Override
	public List<RequestResponseDTO> getRequestsByTypeEnriched(String type) {
	    return requestRepo.findByRequestType(type).stream().map(this::toDTO).collect(Collectors.toList());
	}

	@Override
	public List<RequestResponseDTO> getRequestsByTitleEnriched(String title) {
	    return requestRepo.findByRequestTitle(title).stream().map(this::toDTO).collect(Collectors.toList());
	}

	private RequestResponseDTO toDTO(Request r) {
	    RequestResponseDTO dto = new RequestResponseDTO();
	    dto.setRequestId(r.getRequestId());
	    dto.setRequestTitle(r.getRequestTitle());
	    dto.setRequestDescription(r.getRequestDescription());
	    dto.setRequestType(r.getRequestType());
	    dto.setUrgencyLevel(r.getUrgencyLevel());
	    dto.setRequestStatus(r.getRequestStatus());
	    dto.setLocation(r.getLocation());
	    dto.setLinkedOfferIds(r.getLinkedOfferIds());
	    dto.setCreatedAt(r.getCreatedAt() != null ? r.getCreatedAt().toString() : null);
	    dto.setRequestingUserId(r.getRequestingUserId());
	    dto.setRequestingFamilyId(r.getRequestingFamilyId());

	    try {
	        User user = userService.getUserById(r.getRequestingUserId());
	        dto.setRequestingUserName(user.getFirstName() + " " + user.getLastName());
	    } catch (Exception e) {
	        dto.setRequestingUserName("Unknown");
	    }

	    try {
	        Family family = familyService.getFamilyById(r.getRequestingFamilyId());
	        dto.setRequestingFamilyName(family.getFamilyName());
	        dto.setFamilyTrustScore(family.getFamilyTrustScore());
	    } catch (Exception e) {
	        dto.setRequestingFamilyName("Unknown Family");
	    }

	    return dto;
	}
}
