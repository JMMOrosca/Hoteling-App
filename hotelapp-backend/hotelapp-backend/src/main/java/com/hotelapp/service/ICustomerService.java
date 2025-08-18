package com.hotelapp.service;

import com.hotelapp.dto.CustomerDTO;
import com.hotelapp.model.Customer;

import java.util.List;
import java.util.Optional;

public interface ICustomerService {
    public List<CustomerDTO> getAllCustomers();
    public Optional<CustomerDTO> getCustomerById(Long id);
    public Optional<CustomerDTO> getCustomerByEmail(String email);
    public CustomerDTO createCustomer(CustomerDTO customerDTO);
    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO);
    public void deleteCustomer(Long id);
}
