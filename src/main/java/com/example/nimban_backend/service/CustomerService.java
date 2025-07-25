package com.example.nimban_backend.service;

import java.util.List;

import com.example.nimban_backend.entity.Customer;

public interface CustomerService {
  Customer createCustomer(Customer customer);

  Customer getCustomer(Long id);

  List<Customer> getAllCustomers();

  Customer updateCustomer(Long id, Customer customer);

  Customer patchCustomer(Long id, Customer updates);

  void deleteCustomer(Long id);

  List<Customer> findByEmailIgnoreCase(String email);
}