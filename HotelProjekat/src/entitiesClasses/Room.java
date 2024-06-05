package entitiesClasses;

import java.util.EnumMap;

import entitiesEnums.RoomFacility;
import entitiesEnums.RoomStatus;
import entitiesEnums.RoomType;

public class Room extends BaseEntity {

    private int roomNumber;
    private RoomType roomType;
    private RoomStatus roomStatus;
    private EnumMap<RoomFacility, Boolean> roomFacilitiesAvailablity;

//	==============================================================================================
//										CONSTRUCTORS
//	==============================================================================================
	
	public Room(int roomNumber, RoomType roomType, RoomStatus roomStatus,
			EnumMap<RoomFacility, Boolean> roomFacilitiesAvailablity) {
	    super();
    	setRoomType(roomType);
	    setRoomNumber(roomNumber);
	    setRoomStatus(roomStatus);
	    setRoomFacilitiesAvailablity(roomFacilitiesAvailablity);
	}
	
	public Room(String id, int roomNumber, RoomType roomType, RoomStatus roomStatus,
			EnumMap<RoomFacility, Boolean> roomFacilitiesAvailablity) {
	    super();
    	setRoomType(roomType);
	    setRoomNumber(roomNumber);
	    setRoomStatus(roomStatus);
	    setRoomFacilitiesAvailablity(roomFacilitiesAvailablity);
	    setId(id);
	}

//	==============================================================================================
//										GETTERS & SETTERS
//	==============================================================================================
	
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
    	this.roomType = roomType;
    }

    public RoomStatus getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(RoomStatus roomStatus) {
        this.roomStatus = roomStatus;
    }

    public EnumMap<RoomFacility, Boolean> getRoomFacilitiesAvailablity() {
        return this.roomFacilitiesAvailablity;
    }
    
	public void setRoomFacilitiesAvailablity(EnumMap<RoomFacility, Boolean> roomFacilitiesAvailablity) {
		this.roomFacilitiesAvailablity = roomFacilitiesAvailablity;
	}
//	==============================================================================================
//										TO STRING
//	==============================================================================================
	
    @Override
	public String toString() {
		return "Room [roomNumber=" + roomNumber + ", roomType=" + roomType + ", roomStatus=" + roomStatus
				+ ", roomFacilities=" + roomFacilitiesAvailablity + "]";
	}
    

}
