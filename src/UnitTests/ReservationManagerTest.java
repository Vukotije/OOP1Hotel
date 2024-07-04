package UnitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import entitiesClasses.AdditionalService;
import entitiesClasses.Guest;
import entitiesClasses.Reservation;
import entitiesClasses.Room;
import entitiesClasses.RoomFacility;
import entitiesEnums.Gender;
import entitiesEnums.ReservationStatus;
import entitiesEnums.RoomStatus;
import entitiesEnums.RoomType;
import entitiesManager.ReservationManager;
import entitiesManager.RoomManager;

public class ReservationManagerTest {

    private ReservationManager reservationManager;

    @Before
    public void setUp() {
        reservationManager = ReservationManager.getInstance();
        reservationManager.setReservationData(new HashMap<>());
    }

    @Test
    public void testMakeReservation() {
        Guest guest = new Guest("G001", "John", "Doe", Gender.MALE, LocalDate.of(1990, 1, 1),
                                "1234567890", "123 Main St", "johndoe", "password");
        ArrayList<AdditionalService> additionalServices = new ArrayList<>();
        Set<RoomFacility> roomFacilities = new HashSet<>();
        
        reservationManager.makeReservation(LocalDate.now().plusDays(1), LocalDate.now().plusDays(3),
                                           guest, additionalServices, roomFacilities, RoomType.ONE_PERSON_ROOM_ONE_SINGLE_BED);

        assertFalse(reservationManager.getReservationData().isEmpty());
    }

    @Test
    public void testApproveReservation() {
        Guest guest = new Guest("G001", "John", "Doe", Gender.MALE, LocalDate.of(1990, 1, 1),
                                "1234567890", "123 Main St", "johndoe", "password");
        Reservation reservation = new Reservation(ReservationStatus.PENDING, guest,
                                                  LocalDate.now().plusDays(1), LocalDate.now().plusDays(3),
                                                  RoomType.THREE_PERSON_ROOM_ONE_DOUBLE_BED_ONE_SINGLE_BED, new HashSet<>(), new ArrayList<>());
        reservationManager.addReservation(reservation);

        Room room = new Room("R001", 101, RoomType.TWO_PERSON_ROOM_TWO_SINGLE_BEDS, RoomStatus.AVAILABLE, null, new HashSet<>());
        RoomManager.getInstance().addRoom(room);

        assertTrue(reservationManager.approveReservation(reservation.getId()));
        assertEquals(ReservationStatus.APPROVED, reservation.getStatus());
    }

    @Test
    public void testDenyReservation() {
        Guest guest = new Guest("G001", "John", "Doe", Gender.MALE, LocalDate.of(1990, 1, 1),
                                "1234567890", "123 Main St", "johndoe", "password");
        Reservation reservation = new Reservation(ReservationStatus.PENDING, guest,
                                                  LocalDate.now().plusDays(1), LocalDate.now().plusDays(3),
                                                  RoomType.THREE_PERSON_ROOM_ONE_DOUBLE_BED_ONE_SINGLE_BED, new HashSet<>(), new ArrayList<>());
        reservationManager.addReservation(reservation);

        reservationManager.denyReservation(reservation.getId());
        assertEquals(ReservationStatus.DENIED, reservation.getStatus());
    }

    @Test
    public void testCancelReservation() {
        Guest guest = new Guest("G001", "John", "Doe", Gender.MALE, LocalDate.of(1990, 1, 1),
                                "1234567890", "123 Main St", "johndoe", "password");
        Reservation reservation = new Reservation(ReservationStatus.APPROVED, guest,
                                                  LocalDate.now().plusDays(1), LocalDate.now().plusDays(3),
                                                  RoomType.THREE_PERSON_ROOM_THREE_SINGLE_BEDS, new HashSet<>(), new ArrayList<>());
        reservationManager.addReservation(reservation);

        reservationManager.cancelReservation(reservation.getId());
        assertEquals(ReservationStatus.CANCELLED, reservation.getStatus());
    }
}