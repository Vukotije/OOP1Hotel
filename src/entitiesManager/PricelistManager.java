package entitiesManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.EnumMap;
import java.util.HashMap;

import entitiesClasses.AdditionalService;
import entitiesClasses.Pricelist;
import entitiesEnums.RoomType;

public class PricelistManager implements CSVSupport{
	
	private static PricelistManager instance = null;
    private HashMap<String, Pricelist> pricelistData;
    
//	==============================================================================================
//										CONSTRUCTOR & INSTANCE
//	==============================================================================================
	
    private PricelistManager() {
    	pricelistData = new HashMap<>();
    	readFromCSV();
    }
    
	public static PricelistManager getInstance() {
		if (instance == null) {
			instance = new PricelistManager();
		}
		return instance;
	}

//	==============================================================================================
//											CSV SUPPORT
//	==============================================================================================
	
	@Override
	public void writeToCSV() {
		String filePath = "storage/pricelistData.csv";
		try (FileWriter writer = new FileWriter(filePath)) {
			for (String id : pricelistData.keySet()) {
				Pricelist pricelist = getPricelist(id);
	            StringBuilder sb = new StringBuilder();
				sb.append(id)
					.append(',')
					.append(pricelist.getStartDate().toString())
					.append(',')
					.append(pricelist.getEndDate().toString())
					.append(',')
					.append(' ')
					.append(',');
				
				for (RoomType roomType : pricelist.getRoomTypePrices().keySet()){
                    sb.append(roomType.toString())
	                    .append(',')
	                    .append(Double.toString(pricelist.getRoomTypePrice(roomType)))
	                    .append(',');
                }

				sb.append(' ')
					.append(',');
				                
                for (AdditionalService additionalService : pricelist.getAdditionalServicePrices().keySet()) {
                    sb.append(additionalService.getName())
                    	.append(',')
                    	.append(Double.toString(pricelist.getAdditionalServicePrice(additionalService)))
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
		String filePath = "storage/pricelistData.csv";
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] values = line.split(",");
				String id = values[0];
				LocalDate startDate = LocalDate.parse(values[1]);
				LocalDate endDate = LocalDate.parse(values[2]);
				
				EnumMap<RoomType, Double> roomTypePrices = new EnumMap<>(RoomType.class);
				
				// index 3 is empty
				int i = 4;
				while (!values[i].strip().isEmpty()) {
					RoomType roomType = RoomType.fromString(values[i]);
					Double roomTypePrice = Double.parseDouble(values[i + 1]);
					roomTypePrices.put(roomType, roomTypePrice);
					i+=2;
				}
				i++;
				
				HashMap<AdditionalService, Double> additionalServicePrices = new HashMap<>();
				
				while (i < values.length) {
					AdditionalService additionalService = 
							AdditionalServiceManager.getInstance().getAdditionalServiceByName((values[i]));
					Double additionalServiceTypePrice = Double.parseDouble(values[i + 1]);
					additionalServicePrices.put(additionalService, additionalServiceTypePrice);
					i+=2;
				}

				addPricelist(new Pricelist(id, startDate, endDate, roomTypePrices, additionalServicePrices));
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
	
    public void setPricelistData(HashMap<String, Pricelist> pricelistData) {
    	this.pricelistData = pricelistData;
    }

	public HashMap<String, Pricelist> getPricelistData() {
		return pricelistData;
	}
	
	public Pricelist getPricelist(String id) {
		return pricelistData.get(id);
	}
	
	// won't be used by GUI
	public Pricelist getPriceListByStartDate(LocalDate startDate) {
		try {
			for (String id : getPricelistData().keySet()) {
				if (getPricelist(id).getStartDate().equals(startDate)) {
					return getPricelist(id);
				}
			}
		} catch (Exception e) {
			System.err.println("Cenovnik ne postoji.");
		}
		return null;
	}
	
//	==============================================================================================
//											C R U D
//	==============================================================================================
	
    public void addPricelist(Pricelist pricelist) {
    	pricelistData.put(pricelist.getId(), pricelist);
    }

	public void removePricelist(String id) {
		pricelistData.remove(id);
	}
	
	public void readPricelistData() {
		System.out.println("Pricelist Data:");
		for (String id : pricelistData.keySet()) {
			System.out.println(pricelistData.get(id).toString());
		}
	}
		
}
