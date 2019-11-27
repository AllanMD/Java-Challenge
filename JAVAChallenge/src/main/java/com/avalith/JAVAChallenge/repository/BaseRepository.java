package com.avalith.JAVAChallenge.repository;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Connection;
// for the timezone error:  SET GLOBAL time_zone = '+3:00';
@Data
@AllArgsConstructor
public abstract class BaseRepository {
    Connection connection;
}
