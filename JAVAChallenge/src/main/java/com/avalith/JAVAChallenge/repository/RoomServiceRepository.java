package com.avalith.JAVAChallenge.repository;

import com.avalith.JAVAChallenge.domain.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RoomServiceRepository extends BaseRepository {

    @Autowired
    public RoomServiceRepository(@Qualifier("connection1") Connection connection) {
        super(connection);
    }

    public void addService(RoomService roomService, int roomId) throws SQLException {
        String sql = "insert into services(name,roomId) values (?,?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, roomService.getName());
        ps.setInt(2,roomId);
        ps.executeUpdate();
    }

    public List<RoomService> getServicesByRoomId(int roomId) throws SQLException {
        String sql = "SELECT * FROM services WHERE roomId = ?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setInt(1,roomId);
        ResultSet result = ps.executeQuery();
        List<RoomService> roomServices = new ArrayList<RoomService>();
        while (result.next()){
            roomServices.add(new RoomService(result.getInt("id"), result.getString("name")));
        }

        return roomServices;

    }

    public void modify (RoomService roomService) throws SQLException {
        String sql = "UPDATE services SET name = ? WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, roomService.getName());
        ps.setInt(2,roomService.getId());

        ps.executeUpdate();

    }
}
