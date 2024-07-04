package UnitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import entitiesClasses.AdditionalService;
import entitiesManager.AdditionalServiceManager;

public class AdditionalServiceManagerTest {

    private AdditionalServiceManager additionalServiceManager;

    @Before
    public void setUp() {
        additionalServiceManager = AdditionalServiceManager.getInstance();
        additionalServiceManager.setAdditionalServiceData(new HashMap<>());
    }

    @Test
    public void testAddAdditionalService() {
        AdditionalService service = new AdditionalService("WiFi", "AS001");
        additionalServiceManager.addAdditionalService(service);
        assertEquals(service, additionalServiceManager.getAdditionalService("AS001"));
    }

    @Test
    public void testDeleteAdditionalService() {
        AdditionalService service = new AdditionalService("WiFi", "AS001");
        additionalServiceManager.addAdditionalService(service);
        additionalServiceManager.deleteAditionalService("AS001");
        assertNull(additionalServiceManager.getAdditionalService("AS001"));
    }

    @Test
    public void testGetAdditionalServiceByName() {
        AdditionalService service = new AdditionalService("WiFi", "AS001");
        additionalServiceManager.addAdditionalService(service);
        assertEquals(service, additionalServiceManager.getAdditionalServiceByName("WiFi"));
    }

    @Test
    public void testGetAdditionalServiceByName_NotFound() {
        assertNull(additionalServiceManager.getAdditionalServiceByName("NonExistentService"));
    }
}