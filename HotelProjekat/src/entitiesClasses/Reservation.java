package entitiesClasses;

import java.time.LocalDate;
import java.util.ArrayList;

import entitiesEnums.ReservationStatus;
import entitiesEnums.RoomType;
import storage.PricelistData;

public class Reservation extends BaseEntity{
	
	@Override
	public String toString() {
		return "Reservation [status=" + status + ", guest=" + guest + ", startDate=" + startDate + ", endDate="
				+ endDate + ", roomType=" + roomType + ", additionalServiceWanted=" + additionalServiceWanted
				+ ", price=" + price + "]";
	}


	private ReservationStatus status;
	private Guest guest;
	private LocalDate startDate;
	private LocalDate endDate;
	private RoomType roomType;
	private ArrayList<AdditionalService> additionalServiceWanted;
	private double price;
	

	public Reservation(Guest guest, LocalDate startDate, LocalDate endDate, RoomType roomType,
			ArrayList<AdditionalService> additionalServiceWanted, PricelistData pricelistMap) {
	
	  super();
	  this.status = ReservationStatus.PENDING;
	  setGuest(guest);
	  setStartDate(startDate);
	  setEndDate(endDate);
	  setRoomType(roomType);	
	  setAdditionalServiceWanted(additionalServiceWanted);
	  generatePrice(pricelistMap);
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
	

	public ArrayList<AdditionalService> getAdditionalServiceWanted() {
		return additionalServiceWanted;
	}


	public void setAdditionalServiceWanted(ArrayList<AdditionalService> additionalServiceWanted) {
		this.additionalServiceWanted = additionalServiceWanted;
	}
		
	public void generatePrice(PricelistData pricelistData) {
		double price = 0;
		try {
			for (LocalDate movingDate = getStartDate(); !movingDate.isAfter(getEndDate()); movingDate = movingDate.plusDays(1)) {
				for (String id : pricelistData.getPricelistMap().keySet()) {
                    if (pricelistData.getPricelist(id).getStartDate().isBefore(movingDate)
                    && pricelistData.getPricelist(id).getEndDate().isAfter(movingDate)) {
                        price += pricelistData.getPricelist(id).getRoomTypePrice(getRoomType());
                        for (AdditionalService service : getAdditionalServiceWanted()) {
                            price += pricelistData.getPricelist(id).getAdditionalServicePrice(service);
                        }
                    }
				}	
		    }
		} catch (Exception e) {
			System.out.println("Hotel nije spreman za rezervacije, ne postoji cenovnik!");
		}
		this.price = price;
	}
}