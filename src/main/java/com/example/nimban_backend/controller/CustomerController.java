package com.example.nimban_backend.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.nimban_backend.entity.Customer;
import com.example.nimban_backend.service.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer) {
        Customer newCustomer = customerService.createCustomer(customer);
        logger.info("🟢 {} {} POST Customer Success", LocalDate.now(), LocalTime.now());
        return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
    }

    // READ ALL or FILTER by email
    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers(@RequestParam(required = false) String email) {
        List<Customer> customers = (email != null && !email.trim().isEmpty())
                ? customerService.findByEmailIgnoreCase(email)
                : customerService.getAllCustomers();

        logger.info("🟢 {} {} GET Customers Success", LocalDate.now(), LocalTime.now());
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    // READ ONE
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable Long id) {
        Customer foundCustomer = customerService.getCustomer(id);
        logger.info("🟢 {} {} GET Customer/{} Success", LocalDate.now(), LocalTime.now(), id);
        return new ResponseEntity<>(foundCustomer, HttpStatus.OK);
    }

    // FULL UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @Valid @RequestBody Customer customer) {
        Customer updatedCustomer = customerService.updateCustomer(id, customer);
        logger.info("🟢 {} {} PUT Customer/{} Success", LocalDate.now(), LocalTime.now(), id);
        return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
    }

    // PARTIAL UPDATE
    @PatchMapping("/{id}")
    public ResponseEntity<Customer> patchCustomer(@PathVariable Long id, @Valid @RequestBody Customer updates) {
        Customer patchedCustomer = customerService.patchCustomer(id, updates);
        logger.info("🟢 {} {} PATCH Customer/{} Success", LocalDate.now(), LocalTime.now(), id);
        return new ResponseEntity<>(patchedCustomer, HttpStatus.OK);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        logger.info("🟢 {} {} DELETE Customer/{} Success", LocalDate.now(), LocalTime.now(), id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}