package entitiesManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import entitiesClasses.AdditionalService;
import entitiesClasses.Guest;
import entitiesClasses.Reservation;
import entitiesClasses.Room;
import entitiesClasses.RoomFacility;
import entitiesEnums.ReservationStatus;
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
				
				if (!reservation.getRoomFacilitiesWanted().isEmpty()) {
					for (RoomFacility roomFacility : reservation.getRoomFacilitiesWanted()){
	                    sb.append(roomFacility.getId())
		                    .append(',');
					}
				}
				
				sb.append(' ')
					.append(',');
				
				if (!reservation.getAdditionalServicesWanted().isEmpty()) {
					for (AdditionalService additionalService : reservation.getAdditionalServicesWanted()) {
						sb.append(additionalService.getId())
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
				
				Set<RoomFacility> roomFacilities = new HashSet<RoomFacility>();
				
				// index 9 is empty
				int i = 10;
				while (!values[i].strip().isEmpty()) {
					RoomFacility roomFacility = RoomFacilityManager.getInstance().getRoomFacility(values[i]);
					roomFacilities.add(roomFacility);
					i++;
				}
				i++;
				
				ArrayList<AdditionalService> additionalServices = new ArrayList<>();

				while (i < values.length) {
					AdditionalService additionalService = AdditionalServiceManager.getInstance().getAdditionalService(values[i]);
					additionalServices.add(additionalService);
					i++;
				}
				
				addReservation(new Reservation(id, creationDate, room, price, status,
						guest, startDate, endDate, roomType, roomFacilities, additionalServices));
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
	
	public void readAllReservations() {
		System.out.println("Sve rezervacije: ");
		for (String id : reservationData.keySet()) {
			System.out.println(reservationData.get(id).toString());
		}
		System.out.println();
	}

//	==============================================================================================
//										OTHER METHODS
//	==============================================================================================
	
	public void makeReservation(LocalDate startDate, LocalDate endDate, Guest guest,
			ArrayList<AdditionalService> additionalServiceWanted, 
			Set<RoomFacility> roomFacilitiesWanted, RoomType roomType) {
		
		this.addReservation(new Reservation(ReservationStatus.PENDING, guest,
				startDate, endDate, roomType, roomFacilitiesWanted,
				additionalServiceWanted));					
    }
	
	public Boolean approveReservation(String id) {
		Reservation reservation = getReservation(id);
		ArrayList<RoomType> availableRoomTypes = getAvailableRoomTypes(reservation.getStartDate(),
				reservation.getEndDate(), reservation.getRoomFacilitiesWanted());
		
		if (availableRoomTypes.contains(reservation.getRoomType())) {
			System.out.println("Rezervacija je odobrena.");
			reservation.setStatus(ReservationStatus.APPROVED);
			return true;
		} else {
			System.err.println("Rezervacija ne moze biti odobrena, sve je zauzeto.");
			reservation.setStatus(ReservationStatus.DENIED);
			return false;
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
			if (reservation.getStartDate().isBefore(currentDate)
				&& reservation.getStatus() == ReservationStatus.PENDING) {
					reservation.setStatus(ReservationStatus.DENIED);
			}
		}
	}
	
	public HashMap<String, Reservation> getUsersResrevations(Guest guest) {
		HashMap<String, Reservation> guestReservations = new HashMap<>();
		for (String id : reservationData.keySet()) {
			if (reservationData.get(id).getGuest().equals(guest)) {
                guestReservations.put(id, reservationData.get(id));
			}
		}
		return guestReservations;
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
	
	public Room checkInReservation(String id, ArrayList<AdditionalService> newAdditionalServices) {
		RoomManager roomData = RoomManager.getInstance();
		Reservation reservation = getReservation(id);
		
		for (String roomId : roomData.getRoomData().keySet()) {
			Room room = roomData.getRoom(roomId);
			if (room.getRoomType().equals(reservation.getRoomType())
				&& room.getRoomFacilitiesAvailable().containsAll(reservation.getRoomFacilitiesWanted())
				&& room.getRoomStatus().equals(RoomStatus.AVAILABLE)) {
					room.setRoomStatus(RoomStatus.OCCUPIED);
					room.setReservationId(id);
					reservation.setRoom(room);																
					reservation.addToPrice(newAdditionalServices);					
					ArrayList<AdditionalService> newWantedAdditionalServices = new ArrayList<>();
					newWantedAdditionalServices.addAll(newAdditionalServices);
					newWantedAdditionalServices.addAll(reservation.getAdditionalServicesWanted());
					reservation.setAdditionalServicesWanted(newWantedAdditionalServices);
					return room;
					
					
			}
		}
		return null;
	}
	
	public ArrayList<RoomType> getAvailableRoomTypes(LocalDate startDate, LocalDate endDate,
			Set<RoomFacility> roomFacilitiesWanted) {
	
		RoomManager roomData = RoomManager.getInstance();
		
		EnumMap<RoomType, Integer> availableRoomTypeMap = new EnumMap<>(RoomType.class);
		for (RoomType type : RoomType.values()) {
		    availableRoomTypeMap.put(type, 0);
		}
				
		// increase the number of each room type for each room type and criteria that fit the wanted
		if (!roomData.getRoomData().isEmpty()) {
			for (String id : roomData.getRoomData().keySet()) {
				Room room = roomData.getRoom(id);
				if (room.getRoomFacilitiesAvailable().containsAll(roomFacilitiesWanted)){
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
					&& reservation.getRoomFacilitiesWanted().containsAll(roomFacilitiesWanted)){
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
