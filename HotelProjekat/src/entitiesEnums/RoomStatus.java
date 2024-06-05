package entitiesEnums;

public enum RoomStatus {
	AVAILABLE("Available"),
	OCCUPIED("Occupied"),
	CLEANING("Cleaning");
	
	private final String displayName;
	
	RoomStatus(String displayName) {
		this.displayName = displayName;
	}

//	==============================================================================================
//											STRING
//	==============================================================================================
	
	@Override
	public String toString() {
		return this.displayName;
	}
	
	public static RoomStatus fromString(String value) {
		for (RoomStatus roomStatus : RoomStatus.values()) {
			if (roomStatus.toString().equals(value)) {
				return roomStatus;
			}
		}
		throw new IllegalArgumentException("No enum constant Room Status with value " + value);
	}
}
