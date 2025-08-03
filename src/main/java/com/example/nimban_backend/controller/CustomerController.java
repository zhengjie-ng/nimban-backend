package com.example.nimban_backend.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.nimban_backend.entity.Customer;
import com.example.nimban_backend.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private CustomerService customerService;

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer) {
        try {
            Customer newCustomer = customerService.createCustomer(customer);
            logger.info("🟢 {} {} POST Customer Success", LocalDate.now(), LocalTime.now());
            return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            logger.error("🔴 {} {} POST Customer Error Exception {}", LocalDate.now(), LocalTime.now(), e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // READ
    // Read all
    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers(@RequestParam(required = false) String email) {
        try {
            List<Customer> customers = (email != null && !email.trim().isEmpty())
                    ? customerService.findByEmailIgnoreCase(email)
                    : customerService.getAllCustomers();

            logger.info("🟢 {} {} GET Customers Success", LocalDate.now(), LocalTime.now());
            return new ResponseEntity<>(customers, HttpStatus.OK);
        } catch (RuntimeException e) {
            logger.error("🔴 {} {} GET Customers Error Exception {}", LocalDate.now(), LocalTime.now(), e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // @GetMapping
    // public ResponseEntity<List<Customer>> getAllCustomers(@RequestParam(required
    // = false) String email) {

    // if (email != null && !email.trim().isEmpty()) {
    // List<Customer> customers = customerService.findByEmailIgnoreCase(email);
    // return new ResponseEntity<>(customers, HttpStatus.OK);
    // }

    // List<Customer> allCustomers = customerService.getAllCustomers();
    // return new ResponseEntity<>(allCustomers, HttpStatus.OK);
    // }

    // Read one
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable Long id) {
        try {
            Customer foundCustomer = customerService.getCustomer(id);
            logger.info("🟢 {} {} GET Project/{} Success", LocalDate.now(), LocalTime.now(), id);
            return new ResponseEntity<>(foundCustomer, HttpStatus.OK);
        } catch (RuntimeException e) {
            logger.error("🔴 {} {} GET Project/{} Error 404 Exception {}", LocalDate.now(), LocalTime.now(), id,
                    e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    // @GetMapping("/{id}")
    // public ResponseEntity<Customer> getCustomer(@PathVariable Long id) {
    // Customer foundCustomer = customerService.getCustomer(id);
    // return new ResponseEntity<>(foundCustomer, HttpStatus.OK);
    // }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @Valid @RequestBody Customer customer) {
        try {
            Customer updatedCustomer = customerService.updateCustomer(id, customer);
            logger.info("🟢 {} {} PUT Customer/{} Success", LocalDate.now(), LocalTime.now(), id);
            return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
        } catch (RuntimeException e) {
            logger.error("🔴 {} {} PUT Customer/{} Error Exception {}", LocalDate.now(), LocalTime.now(), id,
                    e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    // @PutMapping("/{id}")
    // public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @Valid
    // @RequestBody Customer customer) {
    // Customer updatedCustomer = customerService.updateCustomer(id, customer);
    // return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
    // }

    // PARTIAL UPDATE
    @PatchMapping("/{id}")
    public ResponseEntity<Customer> patchCustomer(@PathVariable Long id, @Valid @RequestBody Customer updates) {
        try {
            Customer patchedCustomer = customerService.patchCustomer(id, updates);
            logger.info("🟢 {} {} PATCH Customer/{} Success", LocalDate.now(), LocalTime.now(), id);
            return new ResponseEntity<>(patchedCustomer, HttpStatus.OK);
        } catch (RuntimeException e) {
            logger.error("🔴 {} {} PATCH Customer/{} Error Exception {}", LocalDate.now(), LocalTime.now(), id,
                    e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    // @PatchMapping("/{id}")
    // public ResponseEntity<Customer> patchCustomer(@PathVariable Long id, @Valid
    // @RequestBody Customer updates) {
    // Customer patchedCustomer = customerService.patchCustomer(id, updates);
    // return new ResponseEntity<>(patchedCustomer, HttpStatus.OK);
    // }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCustomer(@PathVariable Long id) {
        try {
            customerService.deleteCustomer(id);
            logger.info("🟢 {} {} DELETE Customer/{} Success", LocalDate.now(), LocalTime.now(), id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            logger.error("🔴 {} {} DELETE Customer/{} Error Exception {}", LocalDate.now(), LocalTime.now(), id,
                    e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // @DeleteMapping("/{id}")
    // public ResponseEntity<HttpStatus> deleteCustomer(@PathVariable Long id) {
    // customerService.deleteCustomer(id);
    // return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    // }
}