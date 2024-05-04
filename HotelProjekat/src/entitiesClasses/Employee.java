package entitiesClasses;

import java.time.LocalDate;

import entitiesEnums.EducationLevel;
import entitiesEnums.Gender;

public abstract class Employee extends User {

	private EducationLevel educationLevel;
	private double experience;
	private double salary;

	public Employee(String name, String surname, Gender gender, LocalDate birthdate, String phone, String address,
			String username, String password, EducationLevel educationLevel, double experience, double salary) {
		
        super(name, surname, gender, birthdate, phone, address, username, password);
        setEducationLevel(educationLevel);
		setExperience(experience);
		setSalary(salary);

	}

	@Override
	public String toString() {
		return super.toString() + "educationLevel=" + educationLevel +
				", experience=" + experience + ", salary=" + salary;
	}

	public EducationLevel getEducationLevel() {
		return educationLevel;
	}

	public void setEducationLevel(EducationLevel educationLevel) {
		this.educationLevel = educationLevel;
	}

	public double getExperience() {
		return experience;
	}

	public void setExperience(double experience) {
		this.experience = experience;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}


}
