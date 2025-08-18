package com.hotelapp.service.impl;

import com.hotelapp.dto.CustomerDTO;
import com.hotelapp.mapper.CustomerMapper;
import com.hotelapp.model.Customer;
import com.hotelapp.repo.CustomerRepo;
import com.hotelapp.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService implements ICustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private CustomerRepo customerRepo;

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepo.findAll()
                .stream()
                .map(customerMapper)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(Long id) {
        return customerRepo.findById(id)
                .map(customerMapper);
    }

    @Override
    public Optional<CustomerDTO> getCustomerByEmail(String email) {
        return customerRepo.findByEmail(email)
                .map(customerMapper);
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        if(customerRepo.existsByEmail(customerDTO.email())){
            throw new RuntimeException("Email already exists");
        }
        Customer customer = customerMapper.toEntity(customerDTO);
        Customer savedCustomer = customerRepo.save(customer);
        return customerMapper.apply(savedCustomer);
    }

    @Override
    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
        Optional<Customer> existingCustomer = customerRepo.findById(id);
        if(existingCustomer.isEmpty()){
            throw new RuntimeException("Customer not found with id:" + id);
        }
        if(!existingCustomer.get().getEmail().equals(customerDTO.email()) &&
                customerRepo.existsByEmail(customerDTO.email())){
            throw new RuntimeException("Customer with email already exists");
        }
        Customer newCustomer = existingCustomer.get();
        newCustomer.setFirstName(customerDTO.firstName());
        newCustomer.setLastName(customerDTO.lastName());
        newCustomer.setEmail(customerDTO.email());
        newCustomer.setPhone(customerDTO.phone());

        Customer savedCustomer = customerRepo.save(newCustomer);
        return customerMapper.apply(savedCustomer);
    }

    @Override
    public void deleteCustomer(Long id) {
        if(!customerRepo.existsById(id)){
            throw new RuntimeException("Customer not found with id: " + id);
        }
        customerRepo.deleteById(id);
    }
}
