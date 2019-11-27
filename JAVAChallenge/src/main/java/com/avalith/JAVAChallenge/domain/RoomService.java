package com.avalith.JAVAChallenge.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomService {

    @Id
    private Integer id;
    private String name;
}
