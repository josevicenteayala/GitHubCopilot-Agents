package com.loyalty.rewards.service;

import com.loyalty.rewards.domain.Customer;
import com.loyalty.rewards.domain.Transaction;
import com.loyalty.rewards.domain.TransactionType;
import com.loyalty.rewards.exception.IneligibleCustomerException;
import com.loyalty.rewards.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class TransactionService {

    private static final int POINTS_PER_DOLLAR = 10;

    private final TransactionRepository transactionRepository;
    private final CustomerService customerService;

    public TransactionService(TransactionRepository transactionRepository,
                              CustomerService customerService) {
        this.transactionRepository = transactionRepository;
        this.customerService = customerService;
    }

    public Transaction createTransaction(Long customerId, Double amount,
                                         TransactionType type, String description) {
        Customer customer = customerService.getCustomerById(customerId);
        int points = calculatePointsForAmount(amount);

        Transaction transaction = new Transaction();
        transaction.setCustomer(customer);
        transaction.setAmount(amount);
        transaction.setType(type);
        transaction.setDescription(description);
        transaction.setTransactionDate(LocalDateTime.now());

        if (type == TransactionType.EARN) {
            transaction.setPointsEarned(points);
            transaction.setPointsRedeemed(0);
            customerService.updateCustomerPoints(customerId, points);
        } else if (type == TransactionType.REDEEM) {
            transaction.setPointsEarned(0);
            transaction.setPointsRedeemed(points);
        } else {
            transaction.setPointsEarned(0);
            transaction.setPointsRedeemed(0);
        }

        customerService.updateCustomerTier(customerId);
        return transactionRepository.save(transaction);
    }

    public int calculatePointsForAmount(Double amount) {
        return (int) (amount * POINTS_PER_DOLLAR);
    }

    @Transactional(readOnly = true)
    public List<Transaction> getTransactionsForCustomer(Long customerId) {
        return transactionRepository.findByCustomerId(customerId);
    }

    public Transaction redeemPoints(Long customerId, int points) {
        Customer customer = customerService.getCustomerById(customerId);
        if (customer.getLoyaltyPoints() < points) {
            throw new IneligibleCustomerException(
                    "Insufficient loyalty points. Available: " + customer.getLoyaltyPoints()
                    + ", Requested: " + points);
        }

        customerService.updateCustomerPoints(customerId, -points);
        customerService.updateCustomerTier(customerId);

        Transaction transaction = new Transaction();
        transaction.setCustomer(customer);
        transaction.setAmount(0.0);
        transaction.setPointsEarned(0);
        transaction.setPointsRedeemed(points);
        transaction.setType(TransactionType.REDEEM);
        transaction.setDescription("Points redemption: " + points + " points");
        transaction.setTransactionDate(LocalDateTime.now());

        return transactionRepository.save(transaction);
    }
}
