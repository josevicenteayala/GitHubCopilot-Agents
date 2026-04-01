package com.loyalty.rewards.service;

import com.loyalty.rewards.domain.Customer;
import com.loyalty.rewards.exception.CustomerNotFoundException;
import com.loyalty.rewards.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional(readOnly = true)
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer updateCustomerPoints(Long customerId, int points) {
        Customer customer = getCustomerById(customerId);
        customer.setLoyaltyPoints(customer.getLoyaltyPoints() + points);
        return customerRepository.save(customer);
    }

    public Customer updateCustomerTier(Long customerId) {
        Customer customer = getCustomerById(customerId);
        int points = customer.getLoyaltyPoints();
        String newTier;
        if (points >= 5000) {
            newTier = "GOLD";
        } else if (points >= 1000) {
            newTier = "SILVER";
        } else {
            newTier = "BRONZE";
        }
        customer.setTier(newTier);
        return customerRepository.save(customer);
    }
}
