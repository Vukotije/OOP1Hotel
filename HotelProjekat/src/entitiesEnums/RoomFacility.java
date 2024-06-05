package entitiesEnums;

public enum RoomFacility {
	AC_UNIT("AC Unit"),
    BALCONY("Balcony"),
    TV("TV"),
    SMOKING_ROOM("Smoking Room");
	
	private String displayName;
	
	private RoomFacility(String displayName) {
		this.displayName = displayName;
	}

//	==============================================================================================
//											STRING
//	==============================================================================================
	
	@Override
	public String toString() {
		return displayName;
	}
	
	public static RoomFacility fromString(String value) {
		for (RoomFacility facility : RoomFacility.values()) {
			if (facility.toString().equals(value)) {
				return facility;
			}
		}
		throw new IllegalArgumentException("No enum constant Room Facilitiy with value " + value);
	}
	
	
}
