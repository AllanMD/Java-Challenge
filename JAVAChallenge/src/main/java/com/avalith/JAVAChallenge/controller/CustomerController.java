package com.avalith.JAVAChallenge.controller;

import com.avalith.JAVAChallenge.domain.Customer;
import com.avalith.JAVAChallenge.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestController()
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    /**
     * Adds a customer to the databse
     * @param customer : a Customer object
     */
    @PostMapping("/add")
    public void addCustomer(@RequestBody Customer customer) {
        try {
            customerService.addCustomer(customer);
        } catch (Exception e) {
            e.printStackTrace();
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Database error");
        }
    }

    /**
     * Returns all the customers
     */
    @GetMapping("/getAll")
    public List<Customer> getAll() {
        List<Customer> customers = customerService.getAll();
        return customers;
    }

}
