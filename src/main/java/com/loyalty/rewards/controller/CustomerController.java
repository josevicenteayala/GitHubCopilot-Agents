package com.loyalty.rewards.controller;

import com.loyalty.rewards.domain.Customer;
import com.loyalty.rewards.domain.Offer;
import com.loyalty.rewards.dto.CustomerDTO;
import com.loyalty.rewards.dto.OfferDTO;
import com.loyalty.rewards.service.CustomerService;
import com.loyalty.rewards.service.OfferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final OfferService offerService;

    public CustomerController(CustomerService customerService, OfferService offerService) {
        this.customerService = customerService;
        this.offerService = offerService;
    }

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<CustomerDTO> customers = customerService.getAllCustomers().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(toDTO(customer));
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO dto) {
        Customer customer = fromDTO(dto);
        Customer saved = customerService.createCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(saved));
    }

    @GetMapping("/{id}/offers")
    public ResponseEntity<List<OfferDTO>> getOffersForCustomer(@PathVariable Long id) {
        Customer customer = customerService.getCustomerById(id);
        List<OfferDTO> offers = offerService.getOffersForCustomer(customer).stream()
                .map(this::toOfferDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(offers);
    }

    private CustomerDTO toDTO(Customer customer) {
        return new CustomerDTO(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getTier(),
                customer.getLoyaltyPoints(),
                customer.getCreatedAt()
        );
    }

    private Customer fromDTO(CustomerDTO dto) {
        Customer customer = new Customer();
        customer.setName(dto.getName());
        customer.setEmail(dto.getEmail());
        customer.setTier(dto.getTier() != null ? dto.getTier() : "BRONZE");
        customer.setLoyaltyPoints(dto.getLoyaltyPoints() != null ? dto.getLoyaltyPoints() : 0);
        return customer;
    }

    private OfferDTO toOfferDTO(Offer offer) {
        return new OfferDTO(
                offer.getId(),
                offer.getTitle(),
                offer.getDescription(),
                offer.getDiscountPercentage(),
                offer.getMinPointsRequired(),
                offer.getStatus(),
                offer.getValidFrom(),
                offer.getValidTo(),
                offer.getTargetTier()
        );
    }
}
