package entitiesManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import entitiesClasses.Room;
import entitiesClasses.RoomFacility;
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
	            String reservationId;
                if (room.getReservationId() == null) {
                	reservationId = "";
				} else {
					reservationId = room.getReservationId();
				}
				sb.append(id)
					.append(',')
					.append(Integer.toString(room.getRoomNumber()))
					.append(',')
					.append(room.getRoomType().toString())
					.append(',')
					.append(room.getRoomStatus().toString())
					.append(',')
					.append(reservationId)
					.append(',')
					.append(' ')
					.append(',');
				
				if (!room.getRoomFacilitiesAvailable().isEmpty()) {
					for (RoomFacility roomFacility : room.getRoomFacilitiesAvailable()) {
	                    sb.append(roomFacility.getId())
		                    .append(',');
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
		String filePath = "storage/roomData.csv";
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] values = line.split(",");
				String id = values[0];
				int roomNumber = Integer.parseInt(values[1]);
				RoomType roomType = RoomType.fromString(values[2]);
				RoomStatus roomStatus = RoomStatus.fromString(values[3]);
				String reservationId;
				if (values[4].strip().isEmpty()) {
					reservationId = null;
				} else {
					reservationId = values[4];
				}
				
				Set<RoomFacility> roomFacilities = new HashSet<RoomFacility>();
				
				// index 5 is empty
				int i = 6;
				while (i < values.length) {
					RoomFacility roomFacility = RoomFacilityManager.getInstance().getRoomFacility(values[i]);
					roomFacilities.add(roomFacility);
					i++;
				}
				addRoom(new Room(id, roomNumber, roomType, roomStatus, reservationId, roomFacilities));
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
	
	public void readRoomData() {
		System.out.println("Room Data:");
		for (String id : roomData.keySet()) {
			System.out.println(roomData.get(id).toString());
		}
	}
//	==============================================================================================
//											OTHER METHODS
//	==============================================================================================
	
	public void checkOut(String id) {
		Room room = getRoom(id);
        EmployeeManager.getInstance().assignRoomToMaid(room);
        room.setRoomStatus(RoomStatus.CLEANING);
        room.setReservationId(null);
	}	
}
