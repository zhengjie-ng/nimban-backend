package com.example.nimban_backend.controller;

import com.example.nimban_backend.entity.Customer;
import com.example.nimban_backend.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.ResultHandler;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Import(NoValidationConfig.class)
class CustomerPatchEmailTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CustomerRepository customerRepository;

    private Long customerId;

    @BeforeEach
    void setup() {
        customerRepository.deleteAll();

        Customer customer = Customer.builder()
            .firstName("Alice")
            .lastName("Ng")
            .email("alice@example.com")
            .password("SecurePass8@")
            .birthYear(1995)
            .birthMonth(7)
            .birthDay(24)
            .build();

        customerId = customerRepository.save(customer).getId();
    }

    @Test
    void shouldPatchCustomerEmail() throws Exception {
        // Construct full Customer with updated email and all required fields
        Customer updated = Customer.builder()
            .firstName("Alice")
            .lastName("Ng")
            .email("new.email@example.com") // updated field
            .password("SecurePass8@")
            .birthYear(1995)
            .birthMonth(7)
            .birthDay(24)
            .build();

        mockMvc.perform(patch("/customers/{id}", customerId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.email").value("new.email@example.com"));
    }

}