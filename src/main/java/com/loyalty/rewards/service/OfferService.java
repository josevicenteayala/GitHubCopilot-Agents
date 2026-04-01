package com.loyalty.rewards.service;

import com.loyalty.rewards.domain.Customer;
import com.loyalty.rewards.domain.Offer;
import com.loyalty.rewards.domain.OfferStatus;
import com.loyalty.rewards.exception.IneligibleCustomerException;
import com.loyalty.rewards.exception.OfferNotFoundException;
import com.loyalty.rewards.repository.OfferRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OfferService {

    private final OfferRepository offerRepository;
    private final CustomerService customerService;
    private final EligibilityService eligibilityService;

    public OfferService(OfferRepository offerRepository,
                        CustomerService customerService,
                        EligibilityService eligibilityService) {
        this.offerRepository = offerRepository;
        this.customerService = customerService;
        this.eligibilityService = eligibilityService;
    }

    @Transactional(readOnly = true)
    public List<Offer> getAllActiveOffers() {
        return offerRepository.findByStatus(OfferStatus.ACTIVE);
    }

    @Transactional(readOnly = true)
    public Offer getOfferById(Long id) {
        return offerRepository.findById(id)
                .orElseThrow(() -> new OfferNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<Offer> getOffersForCustomer(Customer customer) {
        return offerRepository.findByTargetTierAndStatus(customer.getTier(), OfferStatus.ACTIVE);
    }

    public void assignOfferToCustomer(Long offerId, Long customerId) {
        Offer offer = getOfferById(offerId);
        Customer customer = customerService.getCustomerById(customerId);
        if (!eligibilityService.hasMinimumPoints(customer, offer.getMinPointsRequired())) {
            throw new IneligibleCustomerException(
                    "Customer does not meet minimum points requirement for offer: " + offer.getTitle());
        }
        if (!eligibilityService.isTierEligible(customer, offer.getTargetTier())) {
            throw new IneligibleCustomerException(
                    "Customer tier is not eligible for offer: " + offer.getTitle());
        }
        if (!eligibilityService.isOfferActive(offer)) {
            throw new IneligibleCustomerException(
                    "Offer is not currently active: " + offer.getTitle());
        }
    }

    public Offer createOffer(Offer offer) {
        return offerRepository.save(offer);
    }
}
