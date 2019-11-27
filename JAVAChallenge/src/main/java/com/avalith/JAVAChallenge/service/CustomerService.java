package com.avalith.JAVAChallenge.service;

import com.avalith.JAVAChallenge.domain.Customer;
import com.avalith.JAVAChallenge.repository.CustomerRepository;
import com.avalith.JAVAChallenge.repository.RoomRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    public void addCustomer(Customer customer) throws Exception {
        customerRepository.addCustomer(customer);
    }

    public List<Customer> getAll() {
        return customerRepository.getAll();
    }
}
