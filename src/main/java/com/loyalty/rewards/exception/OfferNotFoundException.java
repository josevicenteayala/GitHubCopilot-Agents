package com.loyalty.rewards.exception;

public class OfferNotFoundException extends RuntimeException {
    public OfferNotFoundException(String message) {
        super(message);
    }

    public OfferNotFoundException(Long id) {
        super("Offer not found with id: " + id);
    }
}
