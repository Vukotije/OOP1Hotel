package entitiesClasses;

import java.time.LocalDate;

import entitiesEnums.EducationLevel;
import entitiesEnums.Gender;

public abstract class Employee extends User {

	private EducationLevel educationLevel;
	private double experience;
	private double baseSalary;
	

//	==============================================================================================
//											CONSTRUCTORS
//	==============================================================================================
	
	public Employee(String name, String surname, Gender gender, LocalDate birthdate, String phone, String address,
			String username, String password, EducationLevel educationLevel, double experience) {
		
        super(name, surname, gender, birthdate, phone, address, username, password);
        this.educationLevel = educationLevel;
        this.experience = experience;

	}

//	==============================================================================================
//											GETTERS AND SETTERS
//	==============================================================================================
	
	public EducationLevel getEducationLevel() {
		return educationLevel;
	}

	public void setEducationLevel(EducationLevel educationLevel) {
		this.educationLevel = educationLevel;
		this.setSalary(generateSalary(baseSalary, experience, educationLevel));
	}

	public double getExperience() {
		return experience;
	}

	public void setExperience(double experience) {
		this.experience = experience;
		this.setSalary(generateSalary(baseSalary, experience, educationLevel));
	}
	
	public abstract double getSalary();
	
	public abstract void setSalary(double salary);
	
	public double getBaseSalary() {
		return baseSalary;
	}
	
//	==============================================================================================
//											TO STRING
//	==============================================================================================

	@Override
	public String toString() {
		return super.toString() + "educationLevel=" + educationLevel +
				", experience=" + experience + ";";
	}
	
//	==============================================================================================
//											OTHER METHODS
//	==============================================================================================
	
	
	// Konacna plata se racuna po formuli:
	// Konacna plata = bazna plata + 1,000 * (broj godina starost + nivo obrazovanja)	
	public double generateSalary(double baseSalary, double experience, EducationLevel educationLevel) {
		return baseSalary + 1000 * (experience + educationLevel.getValue());
	}		

}
