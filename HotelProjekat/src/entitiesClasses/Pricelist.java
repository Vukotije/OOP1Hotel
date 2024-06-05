package entitiesClasses;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.HashMap;

import entitiesEnums.RoomType;

public class Pricelist extends BaseEntity{
	
	private LocalDate startDate;
	private LocalDate endDate;
	private EnumMap<RoomType, Double> roomTypePrices;
	private HashMap<AdditionalService, Double> additionalServicePrices;
	
//	==============================================================================================
//										CONSTRUCTORS
//	==============================================================================================
	

	public Pricelist(LocalDate startDate, LocalDate endDate, 
			EnumMap<RoomType, Double> roomTypePrices,
			HashMap<AdditionalService, Double> additionalServicePrices) {
	    super();
	    setStartDate(startDate);
	    setEndDate(endDate);
	    setRoomTypePrices(roomTypePrices);
	    setAdditionalServicePrices(additionalServicePrices);
	}
	
	public Pricelist(String id, LocalDate startDate, LocalDate endDate, 
			EnumMap<RoomType, Double> roomTypePrices,
			HashMap<AdditionalService, Double> additionalServicePrices) {
	    super();
	    setStartDate(startDate);
	    setEndDate(endDate);
	    setRoomTypePrices(roomTypePrices);
	    setAdditionalServicePrices(additionalServicePrices);
	    setId(id);
	}
	
//	==============================================================================================
//										GETTERS & SETTERS
//	==============================================================================================
	

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
	
	public double getRoomTypePrice(RoomType roomType) {
		return roomTypePrices.get(roomType);
	}

	public void setRoomTypePrice(RoomType roomType, Double price) {
		this.roomTypePrices.put(roomType, price);
	}

	public EnumMap<RoomType, Double> getRoomTypePrices() {
		return roomTypePrices;
	}
	
	public void setRoomTypePrices(EnumMap<RoomType, Double> roomTypePrices) {
		this.roomTypePrices = roomTypePrices;
	}
	
	public double getAdditionalServicePrice(AdditionalService service) {
        return additionalServicePrices.get(service);
    }

    public void setAdditionalServicePrice(AdditionalService service, Double price) {
        this.additionalServicePrices.put(service, price);
    }

    public HashMap<AdditionalService, Double> getAdditionalServicePrices() {
        return additionalServicePrices;
    }
    
	public void setAdditionalServicePrices(HashMap<AdditionalService, Double> additionalServicePrices) {
		this.additionalServicePrices = additionalServicePrices;
	}
		
//	==============================================================================================
//										TO STRING
//	==============================================================================================
	
	@Override
	public String toString() {
		return "Pricelist [startDate=" + startDate + ", endDate=" + endDate + ", roomTypePrices=" +
				roomTypePrices + ", additionalServicePrices=" + additionalServicePrices + "]";
	}


}
