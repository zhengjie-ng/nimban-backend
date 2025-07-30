package com.example.nimban_backend.controller;

import com.example.nimban_backend.entity.Customer;
import com.example.nimban_backend.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class CustomerControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        customerRepository.deleteAll(); // clean slate for each test
    }

    @AfterEach
    void teardown() {
        customerRepository.deleteAll(); // Optional: double-clean for safety
    }

  @Test
    void testCreateCustomer() throws Exception {
        Customer request = Customer.builder()
                .firstName("Marie")
                .lastName("Curie")
                .email("marie@radioactive.com")
                .password("uranium")
                .build();

        mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("marie@radioactive.com"));

        List<Customer> saved = customerRepository.findAll();
        assertThat(saved).hasSize(1);
    }

    @Test
    void testGetCustomerById() throws Exception {
        Customer saved = customerRepository.save(
                Customer.builder().email("tesla@coil.com").firstName("Nikola").build()
        );

        mockMvc.perform(get("/customers/" + saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Nikola"));
    }

    @Test
    void testDeleteCustomer() throws Exception {
        Customer saved = customerRepository.save(
                Customer.builder().email("delete@me.com").build()
        );

        mockMvc.perform(delete("/customers/" + saved.getId()))
                .andExpect(status().isNoContent());

        assertThat(customerRepository.existsById(saved.getId())).isFalse();
    }

    @Test
    void testGetAllCustomers() throws Exception {
        customerRepository.saveAll(List.of(
                Customer.builder().email("one@example.com").build(),
                Customer.builder().email("two@example.com").build()
        ));

        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }
}
