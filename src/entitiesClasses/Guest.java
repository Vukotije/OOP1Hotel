package entitiesClasses;

import java.time.LocalDate;

import entitiesEnums.Gender;
import entitiesEnums.UserRoles;

public class Guest extends User {
	
	public static final UserRoles ROLE = UserRoles.GUEST;
	
//	==============================================================================================
//										CONSTRUCTORS
//	==============================================================================================
	
	public Guest (String name, String surname, Gender gender, LocalDate birthdate,
			String phone, String address, String username, String password) {
		
		super(name, surname, gender, birthdate, phone, address, username, password);
	}
	
	public Guest (String id, String name, String surname, Gender gender, LocalDate birthdate,
			String phone, String address, String username, String password) {
		
		super(name, surname, gender, birthdate, phone, address, username, password);
		setId(id);
	}
	

//	==============================================================================================
//										GETTERS & SETTERS
//	==============================================================================================

	@Override
	public UserRoles getRole() {
		return ROLE;
	}
	
//	==============================================================================================
//										TO STRING
//	==============================================================================================

	@Override
	public String toString() {
		return "Guest [" + super.toString() + "]";
	}
	
	
}
