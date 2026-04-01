package com.loyalty.rewards.dto;

import com.loyalty.rewards.domain.OfferStatus;
import java.time.LocalDate;

public class OfferDTO {

    private Long id;
    private String title;
    private String description;
    private Double discountPercentage;
    private Integer minPointsRequired;
    private OfferStatus status;
    private LocalDate validFrom;
    private LocalDate validTo;
    private String targetTier;

    public OfferDTO() {}

    public OfferDTO(Long id, String title, String description, Double discountPercentage,
                    Integer minPointsRequired, OfferStatus status, LocalDate validFrom,
                    LocalDate validTo, String targetTier) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.discountPercentage = discountPercentage;
        this.minPointsRequired = minPointsRequired;
        this.status = status;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.targetTier = targetTier;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getDiscountPercentage() { return discountPercentage; }
    public void setDiscountPercentage(Double discountPercentage) { this.discountPercentage = discountPercentage; }

    public Integer getMinPointsRequired() { return minPointsRequired; }
    public void setMinPointsRequired(Integer minPointsRequired) { this.minPointsRequired = minPointsRequired; }

    public OfferStatus getStatus() { return status; }
    public void setStatus(OfferStatus status) { this.status = status; }

    public LocalDate getValidFrom() { return validFrom; }
    public void setValidFrom(LocalDate validFrom) { this.validFrom = validFrom; }

    public LocalDate getValidTo() { return validTo; }
    public void setValidTo(LocalDate validTo) { this.validTo = validTo; }

    public String getTargetTier() { return targetTier; }
    public void setTargetTier(String targetTier) { this.targetTier = targetTier; }
}
