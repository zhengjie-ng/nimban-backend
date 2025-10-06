package com.example.nimban_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nimban_backend.entity.Customer;
import java.util.List;
import java.util.Optional;


public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findByEmailIgnoreCase(String email);
    Optional<Customer> findByEmail(String email);
    boolean existsByEmailIgnoreCase(String email);
    boolean existsByEmail(String email);

}
