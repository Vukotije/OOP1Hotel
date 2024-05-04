package storage;

import java.util.HashMap;

import entitiesClasses.Reservation;

public class ReservationData {
	
    private HashMap<String, Reservation> reservationMap;
    
    public ReservationData() {
    	reservationMap = new HashMap<>();
    }

    public void addReservation(Reservation reservation) {
    	reservationMap.put(reservation.getId(), reservation);
    }
    
	public Reservation getReservation(String id) {
		return reservationMap.get(id);
	}
	
	public HashMap<String, Reservation> getReservationMap() {
		return reservationMap;
	}

	public void removePricelist(String id) {
		reservationMap.remove(id);
	}
	
}
