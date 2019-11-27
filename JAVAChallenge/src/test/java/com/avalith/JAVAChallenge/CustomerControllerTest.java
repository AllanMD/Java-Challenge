package com.avalith.JAVAChallenge;

import com.avalith.JAVAChallenge.controller.CustomerController;
import com.avalith.JAVAChallenge.domain.Customer;
import junit.framework.TestCase;
import org.junit.Assert;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CustomerControllerTest extends TestCase {

    @Mock
    CustomerController customerController;

    public void testGetAll(){
        Customer customer = new Customer(1, "Allan","Maduro", "1234", "3444", "mdp");
        List<Customer> expectedCustomers = new ArrayList<Customer>();
        expectedCustomers.add(customer);

        initMocks(this);
        when(customerController.getAll()).thenReturn(expectedCustomers);

        List<Customer> customers = customerController.getAll();
        Assert.assertEquals(customers, expectedCustomers);
    }
}
