package entitiesManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import entitiesClasses.RoomFacility;
public class RoomFacilityManager implements CSVSupport {

    private static RoomFacilityManager instance = null;
    private HashMap<String, RoomFacility> roomFacilityDataMap;

//	==============================================================================================
//										CONSTRUCTOR & INSTANCE
//	==============================================================================================
	
    private RoomFacilityManager() {
        roomFacilityDataMap = new HashMap<>();
        readFromCSV();
    }

    public static RoomFacilityManager getInstance() {
        if (instance == null) {
            instance = new RoomFacilityManager();
        }
        return instance;
    }

//	==============================================================================================
//											CSV SUPPORT
//	==============================================================================================
	
    @Override
    public void writeToCSV() {
    	String filePath = "storage/roomFacilityData.csv";
        try (FileWriter writer = new FileWriter(filePath)) {
            for (String id : roomFacilityDataMap.keySet()) {
                RoomFacility roomFacility = getRoomFacility(id);
                writer.append(id)
                      .append(',')
                      .append(roomFacility.getName())
                      .append('\n');
            }
        }catch (IOException e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
        }
    }

    @Override
    public void readFromCSV(){
    	String filePath = "storage/roomFacilityData.csv";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                String id = values[0];
                String name = values[1];
                addRoomFacility(new RoomFacility(name, id));
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
    
	public void setRoomFacilityData(HashMap<String, RoomFacility> roomFacilityDataMap) {
		this.roomFacilityDataMap = roomFacilityDataMap;
	}

	public HashMap<String, RoomFacility> getRoomFacilityData() {
		return roomFacilityDataMap;
	}

	public RoomFacility getRoomFacility(String id) {
		return roomFacilityDataMap.get(id);
	}
	
	// won't be used by GUI
	public RoomFacility getRoomFacilityByName(String name) {
		try {
			for (String id : getRoomFacilityData().keySet()) {
				if (getRoomFacility(id).getName().equalsIgnoreCase(name)) {
					return getRoomFacility(id);
				}
			}
		} catch (Exception e) {
			System.err.println("Karakteristika sobe ne postoji u sistemu.");
		}
		return null;
	}	

//	==============================================================================================
//											C R U D
//	==============================================================================================
	
	public void addRoomFacility(RoomFacility roomFacility) {
    	roomFacilityDataMap.put(roomFacility.getId(), roomFacility);
    }
    
	public void deleteRoomFacility(String id) {
		roomFacilityDataMap.remove(id);
	}
		
	public void readRoomFacilityData() {
		System.out.println("Sve karakteristike soba: ");
		for (String id : getRoomFacilityData().keySet()) {
			System.out.println(getRoomFacility(id));
		}
		System.out.println();
	}
	
}
