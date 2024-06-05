package managerClasses;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;

import entitiesClasses.AdditionalService;
import entitiesClasses.Guest;
import entitiesClasses.Reservation;
import entitiesClasses.Room;
import entitiesEnums.ReservationStatus;
import entitiesEnums.RoomFacility;
import entitiesEnums.RoomStatus;
import entitiesEnums.RoomType;

public class ReservationManager implements CSVSupport{
	
	private static ReservationManager instance = null;
    private HashMap<String, Reservation> reservationData;

//	==============================================================================================
//										CONSTRUCTOR & INSTANCE
//	==============================================================================================
	
    private ReservationManager() {
    	reservationData = new HashMap<>();
    	readFromCSV();
    	denyLateReservations();
    }
    
	public static ReservationManager getInstance() {
		if (instance == null) {
			instance = new ReservationManager();
		}
		return instance;
	}	

//	==============================================================================================
//											CSV SUPPORT
//	==============================================================================================
	
	@Override
	public void writeToCSV() {
		String filePath = "storage/reservationData.csv";
		try (FileWriter writer = new FileWriter(filePath)) {
			for (String id : reservationData.keySet()) {
				Reservation reservation = getReservation(id);
	            StringBuilder sb = new StringBuilder();
	            String roomId;
                if (reservation.getRoom() == null) {
                	roomId = "";
				} else {
					roomId = reservation.getRoom().getId();
				}
				sb.append(id)
					.append(',')
					.append(reservation.getGuest().getId())
					.append(',')
					.append(reservation.getStartDate().toString())
					.append(',')
					.append(reservation.getEndDate().toString())
					.append(',')
					.append(reservation.getCreationDate().toString())
					.append(',')
					.append(reservation.getStatus().toString())
					.append(',')
					.append(reservation.getRoomType().toString())
					.append(',')
					.append(roomId)
					.append(',')
					.append(Double.toString(reservation.getPrice()))
					.append(',')
					.append(' ')
					.append(',');
				
				for (RoomFacility roomFacility : reservation.getRoomFacilitiesWanted().keySet()){
                    sb.append(roomFacility.toString())
	                    .append(',')
	                    .append(Boolean.toString(reservation.getRoomFacilitiesWanted().get(roomFacility)))
	                    .append(',');
                }
				
				sb.append(' ')
					.append(',');
				
				for (AdditionalService additionalService : reservation.getAdditionalServicesWanted()) {
					sb.append(additionalService.getId())
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
		String filePath = "storage/reservationData.csv";
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] values = line.split(",");
				String id = values[0];
                Guest guest = GuestManager.getInstance().getGuest(values[1]);
                LocalDate startDate = LocalDate.parse(values[2]);
                LocalDate endDate = LocalDate.parse(values[3]);
                LocalDate creationDate = LocalDate.parse(values[4]);
                ReservationStatus status = ReservationStatus.fromString(values[5]);
				RoomType roomType = RoomType.fromString(values[6]);
				Room room;
				if (values[7].strip().isEmpty()) {
					room = null;
				} else {
	                room = RoomManager.getInstance().getRoom(values[7]);
				}
                double price = Double.parseDouble(values[8]);
				
				EnumMap<RoomFacility, Boolean> roomFacilitiesMap = new EnumMap<>(RoomFacility.class);
				
				// index 9 is empty
				int i = 10;
				while (!values[i].strip().isEmpty()) {
					RoomFacility roomFacility = RoomFacility.fromString(values[i]);
					Boolean wantedness = Boolean.parseBoolean(values[i + 1]);
					roomFacilitiesMap.put(roomFacility, wantedness);
					i+=2;
				}
				i++;
				
				ArrayList<AdditionalService> additionalServices = new ArrayList<>();

				while (i < values.length) {
					AdditionalService additionalService = AdditionalServiceManager.getInstance().getAdditionalService(values[i]);
					additionalServices.add(additionalService);
					i++;
				}
				
				addReservation(new Reservation(id, creationDate, room, price, status,
						guest, startDate, endDate, roomType, roomFacilitiesMap, additionalServices));
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
	    
	public void setReservationData(HashMap<String, Reservation> reservationData) {
		this.reservationData = reservationData;
	}
	
	public HashMap<String, Reservation> getReservationData() {
		return reservationData;
	}
    
	public Reservation getReservation(String id) {
		return reservationData.get(id);
	}

//	==============================================================================================
//											C R U D
//	==============================================================================================
	
    public void addReservation(Reservation reservation) {
    	reservationData.put(reservation.getId(), reservation);
    }

	public void removeReservation(String id) {
		reservationData.remove(id);
	}
	
	public void readReservation() {
		System.out.println("Sve rezervacije: ");
		for (String id : reservationData.keySet()) {
			System.out.println(reservationData.get(id));
		}
		System.out.println();
	}
	
	// won't be used by GUI
	public void readGuestsReservation(Guest guest) {
		System.out.println("Rezervacije gosta " + guest.getName() + " " + guest.getSurname() + ": ");
		for (String id : reservationData.keySet()) {
			if (reservationData.get(id).getGuest().equals(guest)) {
				System.out.println(reservationData.get(id));
			}
		}
		System.out.println();
	}

//	==============================================================================================
//										OTHER METHODS
//	==============================================================================================
	
	
	public void makeReservation(LocalDate startDate, LocalDate endDate, Guest guest) {
		
		AdditionalServiceManager additionalServiceData = AdditionalServiceManager.getInstance();
		
		// INPUT FIELDS
		//LocalDate startDate = LocalDate.of(2024, 8, 13);
		//LocalDate endDate = LocalDate.of(2024, 8, 23);

		// RADIO BUTTON IMPLEMETATION
		EnumMap<RoomFacility, Boolean> roomFacilitiesWanted = new EnumMap<>(RoomFacility.class);
		roomFacilitiesWanted.put(RoomFacility.AC_UNIT, true);
		roomFacilitiesWanted.put(RoomFacility.BALCONY, false);
		roomFacilitiesWanted.put(RoomFacility.SMOKING_ROOM, true);
		roomFacilitiesWanted.put(RoomFacility.TV, false);
		
		ArrayList<RoomType> availableRoomTypes = getAvailableRoomTypes(startDate,
													endDate, roomFacilitiesWanted);
		if (availableRoomTypes.size() == 0) {
			System.err.println("Nema slobodnih soba za trazeni period.");
			System.err.println("Molimo vas da izaberete drugi period.");
			System.out.println();			
			return;
		} 
		System.out.println("Tipovi soba koje su dostupne izmedju "
				+ startDate.toString() +" i " + endDate.toString() + " : ");
		for (RoomType roomType : availableRoomTypes) {
			System.out.println(roomType);
		}
		System.out.println();
		// CHOSE ROOM Type
		RoomType roomType = availableRoomTypes.get(0);
		//
		// RADIO BUTTON IMPLEMETATION
		ArrayList<AdditionalService> additionalServiceWanted = new ArrayList<>();
		additionalServiceWanted.add(additionalServiceData.getAdditionalServiceByName("Breakfast"));
		additionalServiceWanted.add(additionalServiceData.getAdditionalServiceByName("Lunch"));	

		this.addReservation(new Reservation(ReservationStatus.PENDING, guest,
				startDate, endDate, roomType, roomFacilitiesWanted,
				additionalServiceWanted));					
    }
	
	public void approveReservation(String id) {
		Reservation reservation = getReservation(id);
		ArrayList<RoomType> availableRoomTypes = getAvailableRoomTypes(reservation.getStartDate(),
				reservation.getEndDate(), reservation.getRoomFacilitiesWanted());
		
		if (availableRoomTypes.contains(reservation.getRoomType())) {
			System.out.println("Rezervacija je odobrena.");
			reservation.setStatus(ReservationStatus.APPROVED);
		} else {
			System.err.println("Rezervacija ne moze biti odobrena, sve je zauzeto.");
			reservation.setStatus(ReservationStatus.DENIED);
		}
	}
	
	public void denyReservation(String id) {
		getReservation(id).setStatus(ReservationStatus.DENIED);
	}
	
	public void cancelReservation(String id) {
		getReservation(id).setStatus(ReservationStatus.CANCELLED);
	}
	
	public void denyLateReservations() {
		LocalDate currentDate = LocalDate.now();
		for (String id : reservationData.keySet()) {
			Reservation reservation = reservationData.get(id);
			if (reservation.getStartDate().isEqual(currentDate.plusDays(1))
				&& reservation.getStatus() == ReservationStatus.PENDING) {
					reservation.setStatus(ReservationStatus.DENIED);
			}
		}
	}
	
	public double getNetPriceForGuest(Guest guest) {
		double netPrice = 0;
		for (String id : reservationData.keySet()) {
			if (reservationData.get(id).getGuest().equals(guest)) {
				netPrice += reservationData.get(id).getPrice();
			}
		}
		return netPrice;
	}
	
	public void checkInReservation(String id) {
		RoomManager roomData = RoomManager.getInstance();
		AdditionalServiceManager additionalServiceData = AdditionalServiceManager.getInstance();
		Reservation reservation = getReservation(id);
		
		for (String roomId : roomData.getRoomData().keySet()) {
			Room room = roomData.getRoom(roomId);
			if (room.getRoomType().equals(reservation.getRoomType())
				&& room.getRoomFacilitiesAvailablity().equals(reservation.getRoomFacilitiesWanted())
				&& room.getRoomStatus().equals(RoomStatus.AVAILABLE)) {
					room.setRoomStatus(RoomStatus.OCCUPIED);
					reservation.setRoom(room);
									
					System.out.println("Ukoliko zelite dodajte jos neke usluge: ");
					ArrayList<AdditionalService> newAdditionalServices = new ArrayList<>();
					
					for (String additionalServiceId: additionalServiceData.getAdditionalServiceData().keySet()){
						AdditionalService additionalService = additionalServiceData.getAdditionalService(additionalServiceId);
						if (!reservation.getAdditionalServicesWanted().contains(additionalService)) {
							System.out.println(additionalService);
							newAdditionalServices.add(additionalService);
						}	
					}
					ArrayList<AdditionalService> newWantedAdditionalServices = new ArrayList<>();
					newWantedAdditionalServices.add(additionalServiceData.getAdditionalServiceByName("Bazen"));
					reservation.addToPrice(newWantedAdditionalServices);
					System.out.println("Ukupna cena rezervacije je: " + reservation.getPrice());
					
			}
		}
	}
	
	protected ArrayList<RoomType> getAvailableRoomTypes(LocalDate startDate, LocalDate endDate,
			EnumMap<RoomFacility, Boolean> roomFacilitiesWanted) {
	
		RoomManager roomData = RoomManager.getInstance();
		
		EnumMap<RoomType, Integer> availableRoomTypeMap = new EnumMap<>(RoomType.class);
		for (RoomType type : RoomType.values()) {
		    availableRoomTypeMap.put(type, 0);
		}
				
		// increase the number of each room type for each room type and criteria that fit the wanted
		if (!roomData.getRoomData().isEmpty()) {
			for (String id : roomData.getRoomData().keySet()) {
				Room room = roomData.getRoom(id);
				if (room.getRoomFacilitiesAvailablity().equals(roomFacilitiesWanted)){
					RoomType roomType = room.getRoomType();
					availableRoomTypeMap.put(roomType, availableRoomTypeMap.get(roomType) + 1);
				}
			}
		}
		
		// decrese the number of each room type for each room type and criteria that fit the wanted
		if (!reservationData.isEmpty()) {
			for (String id : reservationData.keySet()) {
				Reservation reservation = getReservation(id);
				if (reservation.getStartDate().isBefore(endDate)
					&& reservation.getEndDate().isAfter(startDate)
					&& reservation.getStatus() == ReservationStatus.APPROVED
					&& reservation.getRoomFacilitiesWanted().equals(roomFacilitiesWanted)){
						RoomType roomType = reservation.getRoomType();
						availableRoomTypeMap.put(roomType, availableRoomTypeMap.get(roomType) - 1);
				}
			}
		}
		
		ArrayList<RoomType> availableRoomTypes = new ArrayList<>();
		for (RoomType roomType : availableRoomTypeMap.keySet()) {
			if (availableRoomTypeMap.get(roomType) > 0) {
				availableRoomTypes.add(roomType);
			}
		}
		return availableRoomTypes;
	}
	
	
}
