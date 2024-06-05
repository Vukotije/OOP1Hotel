package entitiesEnums;

public enum ReservationStatus {
	PENDING("Pending"),
	APPROVED("Approved"),
	DENIED("Denied"),
	CANCELLED("Cancelled");
	
	private final String displayName;
	
	ReservationStatus(String displayName) {
		this.displayName = displayName;
	}

//	==============================================================================================
//											STRING
//	==============================================================================================
	
	@Override
	public String toString() {
		return this.displayName;
	}
	
	public static ReservationStatus fromString(String value) {
		for (ReservationStatus status : ReservationStatus.values()) {
			if (status.toString().equals(value)) {
				return status;
			}
		}
		throw new IllegalArgumentException("No enum constant Reservation Status with value " + value);
	}
}
