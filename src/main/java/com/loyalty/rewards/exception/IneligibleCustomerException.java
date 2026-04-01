package com.loyalty.rewards.exception;

public class IneligibleCustomerException extends RuntimeException {
    public IneligibleCustomerException(String message) {
        super(message);
    }
}
