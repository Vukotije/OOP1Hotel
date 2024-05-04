package storage;

import java.util.HashMap;

import entitiesClasses.Pricelist;

public class PricelistData {
	
    private HashMap<String, Pricelist> pricelistMap;
    
    public PricelistData() {
    	pricelistMap = new HashMap<>();
    }

    public void addPricelist(Pricelist pricelist) {
    	pricelistMap.put(pricelist.getId(), pricelist);
    }
    
	public Pricelist getPricelist(String id) {
		return pricelistMap.get(id);
	}
	
	public HashMap<String, Pricelist> getPricelistMap() {
		return pricelistMap;
	}

	public void removePricelist(String id) {
		pricelistMap.remove(id);
	}

}
