package com.loyalty.rewards.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loyalty.rewards.domain.Customer;
import com.loyalty.rewards.domain.Offer;
import com.loyalty.rewards.domain.OfferStatus;
import com.loyalty.rewards.exception.CustomerNotFoundException;
import com.loyalty.rewards.service.CustomerService;
import com.loyalty.rewards.service.OfferService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private OfferService offerService;

    private Customer customer1;
    private Customer customer2;

    @BeforeEach
    void setUp() {
        customer1 = new Customer();
        customer1.setId(1L);
        customer1.setName("Alice Johnson");
        customer1.setEmail("alice@example.com");
        customer1.setTier("GOLD");
        customer1.setLoyaltyPoints(5500);
        customer1.setCreatedAt(LocalDateTime.now());

        customer2 = new Customer();
        customer2.setId(2L);
        customer2.setName("Bob Smith");
        customer2.setEmail("bob@example.com");
        customer2.setTier("SILVER");
        customer2.setLoyaltyPoints(1200);
        customer2.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void getAllCustomers_shouldReturn200WithCustomerList() throws Exception {
        when(customerService.getAllCustomers()).thenReturn(Arrays.asList(customer1, customer2));

        mockMvc.perform(get("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Alice Johnson")))
                .andExpect(jsonPath("$[0].email", is("alice@example.com")))
                .andExpect(jsonPath("$[0].tier", is("GOLD")))
                .andExpect(jsonPath("$[1].name", is("Bob Smith")))
                .andExpect(jsonPath("$[1].tier", is("SILVER")));
    }

    @Test
    void getCustomerById_shouldReturn200WhenFound() throws Exception {
        when(customerService.getCustomerById(1L)).thenReturn(customer1);

        mockMvc.perform(get("/api/customers/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Alice Johnson")))
                .andExpect(jsonPath("$.email", is("alice@example.com")))
                .andExpect(jsonPath("$.loyaltyPoints", is(5500)));
    }

    @Test
    void getCustomerById_shouldReturn404WhenNotFound() throws Exception {
        when(customerService.getCustomerById(99L))
                .thenThrow(new CustomerNotFoundException(99L));

        mockMvc.perform(get("/api/customers/99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.message", containsString("99")));
    }

    @Test
    void getOffersForCustomer_shouldReturn200WithOfferList() throws Exception {
        Offer offer = new Offer();
        offer.setId(1L);
        offer.setTitle("Gold Member Exclusive");
        offer.setDiscountPercentage(20.0);
        offer.setMinPointsRequired(5000);
        offer.setStatus(OfferStatus.ACTIVE);
        offer.setValidFrom(LocalDate.now().minusDays(1));
        offer.setValidTo(LocalDate.now().plusDays(30));
        offer.setTargetTier("GOLD");

        when(customerService.getCustomerById(1L)).thenReturn(customer1);
        when(offerService.getOffersForCustomer(customer1)).thenReturn(List.of(offer));

        mockMvc.perform(get("/api/customers/1/offers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is("Gold Member Exclusive")))
                .andExpect(jsonPath("$[0].targetTier", is("GOLD")));
    }
}
