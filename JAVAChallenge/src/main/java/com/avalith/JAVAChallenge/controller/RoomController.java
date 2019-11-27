package com.avalith.JAVAChallenge.controller;

import com.avalith.JAVAChallenge.domain.CheckIn;
import com.avalith.JAVAChallenge.domain.Room;
import com.avalith.JAVAChallenge.service.CheckInService;
import com.avalith.JAVAChallenge.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.sql.SQLException;
import java.util.List;

@RestController()
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    RoomService roomService;
    @Autowired
    CheckInService checkinService;


    static final String AVAILABLE = "Available";
    static final String OCCUPIED = "Occupied";
    static final String CLEANING = "Cleaning";
    static final String IN_MAINTENANCE = "In Maintenance";

    /**
     * Saves a room in the database
     * @param room
     *
     */
    @PostMapping("/add")
    public void addRoom(@RequestBody Room room) {
        try {
            roomService.addRoom(room);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns all the rooms
     *
     */
    @GetMapping("/getAll")
    public List<Room> getAll() {
        List<Room> rooms = roomService.getAll();
        return rooms;
    }

    /**
     * Changes the state of a room
     * @param room_number
     * @param newStatus : the new state of the room --> must be AVAILABLE, OCCUPIED, CLEANING or IN_MAINTENANCE
     */
    @PatchMapping("changeStatus/{room_number}")
    public void changeRoomStatus(@PathVariable Integer room_number, @RequestBody String newStatus){
        try {
            roomService.changeRoomStatus(room_number, newStatus);
            boolean available = false;
            if (newStatus.equalsIgnoreCase(AVAILABLE)){ // if a room is occupied, being cleaned or in maintenance, then it is not available to occupy
                available = true;
            }
            roomService.changeRoomAvailability(room_number, available);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Database error");
        }

    }

    /**
     * To occupy a room for a customer
     * @param checkin : with all the information we need to occupy a room, wich is number of days, number of occupants, the room number and the customer information
     * @return the checkin number for the customer
     */
    @PostMapping("checkin")
    public Integer checkIn(@RequestBody CheckIn checkin){
        Integer checkinNumber = null;
        try {
            Room room = roomService.getRoomByNumber(checkin.getRoomNumber());
            if (room == null){ // means the room doesn´t exist
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "The room number doesnt exist");
            }
            if (!room.isAvailable()){
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "The room is not available");
            }else if(room.getBeds() < checkin.getOccupants()){
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "The occupants exceed number of beds");
            }
            else { // we can occupy the room
                checkinNumber = checkinService.addCheckIn(checkin); // saves the checkin in the database and gets the checkin number for the customer
                changeRoomStatus(room.getRoomNumber(),OCCUPIED); // if everything goes well, we can finish the checkin process and occupy the room
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Database error");
        }
        return checkinNumber;
    }

    /**
     * Process in which a client leaves the room and becomes available again
     * @param checkInId : the checkin number
     * @return the total price of the stay
     */
    @PostMapping("checkout")
    public float checkOut(@RequestBody Integer checkInId){
        float totalPrice = 0;
        try {
            CheckIn checkIn = checkinService.getCheckinById(checkInId); // searches the checkin so we can get the room number that corresponds to that checkin
            Room room = roomService.getRoomByNumber(checkIn.getRoomNumber()); // then we search the room to make it available again
            if ((!room.isAvailable()) && checkIn!=null){
                totalPrice = room.getPrice() * checkIn.getNumberOfDays();
                changeRoomStatus(room.getRoomNumber(),AVAILABLE);
            }else {
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "The room isn´t occupied or the checkin doesnt exists");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalPrice;
    }


    /**
     *
     * To filter the rooms by availability
     * @param available : true or false
     * @return returns the list of rooms filtered by availability
     */
    @GetMapping("")
    public List<Room> getAllByAvailability(@RequestParam boolean available){
        List<Room> rooms = null;
        try {
            rooms = roomService.getAllByAvailability(available);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Database error");
        }
        return rooms;
    }


    /**
     * Modifies the information of a room
     * @param id : the id of the room we want to modify
     * @param room : the modified room
     */
    @PatchMapping("modify/{id}")
    public void modifyRoom (@PathVariable Integer id, @RequestBody Room room){
        try {
            roomService.modify(id, room);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Database error");
        }
    }


}
