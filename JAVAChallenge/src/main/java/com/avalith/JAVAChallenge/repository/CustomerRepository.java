package com.avalith.JAVAChallenge.repository;

import com.avalith.JAVAChallenge.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomerRepository extends BaseRepository{

    @Autowired
    public CustomerRepository(@Qualifier("connection1")Connection connection) {
        super(connection);
    }

    public void addCustomer(Customer customer) throws SQLException {
        String sql = "insert into customers(name,lastName,dni,phone,originCity) values (?,?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1,customer.getName());
        ps.setString(2,customer.getLastName());
        ps.setString(3,customer.getDni());
        ps.setString(4,customer.getPhone());
        ps.setString(5,customer.getOriginCity());
        ps.executeUpdate();
    }

    public List<Customer> getAll() {
        String sql = "select * from customers";
        List<Customer> customers = new ArrayList<Customer>();
        Customer customer = null;
        try {
            Statement stm = connection.createStatement();
            ResultSet resultSet = stm.executeQuery(sql);
            while (resultSet.next()) {
                customer = new Customer(resultSet.getInt("id"),resultSet.getString("name"), resultSet.getString("lastName"),resultSet.getString("dni"),resultSet.getString("phone")
                        ,resultSet.getString("originCity"));
                System.out.println(customer.toString());
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public Customer getCustomerById(int customerId) throws SQLException {
        String sql = "SELECT * FROM customers WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setInt(1,customerId);
        ResultSet result = ps.executeQuery();
        Customer customer = null;
        while (result.next()){
            customer = new Customer(result.getInt("id"), result.getString("name"), result.getString("lastName"),
                    result.getString("dni"), result.getString("phone"), result.getString("originCity"));
        }

        return customer;

    }

}
