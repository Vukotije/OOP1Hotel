package entitiesEnums;

public enum UserRoles {
	GUEST("Guest"),
	MAID("Maid"),
	RECEPTIONIST("Receptionist"),
	ADMIN("Admin");
	
	private final String displayName;
	
	UserRoles(String displayName) {
		this.displayName = displayName;
	}

//	==============================================================================================
//											STRING
//	==============================================================================================
	
	@Override
	public String toString() {
		return this.displayName;
	}
	
	public static UserRoles fromString(String value) {
		for (UserRoles role : UserRoles.values()) {
			if (role.toString().equals(value)) {
				return role;
			}
		}
		throw new IllegalArgumentException("No enum constant User Roles with value " + value);
	}

}
