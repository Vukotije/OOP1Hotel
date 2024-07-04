package entitiesManager;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import entitiesClasses.AdditionalService;
public class AdditionalServiceManager implements CSVSupport {

    private static AdditionalServiceManager instance = null;
    private HashMap<String, AdditionalService> additionalServiceDataHashMap;

//	==============================================================================================
//										CONSTRUCTOR & INSTANCE
//	==============================================================================================
	
    private AdditionalServiceManager() {
        additionalServiceDataHashMap = new HashMap<>();
        readFromCSV();
    }

    public static AdditionalServiceManager getInstance() {
        if (instance == null) {
            instance = new AdditionalServiceManager();
        }
        return instance;
    }

//	==============================================================================================
//											CSV SUPPORT
//	==============================================================================================
	
    @Override
    public void writeToCSV() {
    	String filePath = "storage/additionalServiceData.csv";
        try (FileWriter writer = new FileWriter(filePath)) {
            for (String id : additionalServiceDataHashMap.keySet()) {
                AdditionalService additionalService = getAdditionalService(id);
                writer.append(id)
                      .append(',')
                      .append(additionalService.getName())
                      .append('\n');
            }
        }catch (IOException e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
        }
    }

    @Override
    public void readFromCSV(){
    	String filePath = "storage/additionalServiceData.csv";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                String id = values[0];
                String name = values[1];
                addAdditionalService(new AdditionalService(name, id));
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
    
	public void setAdditionalServiceData(HashMap<String, AdditionalService> additionalServiceDataHashMap) {
		this.additionalServiceDataHashMap = additionalServiceDataHashMap;
	}

	public HashMap<String, AdditionalService> getAdditionalServiceData() {
		return additionalServiceDataHashMap;
	}

	public AdditionalService getAdditionalService(String id) {
		return additionalServiceDataHashMap.get(id);
	}
	
	// won't be used by GUI
	public AdditionalService getAdditionalServiceByName(String name) {
		try {
			for (String id : getAdditionalServiceData().keySet()) {
				if (getAdditionalService(id).getName().equalsIgnoreCase(name)) {
					return getAdditionalService(id);
				}
			}
		} catch (Exception e) {
			System.err.println("Usluga ne postoji.");
		}
		return null;
	}	

//	==============================================================================================
//											C R U D
//	==============================================================================================
	
	public void addAdditionalService(AdditionalService additionalService) {
    	additionalServiceDataHashMap.put(additionalService.getId(), additionalService);
    }
    
	public void deleteAditionalService(String id) {
		additionalServiceDataHashMap.remove(id);
	}
	
	public void readAdditionalServiceData() {
		System.out.println("Additional Service Data:");
		for (String id : additionalServiceDataHashMap.keySet()) {
			System.out.println(additionalServiceDataHashMap.get(id).toString());
		}
	}
}
