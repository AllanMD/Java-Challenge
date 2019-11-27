package com.avalith.JAVAChallenge.service;

import com.avalith.JAVAChallenge.domain.RoomService;
import com.avalith.JAVAChallenge.repository.RoomRepository;
import com.avalith.JAVAChallenge.repository.RoomServiceRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
@Data
public class RoomServiceService {
    @Autowired
    RoomServiceRepository roomServiceRepository;

    public void addService (RoomService roomService, int roomId) throws SQLException {
        roomServiceRepository.addService(roomService, roomId);
    }

    public List<RoomService> getServicesByRoomId(int roomId) throws SQLException {
        return roomServiceRepository.getServicesByRoomId(roomId);
    }

    public void modify (RoomService roomService) throws SQLException {
        roomServiceRepository.modify(roomService);
    }
}
