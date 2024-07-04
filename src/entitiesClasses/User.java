package entitiesClasses;

import java.time.LocalDate;

import entitiesEnums.Gender;
import entitiesEnums.UserRoles;

public abstract class User extends BaseEntity{
	
	private String name;
	private String surname;
	private Gender gender;
	private LocalDate birthdate;
	private String phone;
	private String address;
	private String username;
	private String password;

//	==============================================================================================
//										CONSTRUCTORS
//	==============================================================================================
	
	public User(String name, String surname, Gender gender, LocalDate birthdate,
			String phone, String address, String username, String password){
		super();
        setName(name);
        setSurname(surname);
        setGender(gender);
        setBirthdate(birthdate);
        setPhone(phone);
        setAddress(address);
        setUsername(username);
        setPassword(password);
    }

//	==============================================================================================
//										GETTERS & SETTERS
//	==============================================================================================

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public abstract UserRoles getRole();
	
//	==============================================================================================
//										TO STRING
//	==============================================================================================
	
	@Override
	public String toString() {
		return "name=" + name + ", surname=" + surname + ", gender=" + gender + ", birthdate=" + birthdate
				+ ", phone=" + phone + ", address=" + address + ", username=" + username + ", password=" + password;
	}

}