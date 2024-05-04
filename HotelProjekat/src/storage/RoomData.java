package storage;

import java.util.HashMap;

import entitiesClasses.Room;

public class RoomData {
	
    private HashMap<String, Room> roomsMap;
    
    public RoomData() {
    	roomsMap = new HashMap<>();
    }

    public void addRoom(Room room) {
    	roomsMap.put(room.getId(), room);
    }
    
	public Room getRoom(String id) {
		return roomsMap.get(id);
	}
	
	public HashMap<String, Room> getRoomsMap() {
		return roomsMap;
	}

	public void removeRoom(String id) {
		roomsMap.remove(id);
	}
}
