package com.familyhelpuae.offer.controller;

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

import com.familyhelpuae.offer.model.Offer;
import com.familyhelpuae.offer.service.offerService;

@RestController
@RequestMapping("/api/offer")
public class offerController {

    private final offerService offerService;

    public offerController(offerService offerService) {
        this.offerService = offerService;
    }

    @PostMapping("/save")
    public ResponseEntity<Offer> createOffer(@RequestBody Offer offer) {
        return ResponseEntity.status(HttpStatus.CREATED).body(offerService.createOffer(offer));
    }

    @GetMapping("/{offerId}")
    public ResponseEntity<Offer> getOfferById(@PathVariable String offerId) {
        return ResponseEntity.ok(offerService.getOfferById(offerId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Offer>> getAllOffers() {
        return ResponseEntity.ok(offerService.getAllOffers());
    }

    @PutMapping("/update/{offerId}")
    public ResponseEntity<Offer> updateOffer(@PathVariable String offerId, @RequestBody Offer offer) {
        return ResponseEntity.ok(offerService.updateOffer(offer, offerId));
    }

    @DeleteMapping("/delete/{offerId}")
    public ResponseEntity<Void> deleteOffer(@PathVariable String offerId) {
        offerService.deleteOffer(offerId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/family/{familyId}")
    public ResponseEntity<List<Offer>> getOffersByFamily(@PathVariable String familyId) {
        return ResponseEntity.ok(offerService.getOffersByFamily(familyId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Offer>> getOffersByUser(@PathVariable String userId) {
        return ResponseEntity.ok(offerService.getOffersByUser(userId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Offer>> getOffersByStatus(@PathVariable String status) {
        return ResponseEntity.ok(offerService.getOffersByStatus(status));
    }

    @GetMapping("/type/{offerType}")
    public ResponseEntity<List<Offer>> getOffersByType(@PathVariable String offerType) {
        return ResponseEntity.ok(offerService.getOffersByType(offerType));
    }

    @PutMapping("/{offerId}/status")
    public ResponseEntity<Offer> updateStatus(@PathVariable String offerId, @RequestParam String status) {
        return ResponseEntity.ok(offerService.updateStatus(offerId, status));
    }
}