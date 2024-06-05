package entitiesEnums;

public enum Gender {
	MALE("Male"),
	FEMALE("Female");
	
	private final String displayName;
	
	Gender(String displayName) {
		this.displayName = displayName;
	}

//	==============================================================================================
//											STRING
//	==============================================================================================
	
	@Override
	public String toString() {
		return this.displayName;
	}
	
	public static Gender fromString(String value) {
		for (Gender gender : Gender.values()) {
			if (gender.toString().equals(value)) {
				return gender;
			}
		}
		throw new IllegalArgumentException("No enum constant Gender with value " + value);
	}

}
