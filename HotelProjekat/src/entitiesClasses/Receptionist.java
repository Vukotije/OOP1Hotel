package entitiesClasses;

import java.time.LocalDate;

import entitiesEnums.EducationLevel;
import entitiesEnums.Gender;
import entitiesEnums.UserRoles;

public class Receptionist extends Employee {	

	public static final UserRoles ROLE = UserRoles.RECEPTIONIST;
	
	public Receptionist(String name, String surname, Gender gender, LocalDate birthdate, String phone, String address,
		String username, String password, EducationLevel educationLevel, double experience, double salary) {
		
		super(name, surname, gender, birthdate, phone, address, username, password, educationLevel, experience, salary);
	}

	@Override
	public String toString() {
		return "Receptionist [" + super.toString() + "]";
	}

	
}