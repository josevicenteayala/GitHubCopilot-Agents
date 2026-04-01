package com.loyalty.rewards.service;

import com.loyalty.rewards.domain.Customer;
import com.loyalty.rewards.domain.Offer;
import com.loyalty.rewards.domain.OfferStatus;
import com.loyalty.rewards.repository.OfferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OfferServiceTest {

    @Mock
    private OfferRepository offerRepository;

    @Mock
    private CustomerService customerService;

    @Mock
    private EligibilityService eligibilityService;

    @InjectMocks
    private OfferService offerService;

    private Offer activeOffer1;
    private Offer activeOffer2;
    private Customer goldCustomer;

    @BeforeEach
    void setUp() {
        activeOffer1 = new Offer();
        activeOffer1.setId(1L);
        activeOffer1.setTitle("Gold Member Exclusive");
        activeOffer1.setDiscountPercentage(20.0);
        activeOffer1.setMinPointsRequired(5000);
        activeOffer1.setStatus(OfferStatus.ACTIVE);
        activeOffer1.setValidFrom(LocalDate.now().minusDays(1));
        activeOffer1.setValidTo(LocalDate.now().plusDays(30));
        activeOffer1.setTargetTier("GOLD");

        activeOffer2 = new Offer();
        activeOffer2.setId(2L);
        activeOffer2.setTitle("Silver Rewards Boost");
        activeOffer2.setDiscountPercentage(10.0);
        activeOffer2.setMinPointsRequired(1000);
        activeOffer2.setStatus(OfferStatus.ACTIVE);
        activeOffer2.setValidFrom(LocalDate.now().minusDays(1));
        activeOffer2.setValidTo(LocalDate.now().plusDays(30));
        activeOffer2.setTargetTier("SILVER");

        goldCustomer = new Customer();
        goldCustomer.setId(1L);
        goldCustomer.setName("Alice Johnson");
        goldCustomer.setEmail("alice@example.com");
        goldCustomer.setTier("GOLD");
        goldCustomer.setLoyaltyPoints(5500);
    }

    @Test
    void getAllActiveOffers_shouldReturnOnlyActiveOffers() {
        when(offerRepository.findByStatus(OfferStatus.ACTIVE))
                .thenReturn(Arrays.asList(activeOffer1, activeOffer2));

        List<Offer> result = offerService.getAllActiveOffers();

        assertThat(result).hasSize(2);
        assertThat(result).allMatch(o -> o.getStatus() == OfferStatus.ACTIVE);
        verify(offerRepository, times(1)).findByStatus(OfferStatus.ACTIVE);
    }

    @Test
    void getOffersForCustomer_shouldReturnOffersMatchingCustomerTier() {
        when(offerRepository.findByTargetTierAndStatus("GOLD", OfferStatus.ACTIVE))
                .thenReturn(Arrays.asList(activeOffer1));

        List<Offer> result = offerService.getOffersForCustomer(goldCustomer);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTargetTier()).isEqualTo("GOLD");
        assertThat(result.get(0).getTitle()).isEqualTo("Gold Member Exclusive");
    }

    @Test
    void getOffersForCustomer_shouldReturnEmptyListWhenNoOffersForTier() {
        Customer bronzeCustomer = new Customer();
        bronzeCustomer.setId(3L);
        bronzeCustomer.setTier("BRONZE");
        bronzeCustomer.setLoyaltyPoints(100);

        when(offerRepository.findByTargetTierAndStatus("BRONZE", OfferStatus.ACTIVE))
                .thenReturn(List.of());

        List<Offer> result = offerService.getOffersForCustomer(bronzeCustomer);

        assertThat(result).isEmpty();
    }
}
