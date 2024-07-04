package UnitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import entitiesClasses.RoomFacility;
import entitiesManager.RoomFacilityManager;

public class RoomFacilityManagerTest {

    private RoomFacilityManager roomFacilityManager;

    @Before
    public void setUp() {
        roomFacilityManager = RoomFacilityManager.getInstance();
        roomFacilityManager.setRoomFacilityData(new HashMap<>());
    }

    @Test
    public void testAddRoomFacility() {
        RoomFacility facility = new RoomFacility("Air Conditioning", "RF001");
        roomFacilityManager.addRoomFacility(facility);
        assertEquals(facility, roomFacilityManager.getRoomFacility("RF001"));
    }

    @Test
    public void testDeleteRoomFacility() {
        RoomFacility facility = new RoomFacility("Air Conditioning", "RF001");
        roomFacilityManager.addRoomFacility(facility);
        roomFacilityManager.deleteRoomFacility("RF001");
        assertNull(roomFacilityManager.getRoomFacility("RF001"));
    }

    @Test
    public void testGetRoomFacilityByName() {
        RoomFacility facility = new RoomFacility("Air Conditioning", "RF001");
        roomFacilityManager.addRoomFacility(facility);
        assertEquals(facility, roomFacilityManager.getRoomFacilityByName("Air Conditioning"));
    }

    @Test
    public void testGetRoomFacilityByName_NotFound() {
        assertNull(roomFacilityManager.getRoomFacilityByName("NonExistentFacility"));
    }
}