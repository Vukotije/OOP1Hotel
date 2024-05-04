package entitiesClasses;

import java.util.EnumMap;

import entitiesEnums.RoomFacilities;
import entitiesEnums.RoomStatus;
import entitiesEnums.RoomType;

public class Room extends BaseEntity {

    private int roomNumber;
    private RoomType roomType;
    private RoomStatus roomStatus;
    private EnumMap<RoomFacilities, Boolean> roomFacilitiesAvailablity;

    private static EnumMap<RoomType, Integer> roomTypeCounts = new EnumMap<>(RoomType.class);

	public Room(int roomNumber, RoomType roomType, RoomStatus roomStatus,
			EnumMap<RoomFacilities, Boolean> roomFacilitiesAvailablity) {
	    super();
	    setRoomNumber(roomNumber);
    	this.roomType = roomType;
	    setRoomStatus(roomStatus);
	    this.roomFacilitiesAvailablity = new EnumMap<>(RoomFacilities.class);
	    for (RoomFacilities facility : RoomFacilities.values()) {
	    	this.roomFacilitiesAvailablity.put(facility, false);
	     }
	    this.roomFacilitiesAvailablity.putAll(roomFacilitiesAvailablity);
	    
        roomTypeCounts.put(roomType, roomTypeCounts.getOrDefault(roomType, 0) + 1);
	}
	
	public static int getRoomCountByType(RoomType roomType) {
        return roomTypeCounts.getOrDefault(roomType, 0);
    }

    @Override
	public String toString() {
		return "Room [roomNumber=" + roomNumber + ", roomType=" + roomType + ", roomStatus=" + roomStatus
				+ ", roomFacilities=" + roomFacilitiesAvailablity + "]";
	}

	public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
    	RoomType oldRoomType = getRoomType();
    	if (!oldRoomType.equals(roomType)) { 
			roomTypeCounts.put(oldRoomType, getRoomCountByType(oldRoomType) - 1);
			roomTypeCounts.put(roomType, getRoomCountByType(roomType) + 1);
    	}
    	this.roomType = roomType;
    }

    public RoomStatus getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(RoomStatus roomStatus) {
        this.roomStatus = roomStatus;
    }

    public void setRoomFacilityAvailablity(RoomFacilities roomFacilities, boolean value) {
        this.roomFacilitiesAvailablity.put(roomFacilities, value);
    }

    public boolean getRoomFacilityAvailablity(RoomFacilities roomFacilities) {
        return this.roomFacilitiesAvailablity.get(roomFacilities);
    }

    public EnumMap<RoomFacilities, Boolean> getRoomFacilitiesAvailablity() {
        return this.roomFacilitiesAvailablity;
    }
}
