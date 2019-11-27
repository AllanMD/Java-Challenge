package com.avalith.JAVAChallenge.repository;

import com.avalith.JAVAChallenge.domain.Room;
import com.avalith.JAVAChallenge.domain.RoomService;
import com.avalith.JAVAChallenge.service.RoomServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RoomRepository extends BaseRepository{

    @Autowired
    RoomServiceService roomServiceService;

    @Autowired
    public RoomRepository(@Qualifier("connection1")Connection connection) {
        super(connection);
    }

    public void addRoom(Room room) throws SQLException {
        String sql = "insert into rooms(roomNumber,beds,price,status,available) values (?,?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1,room.getRoomNumber());
        ps.setInt(2,room.getBeds());
        ps.setFloat(3,room.getPrice());
        ps.setString(4,room.getStatus());
        ps.setBoolean(5,room.isAvailable());
        ps.executeUpdate();

        sql = "select last_insert_id()";
        ps = connection.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();
        int id = -1;
        while (rs.next()){
            id = rs.getInt("last_insert_id()");
        }

        for ( RoomService roomService : room.getServices()) {
            roomServiceService.addService(roomService, id);
        }
    }

    public List<Room> getAll() {
        String sql = "select * from rooms";
        List<Room> rooms = new ArrayList<Room>();
        Room room = null;
        try {
            Statement stm = connection.createStatement();
            ResultSet resultSet = stm.executeQuery(sql);
            while (resultSet.next()) {
                List<RoomService> roomServices = roomServiceService.getServicesByRoomId(resultSet.getInt("id"));
                room = new Room(resultSet.getInt("id"),resultSet.getInt("roomNumber"), resultSet.getInt("beds"),
                        resultSet.getFloat("price"),resultSet.getString("status"),roomServices, resultSet.getBoolean("available"));
                System.out.println(room.toString());
                rooms.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    public List<Room> getAllByAvailability (boolean available){
        String sql = "SELECT * FROM rooms WHERE available = ?";
        List<Room> rooms = new ArrayList<Room>();
        Room room = null;
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setBoolean(1,available);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                List<RoomService> roomServices = roomServiceService.getServicesByRoomId(resultSet.getInt("id"));
                room = new Room(resultSet.getInt("id"),resultSet.getInt("roomNumber"), resultSet.getInt("beds"),
                        resultSet.getFloat("price"),resultSet.getString("status"),roomServices, resultSet.getBoolean("available"));
                System.out.println(room.toString());
                rooms.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }



    public Room getByRoomNumber(int room_number) throws SQLException{
        String sql = "SELECT * FROM rooms WHERE roomNumber = ?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setInt(1,room_number);
        ResultSet result = ps.executeQuery();
        Room room = null;
        while (result.next()){
            List<RoomService> roomServices = roomServiceService.getServicesByRoomId(result.getInt("id"));

            room = new Room(result.getInt("id"), result.getInt("roomNumber"), result.getInt("beds"),
                   result.getFloat("price"), result.getString("status"), roomServices, result.getBoolean("available"));
        }

        return room;
    }

    public void changeRoomStatus(int room_number, String newStatus) throws SQLException{
        String sql = "UPDATE rooms SET status = ? WHERE roomNumber = ?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, newStatus);
        ps.setInt(2,room_number);
        ps.executeUpdate();

    }

    public void changeRoomAvailability(int room_number, boolean available) throws SQLException{
        String sql = "UPDATE rooms SET available = ? WHERE roomNumber = ?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setBoolean(1, available);
        ps.setInt(2,room_number);
        ps.executeUpdate();

    }

    public void modify (Integer id, Room room) throws SQLException {
        String sql = "UPDATE rooms SET roomNumber = ?, beds = ?, price = ?, status = ?, available = ? WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setInt(1, room.getRoomNumber());
        ps.setInt(2,room.getBeds());
        ps.setFloat(3,room.getPrice());
        ps.setString(4,room.getStatus());
        ps.setBoolean(5,room.isAvailable());
        ps.setInt(6, id);

        ps.executeUpdate();

        // we also need to modify all the RoomServices related to the room
        for (RoomService roomService: room.getServices()) {
            roomServiceService.modify(roomService);
        }
    }


}
