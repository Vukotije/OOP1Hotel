package entitiesManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;

import entitiesClasses.Guest;
import entitiesEnums.Gender;

public class GuestManager implements CSVSupport {
	
	private static GuestManager instance = null;
    private HashMap<String, Guest> guestData;

//	==============================================================================================
//										CONSTRUCTOR & INSTANCE
//	==============================================================================================
	
    private GuestManager() {
    	guestData = new HashMap<>();
    	readFromCSV();
    }
    
    public static GuestManager getInstance() {
    	if (instance == null) {
    		instance = new GuestManager();
    	}
        return instance;
    }

//	==============================================================================================
//										CSV SUPPORT
//	==============================================================================================

    @Override
    public void writeToCSV() {
    	String filePath = "storage/guestData.csv";
        try (FileWriter writer = new FileWriter(filePath)) {
            for (String id : guestData.keySet()) {
                Guest guest = getGuest(id);
                writer.append(id)
	                .append(',')
	                .append(guest.getName())
	                .append(',')
	                .append(guest.getSurname())
	                .append(',')
	                .append(guest.getGender().toString())
	                .append(',')
	                .append(guest.getBirthdate().toString())
	                .append(',')
	                .append(guest.getPhone())
	                .append(',')
	                .append(guest.getAddress())
	                .append(',')
	                .append(guest.getUsername())
	                .append(',')
	                .append(guest.getPassword())
	                .append('\n');
            }
        }catch (IOException e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
        }
    }

    @Override
    public void readFromCSV(){
    	String filePath = "storage/guestData.csv";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                String id = values[0];
                String name = values[1];
                String surname = values[2];
                Gender gender = Gender.fromString(values[3]);
                LocalDate birthdate = LocalDate.parse(values[4]);
                String phone = values[5];
                String address = values[6];
                String username = values[7];
                String password = values[8];
                addGuest(new Guest(id, name, surname, gender, birthdate, phone, address, username, password));
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
	
	public void setGuestData(HashMap<String, Guest> guestData) {
		this.guestData = guestData;
	}

	public HashMap<String, Guest> getGuestData() {
		return guestData;
	}

	public Guest getGuest(String id) {
		return guestData.get(id);
	}
	
	// won't be used by GUI
	public Guest getGuestByName(String name, String surname) {
		try {
			for (String id : getGuestData().keySet()) {
				if (getGuest(id).getName().equalsIgnoreCase(name) &&
						getGuest(id).getSurname().equalsIgnoreCase(surname)) {
					return getGuest(id);
				}
			}
		} catch (NullPointerException e) {
			System.err.println("Gost ne postoji.");
		}
		return null;
	}

//	==============================================================================================
//											C R U D
//	==============================================================================================
	
    public void addGuest(Guest guest) {
        guestData.put(guest.getId(), guest);
    }
    
	public void removeGuest(String id) {
		guestData.remove(id);
	}
		
	public void readGuest() {
		System.out.println("Svi gosti: ");
		for (String id : getGuestData().keySet()) {
			System.out.println(getGuest(id));
		}
		System.out.println();
	}
	
}