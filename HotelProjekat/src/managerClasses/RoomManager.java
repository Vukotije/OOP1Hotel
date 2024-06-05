package managerClasses;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.EnumMap;
import java.util.HashMap;

import entitiesClasses.Room;
import entitiesEnums.RoomFacility;
import entitiesEnums.RoomStatus;
import entitiesEnums.RoomType;

public class RoomManager implements CSVSupport{
	
	private static RoomManager instance = null;
    private HashMap<String, Room> roomData;

//	==============================================================================================
//										CONSTRUCTOR & INSTANCE
//	==============================================================================================
	
    private RoomManager() {
    	roomData = new HashMap<>();
    	readFromCSV();
    }
    
	public static RoomManager getInstance() {
		if (instance == null) {
			instance = new RoomManager();
		}
		return instance;
	}

//	==============================================================================================
//											CSV SUPPORT
//	==============================================================================================
	
	@Override
	public void writeToCSV() {
		String filePath = "storage/roomData.csv";
		try (FileWriter writer = new FileWriter(filePath)) {
			for (String id : roomData.keySet()) {
				Room room = getRoom(id);
	            StringBuilder sb = new StringBuilder();
				sb.append(id)
					.append(',')
					.append(Integer.toString(room.getRoomNumber()))
					.append(',')
					.append(room.getRoomType().toString())
					.append(',')
					.append(room.getRoomStatus().toString())
					.append(',')
					.append(' ')
					.append(',');
				
				for (RoomFacility roomFacility : room.getRoomFacilitiesAvailablity().keySet()){
                    sb.append(roomFacility.toString())
	                    .append(',')
	                    .append(Boolean.toString(room.getRoomFacilitiesAvailablity().get(roomFacility)))
	                    .append(',');
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
		String filePath = "storage/roomData.csv";
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] values = line.split(",");
				String id = values[0];
				int roomNumber = Integer.parseInt(values[1]);
				RoomType roomType = RoomType.fromString(values[2]);
				RoomStatus roomStatus = RoomStatus.fromString(values[3]);
				
				EnumMap<RoomFacility, Boolean> roomFacilitiesMap = new EnumMap<>(RoomFacility.class);
				
				// index 4 is empty
				int i = 5;
				while (i < values.length) {
					RoomFacility roomFacility = RoomFacility.fromString(values[i]);
					Boolean availability = Boolean.parseBoolean(values[i + 1]);
					roomFacilitiesMap.put(roomFacility, availability);
					i+=2;
				}
				addRoom(new Room(id, roomNumber, roomType, roomStatus, roomFacilitiesMap));
			}
		} catch (IOException e) {
			System.err.println("Error reading from CSV file: " + e.getMessage());
		} catch (NumberFormatException e) {
			System.err.println("Error parsing number in CSV file: " + e.getMessage());
		}
	}
	
//	==============================================================================================
//										GETERS & SETERS
//	==============================================================================================
    
    public void setRoomData(HashMap<String, Room> roomData) {
    	this.roomData = roomData;
    }

	public HashMap<String, Room> getRoomData() {
		return roomData;
	}
    
	public Room getRoom(String id) {
		return roomData.get(id);
	}
	
	// wont be used by GUI
	public Room getRoomByNumber(int roomNumber) {
		try {
			for (String id : getRoomData().keySet()) {
				if (getRoom(id).getRoomNumber() == roomNumber) {
					return getRoom(id);
				}
			}
		} catch (Exception e) {
			System.err.println("Soba ne postoji.");
		}
		return null;
	}

//	==============================================================================================
//											C R U D
//	==============================================================================================
	
    public void addRoom(Room room) {
    	roomData.put(room.getId(), room);
    }
    
	public void removeRoom(String id) {
		roomData.remove(id);
	}
	
	public void readRoom() {
		System.out.println("Sve sobe: ");
		for (String id : getRoomData().keySet()) {
			System.out.println(getRoom(id));
		}
		System.out.println();
	}
	

//	==============================================================================================
//											OTHER METHODS
//	==============================================================================================
	
	public void checkOut(String id) {
		Room room = getRoom(id);
        EmployeeManager.getInstance().assignRoomToMaid(room);
        room.setRoomStatus(RoomStatus.CLEANING);
	}
	
	public void cleanRoom(String id) {
		getRoom(id).setRoomStatus(RoomStatus.AVAILABLE);
	}
}
