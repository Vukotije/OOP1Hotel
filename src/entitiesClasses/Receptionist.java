package entitiesClasses;

import java.time.LocalDate;

import entitiesEnums.EducationLevel;
import entitiesEnums.Gender;
import entitiesEnums.UserRoles;

public class Receptionist extends Employee {	

	public static final UserRoles ROLE = UserRoles.RECEPTIONIST;
	public static final double baseSalary = 75000.00;
	private double salary;


//	==============================================================================================
//										COSTRUCTORS
//	==============================================================================================
	
	
	public Receptionist(String name, String surname, Gender gender, LocalDate birthdate,
			String phone, String address, String username, String password,
			EducationLevel educationLevel, double experience) {
		
		super(name, surname, gender, birthdate, phone, address, username, password, educationLevel, experience);
		this.salary = generateSalary(baseSalary, experience, educationLevel);
	}
	
	public Receptionist(String id, String name, String surname, Gender gender, LocalDate birthdate,
			String phone, String address, String username, String password,
			EducationLevel educationLevel, double experience) {
		
		super(name, surname, gender, birthdate, phone, address, username, password, educationLevel, experience);
		this.salary = generateSalary(baseSalary, experience, educationLevel);
		setId(id);
	}

	public Receptionist(String id, double salary, String name, String surname, Gender gender,
			LocalDate birthdate, String phone, String address, String username, String password,
			EducationLevel educationLevel, double experience) {
		
		super(name, surname, gender, birthdate, phone, address, username, password,
				educationLevel, experience);
		setSalary(salary);
		setId(id);
	}

//	==============================================================================================
//										GETTERS & SETTERS
//	==============================================================================================
	
	@Override
	public UserRoles getRole() {
		return ROLE;
	}

	@Override
	public double getSalary() {
		return salary;
	}
	
	@Override
	public void setSalary(double salary) {
		this.salary = salary;
	}
	
//	==============================================================================================
//										TO STRING
//	==============================================================================================
	
	@Override
	public String toString() {
		return "Receptionist [" + super.toString() + "salary=" + salary + "]";
	}
}