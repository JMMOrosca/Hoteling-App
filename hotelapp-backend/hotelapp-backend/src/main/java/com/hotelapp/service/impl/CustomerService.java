package com.hotelapp.service.impl;

import com.hotelapp.model.Customer;
import com.hotelapp.repo.CustomerRepo;
import com.hotelapp.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService implements ICustomerService {

    @Autowired
    private CustomerRepo customerRepo;

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }

    @Override
    public Optional<Customer> getCustomerById(Long id) {
        return customerRepo.findById(id);
    }

    @Override
    public Optional<Customer> getCustomerByEmail(String email) {
        return customerRepo.findByEmail(email);
    }

    @Override
    public Customer createCustomer(Customer customer) {
        if(customerRepo.existsByEmail(customer.getEmail())){
            throw new RuntimeException("Email already exists");
        }
        return customerRepo.save(customer);
    }

    @Override
    public Customer updateCustomer(Long id, Customer customer) {
        Optional<Customer> existingCustomer = customerRepo.findById(id);
        if(existingCustomer.isEmpty()){
            throw new RuntimeException("Customer not found with id:" + id);
        }
        if(!existingCustomer.get().getEmail().equals(customer.getEmail()) &&
                customerRepo.existsByEmail(customer.getEmail())){
            throw new RuntimeException("Customer with email already exists");
        }
        Customer newCustomer = existingCustomer.get();
        newCustomer.setFirstName(customer.getFirstName());
        newCustomer.setLastName(customer.getLastName());
        newCustomer.setEmail(customer.getEmail());
        newCustomer.setPhone(customer.getPhone());

        return customerRepo.save(newCustomer);
    }

    @Override
    public void deleteCustomer(Long id) {
        if(!customerRepo.existsById(id)){
            throw new RuntimeException("Customer not found with id: " + id);
        }
        customerRepo.deleteById(id);
    }
}
