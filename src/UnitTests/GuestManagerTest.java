package UnitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import java.time.LocalDate;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import entitiesClasses.Guest;
import entitiesEnums.Gender;
import entitiesManager.GuestManager;

public class GuestManagerTest {

    private GuestManager guestManager;

    @Before
    public void setUp() {
        guestManager = GuestManager.getInstance();
        guestManager.setGuestData(new HashMap<>());
    }

    @Test
    public void testSingletonInstance() {
        GuestManager instance1 = GuestManager.getInstance();
        GuestManager instance2 = GuestManager.getInstance();
        assertSame(instance1, instance2);
    }

    @Test
    public void testAddGuest() {
        Guest guest = new Guest("G001", "John", "Doe", Gender.MALE, LocalDate.of(1990, 1, 1), "1234567890", "123 Main St", "johndoe", "password");
        guestManager.addGuest(guest);

        assertEquals(guest, guestManager.getGuest("G001"));
    }

    @Test
    public void testRemoveGuest() {
        Guest guest = new Guest("G001", "John", "Doe", Gender.MALE, LocalDate.of(1990, 1, 1), "1234567890", "123 Main St", "johndoe", "password");
        guestManager.addGuest(guest);
        guestManager.removeGuest("G001");

        assertNull(guestManager.getGuest("G001"));
    }

    @Test
    public void testGetGuestByName() {
        Guest guest = new Guest("G001", "John", "Doe", Gender.MALE, LocalDate.of(1990, 1, 1), "1234567890", "123 Main St", "johndoe", "password");
        guestManager.addGuest(guest);

        assertEquals(guest, guestManager.getGuestByName("John", "Doe"));
    }

    @Test
    public void testReadGuest() {
        Guest guest1 = new Guest("G001", "John", "Doe", Gender.MALE, LocalDate.of(1990, 1, 1), "1234567890", "123 Main St", "johndoe", "password");
        Guest guest2 = new Guest("G002", "Jane", "Smith", Gender.FEMALE, LocalDate.of(1992, 2, 2), "0987654321", "456 Elm St", "janesmith", "password");
        guestManager.addGuest(guest1);
        guestManager.addGuest(guest2);

        guestManager.readGuest();
    }
}
