/**
 * Section: 104
 * Group number: 4
 * Student IDs and names: 
 * Laisa Sanjida Isra: 1089635
 * Fatima Syed Wasti: 1095190
 * Aysha Hira: 1088000
 */

package com.familyhelpuae.request.service;

import java.util.List;

import com.familyhelpuae.DTO.RequestResponseDTO;
import com.familyhelpuae.request.model.Request;

public interface RequestService {

    // CRUD
    Request createRequest(Request request);

    Request getRequestById(String requestId);

    List<Request> getAllRequests();

    Request updateRequest(Request newRequest, String requestId);

    void deleteRequest(String requestId);

    // Custom
    List<Request> getRequestsByFamily(String familyId);

    List<Request> getRequestsByStatus(String status);

    Request updateStatus(String requestId, String status);

    List<Request> getRequestsByType(String requestType);

    List<Request> getRequestsByUrgency(String urgencyLevel);

    List<Request> getRequestsByTitle(String title);

    Request linkOffer(String requestId, String offerId);

    Request unlinkOffer(String requestId, String offerId);

    List<RequestResponseDTO> getOpenRequestsEnriched();

    RequestResponseDTO getRequestEnriched(String requestId);

    List<RequestResponseDTO> getRequestsByTypeEnriched(String type);

    List<RequestResponseDTO> getRequestsByTitleEnriched(String title);

}
