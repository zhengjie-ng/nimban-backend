package com.example.nimban_backend.service;

import com.example.nimban_backend.entity.Customer;
import com.example.nimban_backend.repository.CustomerRepository;
import com.example.nimban_backend.exception.CustomerNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    private Customer testCustomer;

    @BeforeEach
    void setup() {
        customerRepository.deleteAll();

        testCustomer = customerRepository.save(Customer.builder()
                .firstName("Test")
                .lastName("User")
                .email("test.user@example.com")
                .password("TestPass@123")
                .teammatesId(List.of(101L, 102L))
                .projectsId(List.of(201L))
                .birthYear(1990)
                .birthMonth(6)
                .birthDay(15)
                .build());
    }

    @Test
    void shouldFindCustomerById() {
        Customer fetched = customerService.getCustomer(testCustomer.getId());
        assertEquals("Test", fetched.getFirstName());
    }

    @Test
    void shouldThrowIfCustomerNotFound() {
        assertThrows(CustomerNotFoundException.class, () -> customerService.getCustomer(8888L) // Intentionally not
                                                                                               // present
        );
    }

    @Test
    void shouldReturnAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        assertTrue(customers.size() >= 1); // At least the one from setup()
    }

    @Test
    void shouldUpdateCustomerDetails() {
        Customer changes = new Customer();
        changes.setFirstName("Updated");
        changes.setBirthYear(1985);
        changes.setBirthMonth(6); // prevent .intValue() on null
        changes.setBirthDay(15); // good practice if day is also accessed

        Customer updated = customerService.updateCustomer(testCustomer.getId(), changes);
        assertEquals("Updated", updated.getFirstName());
        assertEquals(1985, updated.getBirthYear());
    }

    @Test
    void shouldDeleteCustomer() {
        customerService.deleteCustomer(testCustomer.getId());
        assertThrows(CustomerNotFoundException.class, () -> customerService.getCustomer(testCustomer.getId()));
    }
}