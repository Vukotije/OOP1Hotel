package storage;

import java.util.HashMap;

import entitiesClasses.AdditionalService;

public class AdditionalServiceData {

	
    private HashMap<String, AdditionalService> additionalServiceMap;
    
    public AdditionalServiceData() {
    	additionalServiceMap = new HashMap<>();
    }

    public void addAdditionalService(AdditionalService additionalService) {
    	additionalServiceMap.put(additionalService.getId(), additionalService);
    }
    
	public AdditionalService getAdditionalService(String id) {
		return additionalServiceMap.get(id);
	}
	
	public HashMap<String, AdditionalService> getAdditionalService() {
		return additionalServiceMap;
	}

	public void removeAdditionalService(String id) {
		additionalServiceMap.remove(id);
	}

}
