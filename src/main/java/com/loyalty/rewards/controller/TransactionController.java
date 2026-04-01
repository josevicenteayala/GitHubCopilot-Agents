package com.loyalty.rewards.controller;

import com.loyalty.rewards.domain.Transaction;
import com.loyalty.rewards.domain.TransactionType;
import com.loyalty.rewards.dto.TransactionDTO;
import com.loyalty.rewards.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<TransactionDTO>> getTransactionsForCustomer(
            @PathVariable Long customerId) {
        List<TransactionDTO> transactions = transactionService.getTransactionsForCustomer(customerId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(transactions);
    }

    @PostMapping
    public ResponseEntity<TransactionDTO> createTransaction(@RequestBody Map<String, Object> request) {
        Long customerId = Long.valueOf(request.get("customerId").toString());
        Double amount = Double.valueOf(request.get("amount").toString());
        TransactionType type = TransactionType.valueOf(request.get("type").toString());
        String description = request.getOrDefault("description", "").toString();

        Transaction transaction = transactionService.createTransaction(customerId, amount, type, description);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(transaction));
    }

    private TransactionDTO toDTO(Transaction transaction) {
        return new TransactionDTO(
                transaction.getId(),
                transaction.getCustomer().getId(),
                transaction.getAmount(),
                transaction.getPointsEarned(),
                transaction.getPointsRedeemed(),
                transaction.getType(),
                transaction.getTransactionDate(),
                transaction.getDescription()
        );
    }
}
