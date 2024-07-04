package UnitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import entitiesClasses.AdditionalService;
import entitiesClasses.Pricelist;
import entitiesEnums.RoomType;
import entitiesManager.PricelistManager;

public class PricelistManagerTest {

    private PricelistManager pricelistManager;

    @Before
    public void setUp() {
        pricelistManager = PricelistManager.getInstance();
        pricelistManager.setPricelistData(new HashMap<>());
    }

    @Test
    public void testAddPricelist() {
        EnumMap<RoomType, Double> roomTypePrices = new EnumMap<>(RoomType.class);
        roomTypePrices.put(RoomType.THREE_PERSON_ROOM_ONE_DOUBLE_BED_ONE_SINGLE_BED, 100.0);
        roomTypePrices.put(RoomType.ONE_PERSON_ROOM_ONE_SINGLE_BED, 150.0);

        HashMap<AdditionalService, Double> additionalServicePrices = new HashMap<>();
        AdditionalService wifi = new AdditionalService("AS001", "WiFi");
        additionalServicePrices.put(wifi, 10.0);

        Pricelist pricelist = new Pricelist("P001", LocalDate.of(2024, 1, 1), 
                                            LocalDate.of(2024, 12, 31), roomTypePrices, 
                                            additionalServicePrices);
        pricelistManager.addPricelist(pricelist);

        assertEquals(pricelist, pricelistManager.getPricelist("P001"));
    }

    @Test
    public void testRemovePricelist() {
        EnumMap<RoomType, Double> roomTypePrices = new EnumMap<>(RoomType.class);
        HashMap<AdditionalService, Double> additionalServicePrices = new HashMap<>();
        Pricelist pricelist = new Pricelist("P001", LocalDate.of(2024, 1, 1), 
                                            LocalDate.of(2024, 12, 31), roomTypePrices, 
                                            additionalServicePrices);
        pricelistManager.addPricelist(pricelist);
        pricelistManager.removePricelist("P001");

        assertNull(pricelistManager.getPricelist("P001"));
    }

    @Test
    public void testGetPriceListByStartDate() {
        EnumMap<RoomType, Double> roomTypePrices = new EnumMap<>(RoomType.class);
        HashMap<AdditionalService, Double> additionalServicePrices = new HashMap<>();
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        Pricelist pricelist = new Pricelist("P001", startDate, 
                                            LocalDate.of(2024, 12, 31), roomTypePrices, 
                                            additionalServicePrices);
        pricelistManager.addPricelist(pricelist);

        assertEquals(pricelist, pricelistManager.getPriceListByStartDate(startDate));
    }
}