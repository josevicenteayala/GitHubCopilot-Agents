package com.loyalty.rewards.service;

import com.loyalty.rewards.domain.Customer;
import com.loyalty.rewards.domain.Offer;
import com.loyalty.rewards.domain.OfferStatus;
import com.loyalty.rewards.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * EligibilityService - determines whether a customer is eligible for loyalty offers.
 *
 * Some methods in this class are intentionally left incomplete for students to implement.
 */
@Service
public class EligibilityService {

    private final TransactionRepository transactionRepository;

    public EligibilityService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    // TODO: Implement this method to check all eligibility criteria
    // Students should implement: point check, tier check, offer validity check, and transaction limit check
    // Use the helper methods: hasMinimumPoints(), isTierEligible(), isOfferActive(), checkTransactionLimit()
    public boolean isEligibleForOffer(Customer customer, Offer offer) {
        throw new UnsupportedOperationException("TODO: Implement eligibility check logic");
    }

    /**
     * Checks whether a customer has at least the specified number of loyalty points.
     *
     * @param customer  the customer to check
     * @param minPoints the minimum number of points required
     * @return true if the customer has sufficient points
     */
    public boolean hasMinimumPoints(Customer customer, int minPoints) {
        return customer.getLoyaltyPoints() >= minPoints;
    }

    /**
     * Checks whether a customer's tier is eligible for the given target tier.
     * Tier hierarchy: BRONZE < SILVER < GOLD.
     *
     * @param customer   the customer to check
     * @param targetTier the minimum tier required
     * @return true if the customer's tier is equal to or above the target tier
     */
    public boolean isTierEligible(Customer customer, String targetTier) {
        if (targetTier == null) {
            return true;
        }
        int customerScore = calculateTierScore(customer.getTier());
        int targetScore = calculateTierScore(targetTier);
        return customerScore >= targetScore;
    }

    /**
     * Checks whether an offer is currently active based on its status and validity dates.
     *
     * @param offer the offer to check
     * @return true if the offer status is ACTIVE and today is within the valid date range
     */
    public boolean isOfferActive(Offer offer) {
        if (offer.getStatus() != OfferStatus.ACTIVE) {
            return false;
        }
        LocalDate today = LocalDate.now();
        return !today.isBefore(offer.getValidFrom()) && !today.isAfter(offer.getValidTo());
    }

    // TODO: Implement this method to check whether the customer's total number of transactions
    // is within the allowed limit. Use transactionRepository.findByCustomerId() to retrieve
    // the customer's transactions and compare the count against maxTransactions.
    public boolean checkTransactionLimit(Long customerId, int maxTransactions) {
        throw new UnsupportedOperationException("TODO: Implement transaction limit check");
    }

    /**
     * Converts a tier name to a numeric score for comparison.
     *
     * @param tier the tier name (BRONZE, SILVER, GOLD)
     * @return numeric score: GOLD=3, SILVER=2, BRONZE=1, unknown=0
     */
    private int calculateTierScore(String tier) {
        if (tier == null) {
            return 0;
        }
        return switch (tier.toUpperCase()) {
            case "GOLD" -> 3;
            case "SILVER" -> 2;
            case "BRONZE" -> 1;
            default -> 0;
        };
    }
}
