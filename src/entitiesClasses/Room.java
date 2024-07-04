package entitiesClasses;

import java.util.Set;

import entitiesEnums.RoomStatus;
import entitiesEnums.RoomType;
import entitiesManager.ReservationManager;

public class Room extends BaseEntity {

    private int roomNumber;
    private RoomType roomType;
    private RoomStatus roomStatus;
    private String reservationId;
    private Set<RoomFacility> roomFacilitiesAvailable;

//	==============================================================================================
//										CONSTRUCTORS
//	==============================================================================================
	
	public Room(int roomNumber, RoomType roomType, RoomStatus roomStatus,
			Set<RoomFacility> roomFacilitiesAvailable) {
	    super();
    	setRoomType(roomType);
	    setRoomNumber(roomNumber);
	    setRoomStatus(roomStatus);
	    reservationId = null;
	    setRoomFacilitiesAvailable(roomFacilitiesAvailable);
	}

	public Room(String id, int roomNumber, RoomType roomType, RoomStatus roomStatus,
			Set<RoomFacility> roomFacilitiesAvailable) {
	    super();
    	setRoomType(roomType);
	    setRoomNumber(roomNumber);
	    setRoomStatus(roomStatus);
	    setReservationId(reservationId);
	    setRoomFacilitiesAvailable(roomFacilitiesAvailable);
	    setId(id);
	}
	public Room(String id, int roomNumber, RoomType roomType, RoomStatus roomStatus,
			String reservationId, Set<RoomFacility> roomFacilitiesAvailable) {
	    super();
    	setRoomType(roomType);
	    setRoomNumber(roomNumber);
	    setRoomStatus(roomStatus);
	    setReservationId(reservationId);
	    setRoomFacilitiesAvailable(roomFacilitiesAvailable);
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

    public Set<RoomFacility> getRoomFacilitiesAvailable() {
        return this.roomFacilitiesAvailable;
    }
    
	public void setRoomFacilitiesAvailable(Set<RoomFacility> roomFacilitiesAvailable) {
		this.roomFacilitiesAvailable = roomFacilitiesAvailable;
	}
	
	public String getReservationId() {
		return reservationId;
	}
	
	public void setReservationId(String reservationId) {
		this.reservationId = reservationId;
	}
	
	public Reservation getReservation(String reservationId) {
		return ReservationManager.getInstance().getReservation(reservationId);
	}
	
//	==============================================================================================
//										TO STRING
//	==============================================================================================

	@Override
	public String toString() {
		return "Room [roomNumber=" + roomNumber + ", roomType=" + roomType + ", roomStatus=" + roomStatus
				+ ", reservationId=" + reservationId + ", roomFacilitiesAvailable=" + roomFacilitiesAvailable + "]";
	}
	
}
