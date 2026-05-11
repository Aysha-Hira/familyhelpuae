package com.familyhelpuae.request.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.familyhelpuae.DTO.RequestResponseDTO;
import com.familyhelpuae.request.model.Request;
import com.familyhelpuae.request.service.RequestService;
import com.familyhelpuae.security.CustomUserDetails;
import com.familyhelpuae.user.service.UserFamilyService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/request")
@Tag(name = "Request", description = "Help request endpoints")
public class RequestController {

    private final RequestService requestService;
    private final UserFamilyService userFamilyService;

    public RequestController(RequestService requestService, UserFamilyService userFamilyService) {
        this.requestService = requestService;
        this.userFamilyService = userFamilyService;
    }

    @PostMapping("/save")
    public ResponseEntity<?> createRequest(@RequestBody Request request, @AuthenticationPrincipal CustomUserDetails userDetails) {
        String currentUserId = userDetails.getUser().getUserID();

        // Validate that the requesting family actually belongs to the logged-in user
        if (request.getRequestingFamilyId() != null) {
            boolean belongsToUser = userFamilyService.isRelatedToFamily(
                currentUserId, request.getRequestingFamilyId());
            if (!belongsToUser) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("You do not belong to this family");
            }
        }

        request.setRequestingUserId(currentUserId);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(requestService.createRequest(request));
    }
    
    @GetMapping("/{requestId}")
    public ResponseEntity<Request> getRequestById(@PathVariable String requestId) {
        return ResponseEntity.ok(requestService.getRequestById(requestId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Request>> getAllRequests() {
        return ResponseEntity.ok(requestService.getAllRequests());
    }

    @PutMapping("/update/{requestId}")
    public ResponseEntity<Request> updateRequest(@PathVariable String requestId,
                                                  @RequestBody Request request) {
        return ResponseEntity.ok(requestService.updateRequest(request, requestId));
    }

    @DeleteMapping("/delete/{requestId}")
    public ResponseEntity<Void> deleteRequest(@PathVariable String requestId) {
        requestService.deleteRequest(requestId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/family/{familyId}")
    public ResponseEntity<List<Request>> getByFamily(@PathVariable String familyId) {
        return ResponseEntity.ok(requestService.getRequestsByFamily(familyId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Request>> getByStatus(@PathVariable String status) {
        return ResponseEntity.ok(requestService.getRequestsByStatus(status));
    }

    @GetMapping("/type/{requestType}")
    public ResponseEntity<List<Request>> getByType(@PathVariable String requestType) {
        return ResponseEntity.ok(requestService.getRequestsByType(requestType));
    }

    @PutMapping("/{requestId}/status")
    public ResponseEntity<Request> updateStatus(@PathVariable String requestId,
                                                 @RequestParam String status) {
        return ResponseEntity.ok(requestService.updateStatus(requestId, status));
    }
    
    @GetMapping("/title/{title}")
    public ResponseEntity<List<Request>> getByTitle(@PathVariable String title) {
        return ResponseEntity.ok(requestService.getRequestsByTitle(title));
    }
    
    @PostMapping("/{requestId}/offers/{offerId}")
    public ResponseEntity<Request> linkOffer(@PathVariable String requestId,
                                              @PathVariable String offerId) {
        return ResponseEntity.ok(requestService.linkOffer(requestId, offerId));
    }

    @DeleteMapping("/{requestId}/offers/{offerId}")
    public ResponseEntity<Request> unlinkOffer(@PathVariable String requestId,
                                                @PathVariable String offerId) {
        return ResponseEntity.ok(requestService.unlinkOffer(requestId, offerId));
    }
    
    @GetMapping("/feed")
    public ResponseEntity<List<RequestResponseDTO>> getFeed() {
        return ResponseEntity.ok(requestService.getOpenRequestsEnriched());
    }
    
    @GetMapping("/enriched/{requestId}")
    public ResponseEntity<RequestResponseDTO> getEnrichedRequest(@PathVariable String requestId) {
        return ResponseEntity.ok(requestService.getRequestEnriched(requestId));
    }
    
}