package com.example.nimban_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.nimban_backend.entity.AppRole;
import com.example.nimban_backend.entity.Customer;
import com.example.nimban_backend.entity.Role;
import com.example.nimban_backend.repository.CustomerRepository;
import com.example.nimban_backend.repository.RoleRepository;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
        return ResponseEntity.ok(customer);
    }

    @PutMapping("/customers/{id}/status")
    public ResponseEntity<Customer> updateCustomerStatus(
            @PathVariable Long id,
            @RequestParam boolean enabled) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));

        customer.setEnabled(enabled);
        Customer updated = customerRepository.save(customer);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/customers/{id}/role")
    public ResponseEntity<Customer> updateCustomerRole(
            @PathVariable Long id,
            @RequestParam String roleName) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));

        AppRole appRole = roleName.equals("ADMIN") ? AppRole.ROLE_ADMIN : AppRole.ROLE_USER;
        Role role = roleRepository.findByRoleName(appRole)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));

        customer.setRole(role);
        Customer updated = customerRepository.save(customer);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/customers/{id}/lock")
    public ResponseEntity<Customer> updateCustomerLockStatus(
            @PathVariable Long id,
            @RequestParam boolean locked) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));

        customer.setAccountNonLocked(!locked);
        Customer updated = customerRepository.save(customer);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/customers/{id}/password")
    public ResponseEntity<String> updateCustomerPassword(
            @PathVariable Long id,
            @RequestParam String password) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));

        customer.setPassword(passwordEncoder.encode(password));
        customerRepository.save(customer);
        return ResponseEntity.ok("Password updated successfully");
    }
}
