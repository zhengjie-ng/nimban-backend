package com.example.nimban_backend.service;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.example.nimban_backend.entity.Customer;
import com.example.nimban_backend.repository.CustomerRepository;

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
        return customerRepository.save(customer);
    }

    // Read
    // Read One
    @Override
    public Customer getCustomer(Long id) {
        Customer foundCustomer = customerRepository.findById(id).get();

        // Retrieve the customer object and return
        return foundCustomer;
    }

    // Read All
    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> allCustomers = customerRepository.findAll();
        return allCustomers;
    }

    // Update
    @Override
    public Customer updateCustomer(Long id, Customer customer) {
        // Retrieve the customer from the database
        Customer customerToUpdate = customerRepository.findById(id).get();
        // Update the fields of the customer retrieved
        customerToUpdate.setFirstName(customer.getFirstName());
        customerToUpdate.setLastName(customer.getLastName());
        customerToUpdate.setEmail(customer.getEmail());
        customerToUpdate.setPassword(customer.getPassword());
        customerToUpdate.setLastAccessedId(customer.getLastAccessedId());
        customerToUpdate.setTeammatesId(customer.getTeammatesId());
        customerToUpdate.setProjectsId(customer.getProjectsId());

        // Save the updated customer back to the database
        return customerRepository.save(customerToUpdate);
    }

    // Patch
    @Override
    public Customer patchCustomer(Long id, Customer updates) {
        Customer customerToUpdate = getCustomer(id);

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

        return customerRepository.save(customerToUpdate);
    }

    // Delete
    @Override
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

}
