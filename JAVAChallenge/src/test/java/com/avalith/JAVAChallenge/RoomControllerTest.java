package com.avalith.JAVAChallenge;

import com.avalith.JAVAChallenge.controller.RoomController;
import com.avalith.JAVAChallenge.domain.Room;
import com.avalith.JAVAChallenge.domain.RoomService;
import junit.framework.TestCase;
import org.junit.Assert;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class RoomControllerTest extends TestCase {

    @Mock
    RoomController roomController;

    public void testGetAll(){
        RoomService roomService = new RoomService(1,"Seaview");
        List<RoomService> services = new ArrayList<RoomService>();
        services.add(roomService);
        Room room = new Room(1,1,2,322f,"Available", services, true);

        List<Room> expectedList = new ArrayList<Room>();
        expectedList.add(room);

        initMocks(this);
        when(roomController.getAll()).thenReturn(expectedList);

        List<Room> rooms = roomController.getAll();
        Assert.assertEquals(rooms, expectedList);
    }

    public void testCheckOut(){
        initMocks(this);
        when(roomController.checkOut(1)).thenReturn(500f);
        float totalPrice = roomController.checkOut(1);

        Assert.assertEquals(totalPrice, 500f, 0.0002); //delta parameter: the maximum difference between expected and actual for which both numbers are still considered equal.

    }
}
