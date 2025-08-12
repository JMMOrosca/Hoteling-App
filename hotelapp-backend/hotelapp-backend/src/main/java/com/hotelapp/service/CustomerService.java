package com.hotelapp.service;

import com.hotelapp.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    public List<Customer> getAllCustomers();
    public Optional<Customer> getCustomerById(Long id);
    public Optional<Customer> getCustomerByEmail(String email);
    public Customer createCustomer(Customer customer);
    public Customer updateCustomer(Long id, Customer Customer);
    public void deleteCustomer(Long id);
}
