package functionalities;

import java.time.LocalDate;
import java.util.ArrayList;

import entitiesClasses.Room;
import entitiesEnums.ReservationStatus;
import entitiesEnums.RoomType;
import storage.ReservationData;

public class Available {
	
	public static ArrayList<RoomType> betweenDates(LocalDate firstDate, LocalDate lastDate, ReservationData reservationData){
			
		int numOfOnePersonRoomsOneSingleBed = Room.getRoomCountByType(RoomType.ONE_PERSON_ROOM_ONE_SINGLE_BED);
		int numOfTwoPersonRoomsOneDoubleBed = Room.getRoomCountByType(RoomType.TWO_PERSON_ROOM_ONE_DOUBLE_BED);
		int numOfTwoPersonRoomsTwoSingleBeds = Room.getRoomCountByType(RoomType.TWO_PERSON_ROOM_TWO_SINGLE_BEDS);
		int numOfThreePersonRoomsThreeSingleBeds = Room.getRoomCountByType(RoomType.THREE_PERSON_ROOM_THREE_SINGLE_BEDS);
		int numOfThreePersonRoomsOneDoubleBedOneSingleBed = Room.getRoomCountByType(RoomType.THREE_PERSON_ROOM_ONE_DOUBLE_BED_ONE_SINGLE_BED);

		for (String id : reservationData.getReservationMap().keySet()) {
			// proverava da li je ova odobrena rezervacija unutar trazenog perioda
			
			if (!(reservationData.getReservation(id).getStartDate().isAfter(lastDate) ||
	                reservationData.getReservation(id).getEndDate().isBefore(firstDate)) && 
					reservationData.getReservation(id).getStatus().equals(ReservationStatus.APPROVED)) {
									
				RoomType tipSobe  = reservationData.getReservation(id).getRoomType();
				// ako jeste, smanjuje broj soba tog tipa
				switch (tipSobe) {
				case ONE_PERSON_ROOM_ONE_SINGLE_BED:
					numOfOnePersonRoomsOneSingleBed--;
					break;
				case TWO_PERSON_ROOM_ONE_DOUBLE_BED:
					numOfTwoPersonRoomsOneDoubleBed--;
					break;
				case TWO_PERSON_ROOM_TWO_SINGLE_BEDS:
					numOfTwoPersonRoomsTwoSingleBeds--;
					break;
				case THREE_PERSON_ROOM_THREE_SINGLE_BEDS:
					numOfThreePersonRoomsThreeSingleBeds--;
					break;
				case THREE_PERSON_ROOM_ONE_DOUBLE_BED_ONE_SINGLE_BED:
					numOfThreePersonRoomsOneDoubleBedOneSingleBed--;
					break;
				}
			}
		}
		// vraca tipove soba koji su slobodni
		ArrayList<RoomType> slobodniTIpoviSoba = new ArrayList<>();
		if (numOfOnePersonRoomsOneSingleBed > 0) {
			slobodniTIpoviSoba.add(RoomType.ONE_PERSON_ROOM_ONE_SINGLE_BED);
		}
		if (numOfTwoPersonRoomsOneDoubleBed > 0) {
			slobodniTIpoviSoba.add(RoomType.TWO_PERSON_ROOM_ONE_DOUBLE_BED);
		}
		if (numOfTwoPersonRoomsTwoSingleBeds > 0) {
			slobodniTIpoviSoba.add(RoomType.TWO_PERSON_ROOM_TWO_SINGLE_BEDS);
		}
		if (numOfThreePersonRoomsThreeSingleBeds > 0) {
			slobodniTIpoviSoba.add(RoomType.THREE_PERSON_ROOM_THREE_SINGLE_BEDS);
		}
		if (numOfThreePersonRoomsOneDoubleBedOneSingleBed > 0) {
			slobodniTIpoviSoba.add(RoomType.THREE_PERSON_ROOM_ONE_DOUBLE_BED_ONE_SINGLE_BED);
		}
		return slobodniTIpoviSoba;
	}

}
