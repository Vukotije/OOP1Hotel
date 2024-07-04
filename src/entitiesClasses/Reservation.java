package entitiesClasses;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Set;

import entitiesEnums.ReservationStatus;
import entitiesEnums.RoomType;
import entitiesManager.PricelistManager;

public class Reservation extends BaseEntity{
	
	private Guest guest;
	private LocalDate startDate;
	private LocalDate endDate;
	private LocalDate creationDate;
	private ReservationStatus status;
	private RoomType roomType;
	private Room room;
	private double price;
	private Set<RoomFacility> roomFacilitiesWanted;
	private ArrayList<AdditionalService> additionalServiceWanted;

//	==============================================================================================
//										CONSTRUCTORS
//	==============================================================================================
	
	public Reservation(ReservationStatus reservationStatus, Guest guest, LocalDate startDate,
			LocalDate endDate, RoomType roomType,
			Set<RoomFacility> roomFacilitiesWanted,
			ArrayList<AdditionalService> additionalServiceWanted) {
	
	  super();
	  setStatus(reservationStatus);
	  setGuest(guest);
	  setStartDate(startDate);
	  setEndDate(endDate);
	  setRoomType(roomType);	
	  setRoomFacilitiesWanted(roomFacilitiesWanted);
	  setAdditionalServicesWanted(additionalServiceWanted);
	  setPrice(generatePrice());
	  creationDate = LocalDate.now();
	  this.room = null;
	}
	
	public Reservation(String id, LocalDate creationDate, Room room, double price, ReservationStatus reservationStatus, Guest guest, LocalDate startDate, LocalDate endDate, RoomType roomType,
			Set<RoomFacility> roomFacilitiesWanted,
			ArrayList<AdditionalService> additionalServiceWanted) {
	
	  super();
	  setStatus(reservationStatus);
	  setGuest(guest);
	  setStartDate(startDate);
	  setEndDate(endDate);
	  setRoomType(roomType);	
	  setRoomFacilitiesWanted(roomFacilitiesWanted);
	  setAdditionalServicesWanted(additionalServiceWanted);
	  setPrice(price);
	  setCreationDate(creationDate);
	  setRoom(room);
	  setId(id);
	  
	}

//	==============================================================================================
//										GETTERS & SETTERS
//	==============================================================================================
	
	
	public LocalDate getCreationDate() {
		return creationDate;
	}
	
	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	public Guest getGuest() {
		return guest;
	}

	public void setGuest(Guest guest) {
		this.guest = guest;
	}

	public ReservationStatus getStatus() {
		return status;
	}

	public void setStatus(ReservationStatus status) {
		this.status = status;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public RoomType getRoomType() {
		return roomType;
	}

	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}
	
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	public ArrayList<AdditionalService> getAdditionalServicesWanted() {
		return additionalServiceWanted;
	}

	public void setAdditionalServicesWanted(ArrayList<AdditionalService> additionalServiceWanted) {
		this.additionalServiceWanted = additionalServiceWanted;
	}
	
	public Set<RoomFacility> getRoomFacilitiesWanted() {
		return roomFacilitiesWanted;
	}
	
	public void setRoomFacilitiesWanted(Set<RoomFacility> roomFacilitiesWanted) {
		this.roomFacilitiesWanted = roomFacilitiesWanted;
	}
	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

//	==============================================================================================
//										TO STRING
//	==============================================================================================
	
	@Override
	public String toString() {
		return "Reservation [guest=" + guest + ", startDate=" + startDate + ", endDate=" + endDate + ", creationDate="
				+ creationDate + ", status=" + status + ", roomType=" + roomType + ", room=" + room + ", price=" + price
				+ ", roomFacilitiesWanted=" + roomFacilitiesWanted + ", additionalServiceWanted="
				+ additionalServiceWanted + "]";
	}

//	==============================================================================================
//										OTHER METHODS
//	==============================================================================================
	
	public void addAdditionalServiceWanted(AdditionalService additionalService) {
		this.additionalServiceWanted.add(additionalService);
	}
	
	public void removeAdditionalServiceWanted(AdditionalService additionalService) {
		this.additionalServiceWanted.remove(additionalService);
	}	

	public double generatePrice() {
		PricelistManager pricelistData = PricelistManager.getInstance();		
		double price = 0;
		if (!pricelistData.getPricelistData().isEmpty()) {
			for (LocalDate movingDate = getStartDate(); !movingDate.isAfter(getEndDate()); movingDate = movingDate.plusDays(1)) {
				for (String id : pricelistData.getPricelistData().keySet()) {
					Pricelist pricelist = pricelistData.getPricelist(id);
                    if (pricelist.getStartDate().isBefore(movingDate)
                    && pricelist.getEndDate().isAfter(movingDate)) {
                        price += pricelist.getRoomTypePrice(getRoomType());
                        for (AdditionalService service : getAdditionalServicesWanted()) {
                            price += pricelist.getAdditionalServicePrice(service);
                        }
                    }
				}	
		    }
		} else {
			System.err.println("Hotel nije spreman za rezervaciju, ne postoji cenovnik!");
			System.exit(1);			
		}
		return price;
	}

	public static double generatePrice(ArrayList<AdditionalService> additionalServices,
			RoomType roomType, LocalDate startDate, LocalDate endDate) {
		PricelistManager pricelistData = PricelistManager.getInstance();		
		double price = 0;
		if (!pricelistData.getPricelistData().isEmpty()) {
			for (LocalDate movingDate = startDate; !movingDate.isAfter(endDate); movingDate = movingDate.plusDays(1)) {
				for (String id : pricelistData.getPricelistData().keySet()) {
					Pricelist pricelist = pricelistData.getPricelist(id);
                    if (pricelist.getStartDate().isBefore(movingDate)
                    && pricelist.getEndDate().isAfter(movingDate)) {
                        price += pricelist.getRoomTypePrice(roomType);
                        for (AdditionalService service : additionalServices) {
                            price += pricelist.getAdditionalServicePrice(service);
                        }
                    }
				}	
		    }
		} else {
			System.err.println("Hotel nije spreman za rezervaciju, ne postoji cenovnik!");
			System.exit(1);			
		}
		return price;
	}
	
	
	
	public void addToPrice(ArrayList<AdditionalService> additionalServices) {
		PricelistManager pricelistData = PricelistManager.getInstance();
		double aditionalPrice = 0;
		if (!pricelistData.getPricelistData().isEmpty()) {
			for (LocalDate movingDate = getStartDate(); !movingDate.isAfter(getEndDate()); movingDate = movingDate.plusDays(1)) {
				for (String id : pricelistData.getPricelistData().keySet()) {
					Pricelist pricelist = pricelistData.getPricelist(id);
                    if (pricelist.getStartDate().isBefore(movingDate)
                    && pricelist.getEndDate().isAfter(movingDate)) {
                    	if (additionalServices != null) {
                    		for (AdditionalService service : additionalServices) {
                        		aditionalPrice += pricelist.getAdditionalServicePrice(service);
                        	} 
                        }
                    }
				}	
		    }
		} else {
			System.err.println("Hotel nije spreman za check in, ne postoji cenovnik!");
			System.exit(1);			
		}
		this.price += aditionalPrice;
	}
}