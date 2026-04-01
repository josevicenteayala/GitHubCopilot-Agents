package com.loyalty.rewards.dto;

import java.time.LocalDateTime;

public class CustomerDTO {

    private Long id;
    private String name;
    private String email;
    private String tier;
    private Integer loyaltyPoints;
    private LocalDateTime createdAt;

    public CustomerDTO() {}

    public CustomerDTO(Long id, String name, String email, String tier,
                       Integer loyaltyPoints, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.tier = tier;
        this.loyaltyPoints = loyaltyPoints;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTier() { return tier; }
    public void setTier(String tier) { this.tier = tier; }

    public Integer getLoyaltyPoints() { return loyaltyPoints; }
    public void setLoyaltyPoints(Integer loyaltyPoints) { this.loyaltyPoints = loyaltyPoints; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
