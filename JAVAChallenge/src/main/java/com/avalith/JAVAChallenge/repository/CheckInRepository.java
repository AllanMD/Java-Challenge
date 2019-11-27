package com.avalith.JAVAChallenge.repository;

import com.avalith.JAVAChallenge.domain.CheckIn;
import com.avalith.JAVAChallenge.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class CheckInRepository extends BaseRepository {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    public CheckInRepository(@Qualifier("connection1") Connection connection) {
        super(connection);
    }

    // returns the id of the generated checkin
    public Integer addCheckIn(CheckIn checkin) throws SQLException {
        String sql = "insert into checkins(numberOfDays,occupants, roomNumber, customerId) values (?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1,checkin.getNumberOfDays());
        ps.setInt(2,checkin.getOccupants());
        ps.setInt(3,checkin.getRoomNumber());
        ps.setInt(4,checkin.getCustomer().getId());
        ps.executeUpdate();

        sql = "select last_insert_id()";
        ps = connection.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();
        Integer last_id = null;
        while (rs.next()){
             last_id = rs.getInt("last_insert_id()");
        }
        return last_id;
    }

    public CheckIn getCheckinById(int checkinId) throws SQLException {
        String sql = "SELECT * FROM checkins WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setInt(1,checkinId);
        ResultSet result = ps.executeQuery();
        CheckIn checkin = null;
        while (result.next()){
            Customer customer = customerRepository.getCustomerById(result.getInt("customerId"));
            checkin = new CheckIn(result.getInt("id"),result.getInt("numberOfDays"), result.getInt("occupants"), result.getInt("roomNumber"),
                    customer);
        }

        return checkin;
    }
}
