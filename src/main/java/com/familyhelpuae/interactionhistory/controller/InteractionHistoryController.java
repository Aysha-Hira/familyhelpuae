package com.familyhelpuae.interactionhistory.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.familyhelpuae.interactionhistory.model.InteractionHistory;
import com.familyhelpuae.interactionhistory.service.interactionHistoryService;

@RestController
@RequestMapping("/api/interaction")
public class InteractionHistoryController {

    private final interactionHistoryService interactionService;

    public InteractionHistoryController(interactionHistoryService interactionService) {
        this.interactionService = interactionService;
    }

    @PostMapping("/record")
    public ResponseEntity<InteractionHistory> recordInteraction(
            @RequestParam String helpingFamilyId,
            @RequestParam String helpedFamilyId,
            @RequestParam String interactionType,
            @RequestParam String description,
            @RequestParam(required = false) String requestId,
            @RequestParam(required = false) String offerId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(interactionService.recordInteraction(
                    helpingFamilyId, helpedFamilyId, interactionType, description, requestId, offerId));
    }

    @GetMapping("/{interactionId}")
    public ResponseEntity<InteractionHistory> getById(@PathVariable String interactionId) {
        return ResponseEntity.ok(interactionService.getInteractionById(interactionId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<InteractionHistory>> getAll() {
        return ResponseEntity.ok(interactionService.getAllInteractions());
    }

    @GetMapping("/family/{familyId}")
    public ResponseEntity<List<InteractionHistory>> getByFamily(@PathVariable String familyId) {
        return ResponseEntity.ok(interactionService.getInteractionsByFamily(familyId));
    }

    @GetMapping("/helping/{familyId}")
    public ResponseEntity<List<InteractionHistory>> getByHelpingFamily(@PathVariable String familyId) {
        return ResponseEntity.ok(interactionService.getInteractionsByHelpingFamily(familyId));
    }

    @GetMapping("/helped/{familyId}")
    public ResponseEntity<List<InteractionHistory>> getByHelpedFamily(@PathVariable String familyId) {
        return ResponseEntity.ok(interactionService.getInteractionsByHelpedFamily(familyId));
    }

    @PutMapping("/update/{interactionId}")
    public ResponseEntity<InteractionHistory> update(@PathVariable String interactionId,
                                                      @RequestBody InteractionHistory interaction) {
        return ResponseEntity.ok(interactionService.updateInteraction(interactionId, interaction));
    }

    @PutMapping("/{interactionId}/status")
    public ResponseEntity<InteractionHistory> updateStatus(@PathVariable String interactionId,
                                                            @RequestParam String status) {
        return ResponseEntity.ok(interactionService.updateStatus(interactionId, status));
    }

    @PutMapping("/{interactionId}/rate")
    public ResponseEntity<InteractionHistory> addRating(@PathVariable String interactionId,
                                                         @RequestParam String role,
                                                         @RequestParam double rating) {
        return ResponseEntity.ok(interactionService.addRating(interactionId, role, rating));
    }

    @DeleteMapping("/delete/{interactionId}")
    public ResponseEntity<Void> delete(@PathVariable String interactionId) {
        interactionService.deleteInteraction(interactionId);
        return ResponseEntity.noContent().build();
    }
}