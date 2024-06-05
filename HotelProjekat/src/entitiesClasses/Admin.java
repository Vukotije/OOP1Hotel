package entitiesClasses;

import java.time.LocalDate;

import entitiesEnums.EducationLevel;
import entitiesEnums.Gender;
import entitiesEnums.UserRoles;

public class Admin extends Employee {
	
	public static final UserRoles ROLE = UserRoles.ADMIN;
	public static final double baseSalary = 90000.00;
	private double salary;


//	==============================================================================================
//											CONSTRUCTORS
//	==============================================================================================
	
	
	public Admin(String name, String surname, Gender gender, LocalDate birthdate, String phone,
			String address, String username, String password,
			EducationLevel educationLevel, double experience) {
		
		super(name, surname, gender, birthdate, phone,
				address, username, password, educationLevel, experience);
		this.salary = generateSalary(baseSalary, experience, educationLevel);
	}
	
	
	public Admin(String id, double sallary, String name, String surname, Gender gender,
			LocalDate birthdate, String phone, String address,String username,
			String password, EducationLevel educationLevel, double experience) {
		
		super(name, surname, gender, birthdate, phone, address, username, password, educationLevel, experience);
		setSalary(salary);
		setId(id);
	}
	

//	==============================================================================================
//											GETERS & SETTERS
//	==============================================================================================
	

	public static UserRoles getRole() {
		return ROLE;
	}

	@Override
	public double getSalary() {
		return salary;
    }
		

//	==============================================================================================
//											TO STRING
//	==============================================================================================
	
	
	@Override
	public String toString() {
		return "Admin [" + super.toString() + "salary=" + salary + "]";
	}




}
