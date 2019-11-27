package com.avalith.JAVAChallenge.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckIn {

    @Id
    @GeneratedValue
    private int id;
    private int numberOfDays;
    private int occupants;
    private int roomNumber;
    private Customer customer;
}
