package storage;

import java.util.HashMap;

import entitiesClasses.Guest;

public class GuestData {
	
    private HashMap<String, Guest> guestsMap;
    
    public GuestData() {
    	guestsMap = new HashMap<>();
    }

    public void addGuest(Guest guest) {
        guestsMap.put(guest.getId(), guest);
    }
    
	public Guest getGuest(String id) {
		return guestsMap.get(id);
	}
	
	public HashMap<String, Guest> getGuestsMap() {
		return guestsMap;
	}

	public void removeGuest(String id) {
		guestsMap.remove(id);
	}
}