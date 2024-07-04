package UnitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import entitiesClasses.Room;
import entitiesEnums.RoomStatus;
import entitiesEnums.RoomType;
import entitiesManager.RoomManager;

public class RoomManagerTest {

    private RoomManager roomManager;

    @Before
    public void setUp() {
        roomManager = RoomManager.getInstance();
        roomManager.setRoomData(new HashMap<>());
    }

    @Test
    public void testAddRoom() {
        Room room = new Room("R001", 101, RoomType.ONE_PERSON_ROOM_ONE_SINGLE_BED, RoomStatus.AVAILABLE, null, new HashSet<>());
        roomManager.addRoom(room);

        assertEquals(room, roomManager.getRoom("R001"));
    }

    @Test
    public void testRemoveRoom() {
        Room room = new Room("R001", 101, RoomType.ONE_PERSON_ROOM_ONE_SINGLE_BED, RoomStatus.AVAILABLE, null, new HashSet<>());
        roomManager.addRoom(room);
        roomManager.removeRoom("R001");

        assertNull(roomManager.getRoom("R001"));
    }

    @Test
    public void testGetRoomByNumber() {
        Room room = new Room("R001", 101, RoomType.THREE_PERSON_ROOM_THREE_SINGLE_BEDS, RoomStatus.AVAILABLE, null, new HashSet<>());
        roomManager.addRoom(room);

        assertEquals(room, roomManager.getRoomByNumber(101));
    }

    @Test
    public void testCheckOut() {
        Room room = new Room("R001", 101, RoomType.TWO_PERSON_ROOM_ONE_DOUBLE_BED, RoomStatus.OCCUPIED, "RES001", new HashSet<>());
        roomManager.addRoom(room);

        roomManager.checkOut("R001");

        assertEquals(RoomStatus.CLEANING, room.getRoomStatus());
        assertNull(room.getReservationId());
    }
}
