package com.loyalty.rewards.repository;

import com.loyalty.rewards.domain.Offer;
import com.loyalty.rewards.domain.OfferStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
    List<Offer> findByStatus(OfferStatus status);
    List<Offer> findByTargetTierAndStatus(String tier, OfferStatus status);
}
