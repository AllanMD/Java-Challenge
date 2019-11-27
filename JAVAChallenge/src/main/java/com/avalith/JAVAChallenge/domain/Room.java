package com.avalith.JAVAChallenge.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    @Id @GeneratedValue
    private int id;
    private int roomNumber;
    private int beds;
    private Float price;
    private String status;
    private List<RoomService> services;
    private boolean available;

}
