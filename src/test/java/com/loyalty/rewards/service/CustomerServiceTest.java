package com.loyalty.rewards.service;

import com.loyalty.rewards.domain.Customer;
import com.loyalty.rewards.exception.CustomerNotFoundException;
import com.loyalty.rewards.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

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
    void getAllCustomers_shouldReturnAllCustomers() {
        when(customerRepository.findAll()).thenReturn(Arrays.asList(customer1, customer2));

        List<Customer> result = customerService.getAllCustomers();

        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyInAnyOrder(customer1, customer2);
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void getCustomerById_shouldReturnCustomerWhenFound() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer1));

        Customer result = customerService.getCustomerById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Alice Johnson");
        assertThat(result.getEmail()).isEqualTo("alice@example.com");
    }

    @Test
    void getCustomerById_shouldThrowExceptionWhenNotFound() {
        when(customerRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> customerService.getCustomerById(99L))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void createCustomer_shouldSaveAndReturnCustomer() {
        Customer newCustomer = new Customer();
        newCustomer.setName("Carol White");
        newCustomer.setEmail("carol@example.com");
        newCustomer.setTier("BRONZE");
        newCustomer.setLoyaltyPoints(0);

        when(customerRepository.save(any(Customer.class))).thenReturn(newCustomer);

        Customer result = customerService.createCustomer(newCustomer);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Carol White");
        assertThat(result.getEmail()).isEqualTo("carol@example.com");
        verify(customerRepository, times(1)).save(newCustomer);
    }

    @Test
    void updateCustomerTier_shouldSetBronzeForLowPoints() {
        customer2.setLoyaltyPoints(500);
        when(customerRepository.findById(2L)).thenReturn(Optional.of(customer2));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer2);

        Customer result = customerService.updateCustomerTier(2L);

        assertThat(result.getTier()).isEqualTo("BRONZE");
    }

    @Test
    void updateCustomerTier_shouldSetSilverFor1000To4999Points() {
        customer2.setLoyaltyPoints(2000);
        when(customerRepository.findById(2L)).thenReturn(Optional.of(customer2));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer2);

        Customer result = customerService.updateCustomerTier(2L);

        assertThat(result.getTier()).isEqualTo("SILVER");
    }

    @Test
    void updateCustomerTier_shouldSetGoldFor5000PlusPoints() {
        customer1.setLoyaltyPoints(6000);
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer1));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer1);

        Customer result = customerService.updateCustomerTier(1L);

        assertThat(result.getTier()).isEqualTo("GOLD");
    }
}
