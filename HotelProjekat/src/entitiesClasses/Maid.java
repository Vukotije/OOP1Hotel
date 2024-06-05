package entitiesClasses;

import java.time.LocalDate;
import java.util.ArrayList;

import entitiesEnums.EducationLevel;
import entitiesEnums.Gender;
import entitiesEnums.UserRoles;

public class Maid extends Employee{

	public static final UserRoles ROLE = UserRoles.MAID;
	public static final double baseSalary = 65000.00;
	private double salary;
	private ArrayList<LocalDate> RoomsCleaned; // dates when rooms was cleaned
	private ArrayList<LocalDate> RoomsAssignedToClean; // dates when rooms was assigned to clean	
	private ArrayList<Room> RoomsLeftToClean;   // rooms that haveto be cleaned
	
//	==============================================================================================
//										COSTRUCTORS
//	==============================================================================================
	
	public Maid(String name, String surname, Gender gender, LocalDate birthdate, String phone, String address,
			String username, String password, EducationLevel educationLevel, double experience) {
		
		super(name, surname, gender, birthdate, phone, address, username, password, educationLevel, experience);
		this.salary = generateSalary(baseSalary, experience, educationLevel);
		this.RoomsCleaned = new ArrayList<LocalDate>();
		this.RoomsAssignedToClean = new ArrayList<LocalDate>();
		this.RoomsLeftToClean = new ArrayList<Room>();
	}
	
	public Maid(String id, double salary, ArrayList<LocalDate> roomsCleaned,
			ArrayList<LocalDate> RoomsAssignedToClean, ArrayList<Room> roomsLeftToClean,
			String name, String surname, Gender gender, LocalDate birthdate, String phone,
			String address,String username, String password, EducationLevel educationLevel, double experience) {
		
		super(name, surname, gender, birthdate, phone, address, username, password, educationLevel, experience);
		setSalary(salary);
		setRoomsCleaned(roomsCleaned);
		setRoomsAssignedToClean(RoomsAssignedToClean);
		setRoomsLeftToClean(roomsLeftToClean);
		setId(id);
	}
	
//	==============================================================================================
//										GETTERS & SETTERS
//	==============================================================================================
	

	public static UserRoles getRole() {
		return ROLE;
	}
	
	@Override
	public double getSalary() {
		return salary;
	}

	public ArrayList<LocalDate> getRoomsCleaned() {
		return RoomsCleaned;
	}

	public void setRoomsCleaned(ArrayList<LocalDate> roomsCleaned) {
		RoomsCleaned = roomsCleaned;
	}
	
	public ArrayList<LocalDate> getRoomsAssignedToClean() {
		return RoomsAssignedToClean;
	}

	public void setRoomsAssignedToClean(ArrayList<LocalDate> roomsAssignedToClean) {
		RoomsAssignedToClean = roomsAssignedToClean;
	}
	
	public ArrayList<Room> getRoomsLeftToClean() {
		return RoomsLeftToClean;
	}

	public void setRoomsLeftToClean(ArrayList<Room> roomsLeftToClean) {
		RoomsLeftToClean = roomsLeftToClean;
	}	

//	==============================================================================================
//											TO STRING
//	==============================================================================================
	
	@Override
	public String toString() {
		return "Maid [" + super.toString() + "salary=" + salary + "]";
	}
	
//	==============================================================================================
//										OTHER METHODS
//	==============================================================================================
	
	public void addRoomLeftToClean(Room room) {
		RoomsLeftToClean.add(room);
		RoomsAssignedToClean.add(LocalDate.now());
	}
	
	public void removeRoomLeftToClean(Room room) {
		RoomsLeftToClean.remove(room);
		RoomsCleaned.add(LocalDate.now());
	}
	
	public int DateCounter(ArrayList<LocalDate> dates) {
        LocalDate today = LocalDate.now();
        int count = 0;
        for (LocalDate date : dates) {
            if (date.equals(today)) {
                count++;
            }
        }
        return count;
	    
	}

}
