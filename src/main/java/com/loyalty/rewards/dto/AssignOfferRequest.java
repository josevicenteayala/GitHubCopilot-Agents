package com.loyalty.rewards.dto;

public class AssignOfferRequest {

    private Long offerId;
    private Long customerId;

    public AssignOfferRequest() {}

    public AssignOfferRequest(Long offerId, Long customerId) {
        this.offerId = offerId;
        this.customerId = customerId;
    }

    public Long getOfferId() { return offerId; }
    public void setOfferId(Long offerId) { this.offerId = offerId; }

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }
}
