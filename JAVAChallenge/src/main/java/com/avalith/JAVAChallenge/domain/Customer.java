package com.avalith.JAVAChallenge.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id @GeneratedValue
    private int id;
    private String name;
    private String lastName;
    private String dni;
    private String phone;
    private String originCity;
}
