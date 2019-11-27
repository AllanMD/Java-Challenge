package com.avalith.JAVAChallenge.service;

import com.avalith.JAVAChallenge.domain.Customer;
import com.avalith.JAVAChallenge.domain.Room;
import com.avalith.JAVAChallenge.repository.RoomRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
@Data
public class RoomService {
    @Autowired
    RoomRepository roomRepository;

    public void addRoom(Room room) throws Exception {
        roomRepository.addRoom(room);
    }

    public List<Room> getAll() {
        return roomRepository.getAll();
    }

    public void changeRoomStatus(int room_number, String newStatus) throws SQLException {
        roomRepository.changeRoomStatus(room_number, newStatus);
    }

    public void changeRoomAvailability (int room_number, boolean available) throws SQLException {
        roomRepository.changeRoomAvailability(room_number,available);
    }

    public Room getRoomByNumber(int room_number) throws SQLException {
        return roomRepository.getByRoomNumber(room_number);
    }

    public List<Room> getAllByAvailability(boolean available) throws SQLException {
        return roomRepository.getAllByAvailability(available);
    }

    public void modify (Integer id, Room room) throws SQLException {
        roomRepository.modify(id, room);
    }

}
