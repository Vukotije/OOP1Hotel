package entitiesEnums;

public enum RoomType {
	ONE_PERSON_ROOM_ONE_SINGLE_BED("One person room with one single bed"),
	TWO_PERSON_ROOM_ONE_DOUBLE_BED("Two person room with one double bed"),
	TWO_PERSON_ROOM_TWO_SINGLE_BEDS("Two person room with two single beds"),
	THREE_PERSON_ROOM_THREE_SINGLE_BEDS("Three person room with three single beds"),
	THREE_PERSON_ROOM_ONE_DOUBLE_BED_ONE_SINGLE_BED("Three person room with one double bed and one single bed");
	
	private final String displayName;
	
	RoomType(String displayName) {
		this.displayName = displayName;
	}

//	==============================================================================================
//											STRING
//	==============================================================================================
		
	@Override
	public String toString() {
		return this.displayName;
	}
	
	public static RoomType fromString(String value) {
		for (RoomType roomType : RoomType.values()) {
			if (roomType.toString().equals(value)) {
				return roomType;
			}
		}
		throw new IllegalArgumentException("No enum constant Room Type with value " + value);
	}
	
}
