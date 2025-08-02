package com.example.nimban_backend.service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.example.nimban_backend.entity.Customer;
import com.example.nimban_backend.repository.CustomerRepository;

import com.example.nimban_backend.exception.CustomerNotFoundException;

// customerServiceImpl bean is a type of CustomerService
@Primary
@Service
public class CustomerServiceImpl implements CustomerService {
    private CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // CRUD
    // Create
    @Override
    public Customer createCustomer(Customer customer) {
        if (customerRepository.existsByEmailIgnoreCase(customer.getEmail())) {
            throw new IllegalArgumentException("Email is already registered");
        }

        validateBirthday(
            customer.getBirthYear(),
            customer.getBirthMonth(),
            customer.getBirthDay()
        );

        return customerRepository.save(customer);
    }

    // Read One
    @Override
    public Customer getCustomer(Long id) {
        return customerRepository.findById(id)
            .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    // Read All
    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // Update
    @Override
    public Customer updateCustomer(Long id, Customer customer) {
        Customer customerToUpdate = customerRepository.findById(id)
            .orElseThrow(() -> new CustomerNotFoundException(id));

        if (!customerToUpdate.getEmail().equalsIgnoreCase(customer.getEmail()) &&
            customerRepository.existsByEmailIgnoreCase(customer.getEmail())) {
            throw new IllegalArgumentException("Email is already registered to another user");
        }

        validateBirthday(
            customer.getBirthYear(),
            customer.getBirthMonth(),
            customer.getBirthDay()
        );

        validateBirthday(customer.getBirthYear(), customer.getBirthMonth(), customer.getBirthDay());

        customerToUpdate.setFirstName(customer.getFirstName());
        customerToUpdate.setLastName(customer.getLastName());
        customerToUpdate.setEmail(customer.getEmail());
        customerToUpdate.setPassword(customer.getPassword());
        customerToUpdate.setLastAccessedId(customer.getLastAccessedId());
        customerToUpdate.setTeammatesId(customer.getTeammatesId());
        customerToUpdate.setProjectsId(customer.getProjectsId());
        customerToUpdate.setBirthYear(customer.getBirthYear());
        customerToUpdate.setBirthMonth(customer.getBirthMonth());
        customerToUpdate.setBirthDay(customer.getBirthDay());

        return customerRepository.save(customerToUpdate);
    }

    // Patch
    @Override
    public Customer patchCustomer(Long id, Customer updates) {
        Customer customerToUpdate = customerRepository.findById(id)
            .orElseThrow(() -> new CustomerNotFoundException(id));

        if (updates.getFirstName() != null) {
            customerToUpdate.setFirstName(updates.getFirstName());
        }
        if (updates.getLastName() != null) {
            customerToUpdate.setLastName(updates.getLastName());
        }
        if (updates.getEmail() != null) {
            customerToUpdate.setEmail(updates.getEmail());
        }
        if (updates.getPassword() != null) {
            customerToUpdate.setPassword(updates.getPassword());
        }
        if (updates.getLastAccessedId() != null) {
            customerToUpdate.setLastAccessedId(updates.getLastAccessedId());
        }
        if (updates.getTeammatesId() != null) {
            customerToUpdate.setTeammatesId(updates.getTeammatesId());
        }
        if (updates.getProjectsId() != null) {
            customerToUpdate.setProjectsId(updates.getProjectsId());
        }
        if (updates.getBirthYear() != null &&
            updates.getBirthMonth() != null &&
            updates.getBirthDay() != null) {

                validateBirthday(updates.getBirthYear(), updates.getBirthMonth(), updates.getBirthDay());

                customerToUpdate.setBirthYear(updates.getBirthYear());
                customerToUpdate.setBirthMonth(updates.getBirthMonth());
                customerToUpdate.setBirthDay(updates.getBirthDay());
            }

        return customerRepository.save(customerToUpdate);
    }

    // Delete
    @Override
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new CustomerNotFoundException(id);
        }
        customerRepository.deleteById(id);
    }

    @Override
    public List<Customer> findByEmailIgnoreCase(String email) {
        return customerRepository.findByEmailIgnoreCase(email);
    }

     // Birthday validation logic
    private void validateBirthday(int year, int month, int day) {
        LocalDate today = LocalDate.now();

        // Check if date is valid
        LocalDate birthday;
        try {
            birthday = LocalDate.of(year, month, day);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("Birthday is not a valid date.");
        }

        // Check if birth year is too early
        if (year <= 1939) {
            throw new IllegalArgumentException("Customer should be born 1940 or later.");
        }

        // Check if date is in the future
        if (birthday.isAfter(today)) {
            throw new IllegalArgumentException("Birthdate cannot be in the future.");
        }

        // Check if at least 12 years old
        if (Period.between(birthday, today).getYears() < 12) {
            throw new IllegalArgumentException("Customer must be at least 12 years old.");
        }
    }
}
