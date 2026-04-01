package com.loyalty.rewards.dto;

import com.loyalty.rewards.domain.TransactionType;
import java.time.LocalDateTime;

public class TransactionDTO {

    private Long id;
    private Long customerId;
    private Double amount;
    private Integer pointsEarned;
    private Integer pointsRedeemed;
    private TransactionType type;
    private LocalDateTime transactionDate;
    private String description;

    public TransactionDTO() {}

    public TransactionDTO(Long id, Long customerId, Double amount, Integer pointsEarned,
                          Integer pointsRedeemed, TransactionType type,
                          LocalDateTime transactionDate, String description) {
        this.id = id;
        this.customerId = customerId;
        this.amount = amount;
        this.pointsEarned = pointsEarned;
        this.pointsRedeemed = pointsRedeemed;
        this.type = type;
        this.transactionDate = transactionDate;
        this.description = description;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public Integer getPointsEarned() { return pointsEarned; }
    public void setPointsEarned(Integer pointsEarned) { this.pointsEarned = pointsEarned; }

    public Integer getPointsRedeemed() { return pointsRedeemed; }
    public void setPointsRedeemed(Integer pointsRedeemed) { this.pointsRedeemed = pointsRedeemed; }

    public TransactionType getType() { return type; }
    public void setType(TransactionType type) { this.type = type; }

    public LocalDateTime getTransactionDate() { return transactionDate; }
    public void setTransactionDate(LocalDateTime transactionDate) { this.transactionDate = transactionDate; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
