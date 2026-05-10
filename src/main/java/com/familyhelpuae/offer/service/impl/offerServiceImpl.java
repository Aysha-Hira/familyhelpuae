package com.familyhelpuae.offer.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import com.familyhelpuae.exception.ResourceNotFound;
import com.familyhelpuae.offer.model.Offer;
import com.familyhelpuae.offer.repository.offerRepository;
import com.familyhelpuae.offer.service.offerService;
import com.familyhelpuae.request.service.RequestService;
import com.mongodb.lang.NonNull;

@Service
public class offerServiceImpl implements offerService {

    private final offerRepository offerRepository;
    private final RequestService requestService;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public offerServiceImpl(offerRepository offerRepository, RequestService requestService) {
        this.offerRepository = offerRepository;
        this.requestService = requestService;
    }

    @Override
    public Offer createOffer(Offer offer) {
        offer.setOfferId(null);
        offer.setOfferStatus("open");
        String now = LocalDateTime.now().format(formatter);
        offer.setCreatedAt(now);
        offer.setUpdatedAt(now);
        Offer saved = offerRepository.save(offer);


        if (offer.getLinkedRequestId() != null) {
            requestService.linkOffer(offer.getLinkedRequestId(), saved.getOfferId());
        }

        return saved;
    }

    @Override
    public Offer getOfferById(@NonNull String offerId) {
        return offerRepository.findById(offerId)
                .orElseThrow(() -> new ResourceNotFound("Offer", "offerId", offerId));
    }

    @Override
    public List<Offer> getAllOffers() {
        return offerRepository.findAll();
    }

    @Override
    public Offer updateOffer(Offer updatedOffer, @NonNull String offerId) {
        Offer existing = getOfferById(offerId);
        
        if (updatedOffer.getOfferType() != null)
            existing.setOfferType(updatedOffer.getOfferType());
        if (updatedOffer.getOfferLocation() != null)
            existing.setOfferLocation(updatedOffer.getOfferLocation());
        if (updatedOffer.getAvailability() != null)
            existing.setAvailability(updatedOffer.getAvailability());
        if (updatedOffer.getOfferDescription() != null)
            existing.setOfferDescription(updatedOffer.getOfferDescription());
        if (updatedOffer.getOfferStatus() != null)
            existing.setOfferStatus(updatedOffer.getOfferStatus());
        
        existing.setUpdatedAt(LocalDateTime.now().format(formatter));
        
        return offerRepository.save(existing);
    }

    @Override
    public void deleteOffer(@NonNull String offerId) {  // ADD @NonNull HERE
        if (!offerRepository.existsById(offerId)) {
            throw new ResourceNotFound("Offer", "offerId", offerId);
        }
        offerRepository.deleteById(offerId);
    }

    @Override
    public List<Offer> getOffersByFamily(@NonNull String familyId) {  // ADD @NonNull HERE
        return offerRepository.findByOfferingFamilyId(familyId);
    }

    @Override
    public List<Offer> getOffersByUser(@NonNull String userId) {  // ADD @NonNull HERE
        return offerRepository.findByOfferingUserId(userId);
    }

    @Override
    public List<Offer> getOffersByStatus(@NonNull String status) {  // ADD @NonNull HERE
        return offerRepository.findByOfferStatus(status);
    }

    @Override
    public List<Offer> getOffersByType(@NonNull String offerType) {  // ADD @NonNull HERE
        return offerRepository.findByOfferType(offerType);
    }

    @Override
    public Offer updateStatus(@NonNull String offerId, @NonNull String status) {  // ADD @NonNull HERE
        Offer existing = getOfferById(offerId);
        existing.setOfferStatus(status);
        existing.setUpdatedAt(LocalDateTime.now().format(formatter));
        return offerRepository.save(existing);
    }
    
    @Override
    public Offer linkRequest(String offerId, String requestId) {
        Offer offer = getOfferById(offerId);
        requestService.linkOffer(requestId, offerId);
        offer.setUpdatedAt(LocalDateTime.now().format(formatter));
        return offerRepository.save(offer);
    }

	@Override
	public Offer unlinkRequest(String offerId, String requestId) {
		// TODO Auto-generated method stub
		return null;
	}
}