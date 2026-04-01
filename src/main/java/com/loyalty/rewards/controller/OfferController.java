package com.loyalty.rewards.controller;

import com.loyalty.rewards.domain.Offer;
import com.loyalty.rewards.dto.OfferDTO;
import com.loyalty.rewards.service.OfferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/offers")
public class OfferController {

    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping
    public ResponseEntity<List<OfferDTO>> getAllActiveOffers() {
        List<OfferDTO> offers = offerService.getAllActiveOffers().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(offers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfferDTO> getOfferById(@PathVariable Long id) {
        Offer offer = offerService.getOfferById(id);
        return ResponseEntity.ok(toDTO(offer));
    }

    @PostMapping
    public ResponseEntity<OfferDTO> createOffer(@RequestBody OfferDTO dto) {
        Offer offer = fromDTO(dto);
        Offer saved = offerService.createOffer(offer);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(saved));
    }

    @PostMapping("/{offerId}/assign/{customerId}")
    public ResponseEntity<Void> assignOfferToCustomer(@PathVariable Long offerId,
                                                       @PathVariable Long customerId) {
        offerService.assignOfferToCustomer(offerId, customerId);
        return ResponseEntity.ok().build();
    }

    private OfferDTO toDTO(Offer offer) {
        return new OfferDTO(
                offer.getId(),
                offer.getTitle(),
                offer.getDescription(),
                offer.getDiscountPercentage(),
                offer.getMinPointsRequired(),
                offer.getStatus(),
                offer.getValidFrom(),
                offer.getValidTo(),
                offer.getTargetTier()
        );
    }

    private Offer fromDTO(OfferDTO dto) {
        Offer offer = new Offer();
        offer.setTitle(dto.getTitle());
        offer.setDescription(dto.getDescription());
        offer.setDiscountPercentage(dto.getDiscountPercentage());
        offer.setMinPointsRequired(dto.getMinPointsRequired());
        offer.setStatus(dto.getStatus());
        offer.setValidFrom(dto.getValidFrom());
        offer.setValidTo(dto.getValidTo());
        offer.setTargetTier(dto.getTargetTier());
        return offer;
    }
}
