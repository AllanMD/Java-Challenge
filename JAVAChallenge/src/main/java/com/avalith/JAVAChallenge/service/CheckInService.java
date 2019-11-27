package com.avalith.JAVAChallenge.service;

import com.avalith.JAVAChallenge.domain.CheckIn;
import com.avalith.JAVAChallenge.repository.CheckInRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;


@Service
@Data
public class CheckInService {
    @Autowired
    CheckInRepository checkInRepository;

    public Integer addCheckIn(CheckIn checkin) throws SQLException {
        return checkInRepository.addCheckIn(checkin);
    }

    public CheckIn getCheckinById (int checkinId) throws SQLException{
        return checkInRepository.getCheckinById(checkinId);
    }
}
