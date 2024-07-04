package entitiesManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import entitiesClasses.Admin;
import entitiesClasses.Employee;
import entitiesClasses.Maid;
import entitiesClasses.Receptionist;
import entitiesClasses.Room;
import entitiesEnums.EducationLevel;
import entitiesEnums.Gender;
import entitiesEnums.UserRoles;

public class EmployeeManager implements CSVSupport{

    private static EmployeeManager instance = null;
    private HashMap<String, Employee> employeeData;

//	==============================================================================================
//										CONSTRUCTOR & INSTANCE
//	==============================================================================================
	
    private EmployeeManager() {
    	employeeData = new HashMap<>();
    	readFromCSV();
    }
    
	public static EmployeeManager getInstance() {
		if (instance == null) {
			instance = new EmployeeManager();
		}
		return instance;
	}

//	==============================================================================================
//											CSV SUPPORT
//	==============================================================================================
	
	@Override
	public void writeToCSV() {
		String filePath = "storage/employeeData.csv";
		try (FileWriter writer = new FileWriter(filePath)) {
			for (String id : employeeData.keySet()) {
				Employee employee = getEmployee(id);
	            StringBuilder sb = new StringBuilder();
	            
	            

	            sb.append(id)
	                .append(',')
	            	.append(employee.getRole().toString())
	            	.append(',')
	                .append(employee.getName())
	                .append(',')
	                .append(employee.getSurname())
	                .append(',')
	                .append(employee.getGender().toString())
	                .append(',')
	                .append(employee.getBirthdate().toString())
	                .append(',')
	                .append(employee.getPhone())
	                .append(',')
	                .append(employee.getAddress())
	                .append(',')
	                .append(employee.getUsername())
	                .append(',')
	                .append(employee.getPassword())
					.append(',')
					.append(employee.getEducationLevel().toString())
					.append(',')
					.append(Double.toString(employee.getExperience()))
					.append(',')
					.append(Double.toString(employee.getSalary()))
					.append(',');
	            
	            // Support for Maids
				if (employee instanceof Maid) {
					
					sb.append(' ').append(',');
					
					for (LocalDate date : ((Maid) employee).getRoomsCleaned()) {
						sb.append(date.toString()).append(',');
					}
				    
				    sb.append(' ').append(',');
				    
                	for (LocalDate date : ((Maid) employee).getRoomsAssignedToClean()) {
                        sb.append(date.toString()).append(',');
				    }
				    
				    sb.append(' ').append(',');
				    
					for (Room room : ((Maid) employee).getRoomsLeftToClean()) {
						sb.append(room.getId()).append(',');
					}
				}				
								
				sb.deleteCharAt(sb.length() - 1);
                sb.append('\n');
                
                writer.append(sb.toString());
                                
                }
			} catch (IOException e) {
			System.err.println("Error writing to CSV file: " + e.getMessage());
		}
	}

	@Override
	public void readFromCSV() {
		String filePath = "storage/employeeData.csv";
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] values = line.split(",");
				
				String id = values[0];
				UserRoles role = UserRoles.fromString(values[1]);
                String name = values[2];
                String surname = values[3];
                Gender gender = Gender.fromString(values[4]);
                LocalDate birthdate = LocalDate.parse(values[5]);
                String phone = values[6];
                String address = values[7];
                String username = values[8];
                String password = values[9];
                EducationLevel educationLevel = EducationLevel.fromString(values[10]);
                double experience = Double.parseDouble(values[11]);
                double salary = Double.parseDouble(values[12]);
                System.out.println(salary);
                
                if (role.equals(UserRoles.ADMIN)) {
                	addEmployee(new Admin(id, salary, name, surname, gender, birthdate, phone,
                			address, username, password, educationLevel, experience));
                } 
                else if (role.equals(UserRoles.RECEPTIONIST)) {
                	addEmployee(new Receptionist(id, salary, name, surname, gender, birthdate, phone,
                			address, username, password, educationLevel, experience));
                }
                else if (role.equals(UserRoles.MAID)) {
                	
                	ArrayList<LocalDate> RoomsCleaned = new ArrayList<>();
					// index 13 is empty
                	int i = 14;
					while (!values[i].strip().isEmpty()) {
						LocalDate date = LocalDate.parse(values[i]);
						RoomsCleaned.add(date);
						i++;
					}
					i++;
					
					ArrayList<LocalDate> RoomsAssignedToClean = new ArrayList<>();
					while (!values[i].strip().isEmpty()) {
						LocalDate date = LocalDate.parse(values[i]);
						RoomsAssignedToClean.add(date);
						i++;
					}
					i++;
					
					ArrayList<Room> RoomsLeftToClean = new ArrayList<>();
					while (i < values.length) {
						Room room = RoomManager.getInstance().getRoom(values[i]);
						RoomsLeftToClean.add(room);
						i++;
					}
					addEmployee(new Maid(id, salary, RoomsCleaned, RoomsAssignedToClean, RoomsLeftToClean,
							name, surname, gender, birthdate, phone, address, username, password, educationLevel, experience));

                }
			}	
		} catch (IOException e) {
			System.err.println("Error reading from CSV file: " + e.getMessage());
		} catch (NumberFormatException e) {
			System.err.println("Error parsing number in CSV file: " + e.getMessage());
		}
	}

//	==============================================================================================
//										GETERS & SETTERS
//	==============================================================================================
	
    public void setEmployeeData(HashMap<String, Employee> employeeData) {
    	this.employeeData = employeeData;
    }

	public HashMap<String, Employee> getEmployeeData() {
		return employeeData;
	}

	public Employee getEmployee(String id) {
		return employeeData.get(id);
	}
	
	// won't be used by GUI
	public Employee getEmployeeByName(String name, String surname) {
		try {
			for (String id : getEmployeeData().keySet()) {
				if (getEmployee(id).getName().equalsIgnoreCase(name) 
						&& getEmployee(id).getSurname().equalsIgnoreCase(surname)) {
					return getEmployee(id);
				}
			}
		} catch (Exception e) {
			System.err.println("Zaposleni ne postoji.");
		}
		return null;
	}
	
//	==============================================================================================
//											C R U D
//	==============================================================================================
	
    public void addEmployee(Employee employee) {
        employeeData.put(employee.getId(), employee);
    }
    
	public void removeEmployee(String id) {
		employeeData.remove(id);
	}
	
	public void readEmployeeData() {
		System.out.println("Employee Data:");
		for (String id : employeeData.keySet()) {
			System.out.println(employeeData.get(id).toString());
		}
	}
	
//==============================================================================================
//										OTHER METHODS
//==============================================================================================

	public void assignRoomToMaid(Room room) {
		HashMap <Maid, Integer> assignedRoomsToday = new HashMap<>();
		for (String id : getEmployeeData().keySet()) {
			if (getEmployee(id) instanceof Maid) {
				Maid maid = (Maid) getEmployee(id);
				assignedRoomsToday.put(maid, maid.DateCounter(maid.getRoomsAssignedToClean()));
			}
		}

		Maid maidWithMinRooms = null;
		int minRooms = Integer.MAX_VALUE;
		for (Maid maid : assignedRoomsToday.keySet()) {
			if (assignedRoomsToday.get(maid) < minRooms) {
				minRooms = assignedRoomsToday.get(maid);
				maidWithMinRooms = maid;
			}
		}
		
		maidWithMinRooms.addRoomLeftToClean(room);
	}
	
	
	
}
