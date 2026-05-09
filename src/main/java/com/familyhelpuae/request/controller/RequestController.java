package com.familyhelpuae.request.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.familyhelpuae.request.model.Request;
import com.familyhelpuae.request.model.RequestStatus;
import com.familyhelpuae.request.model.RequestType;
import com.familyhelpuae.request.service.RequestService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/request")
@Tag(name = "Request", description = "Help request endpoints")
public class RequestController {

    private final RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @PostMapping("/save")
    public ResponseEntity<Request> createRequest(@RequestBody Request request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(requestService.createRequest(request));
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
    public ResponseEntity<List<Request>> getByStatus(@PathVariable RequestStatus status) {
        return ResponseEntity.ok(requestService.getRequestsByStatus(status));
    }

    @GetMapping("/type/{requestType}")
    public ResponseEntity<List<Request>> getByType(@PathVariable RequestType requestType) {
        return ResponseEntity.ok(requestService.getRequestsByType(requestType));
    }

    @PutMapping("/{requestId}/status")
    public ResponseEntity<Request> updateStatus(@PathVariable String requestId,
                                                 @RequestParam RequestStatus status) {
        return ResponseEntity.ok(requestService.updateStatus(requestId, status));
    }
    
    @GetMapping("/title/{title}")
    public ResponseEntity<List<Request>> getByTitle(@PathVariable String title) {
        return ResponseEntity.ok(requestService.getRequestsByTitle(title));
    }
    
    
}