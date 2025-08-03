package com.example.nimban_backend.controller;

import com.example.nimban_backend.entity.Customer;
import com.example.nimban_backend.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {

        @Autowired
        private MockMvc mockMvc;
        @Autowired
        private ObjectMapper objectMapper;
        @Autowired
        private CustomerRepository customerRepository;

        private Customer existingCustomer;

        @BeforeEach
        void setup() {
                customerRepository.deleteAll();

                existingCustomer = customerRepository.save(Customer.builder()
                                .firstName("Alice")
                                .lastName("Ng")
                                .email("alice@example.com")
                                .password("SecurePass8@")
                                .birthYear(1995)
                                .birthMonth(7)
                                .birthDay(24)
                                .teammatesId(Collections.emptyList())
                                .projectsId(Collections.emptyList())
                                .build());
        }

        @Test
        void shouldCreateCustomer() throws Exception {
                Customer newCustomer = Customer.builder()
                                .firstName("Bob")
                                .lastName("Tan")
                                .email("bob.tan@example.com")
                                .password("TestPassword1@")
                                .birthYear(1990)
                                .birthMonth(12)
                                .birthDay(5)
                                .build();

                mockMvc.perform(post("/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(newCustomer)))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.firstName").value("Bob"));
        }

        @Test
        void shouldGetCustomerById() throws Exception {
                mockMvc.perform(get("/customers/{id}", existingCustomer.getId()))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.email").value("alice@example.com"));
        }

        @Test
        void shouldListAllCustomers() throws Exception {
                mockMvc.perform(get("/customers"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.length()").value(1));
        }

        @Test
        void shouldUpdateCustomer() throws Exception {
                existingCustomer.setFirstName("Alicia");

                mockMvc.perform(put("/customers/{id}", existingCustomer.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(existingCustomer)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.firstName").value("Alicia"));
        }

        // @Test
        // void shouldPatchCustomerEmail() throws Exception {
        //         // String patchJson = "{\"email\": \"new.email@example.com\"}";
        //         Customer partial = new Customer();
        //         partial.setId(existingCustomer.getId()); // optional
        //         partial.setEmail("new.email@example.com"); // only the field you want to patch

        //         String patchJson = objectMapper.writeValueAsString(partial);

        //         mockMvc.perform(patch("/customers/{id}", existingCustomer.getId())
        //                         .contentType(MediaType.APPLICATION_JSON)
        //                         .content(patchJson))
        //                         .andExpect(status().isOk())
        //                         .andExpect(jsonPath("$.email").value("new.email@example.com"));
        // }

        @Test
        void shouldDeleteCustomer() throws Exception {
                mockMvc.perform(delete("/customers/{id}", existingCustomer.getId()))
                                .andExpect(status().isNoContent());

                assertThat(customerRepository.findById(existingCustomer.getId())).isEmpty();
        }
}
