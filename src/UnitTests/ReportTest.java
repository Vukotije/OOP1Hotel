package UnitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import entitiesClasses.Admin;
import entitiesClasses.Employee;
import entitiesClasses.Guest;
import entitiesClasses.Maid;
import entitiesClasses.Reservation;
import entitiesClasses.Room;
import entitiesEnums.EducationLevel;
import entitiesEnums.Gender;
import entitiesEnums.ReservationStatus;
import entitiesEnums.RoomStatus;
import entitiesEnums.RoomType;
import entitiesManager.EmployeeManager;
import entitiesManager.ReservationManager;
import entitiesManager.RoomManager;
import reports.Report;

public class ReportTest {

    private ReservationManager reservationManager;
    private EmployeeManager employeeManager;
    private RoomManager roomManager;

    @Before
    public void setUp() {
        reservationManager = ReservationManager.getInstance();
        employeeManager = EmployeeManager.getInstance();
        roomManager = RoomManager.getInstance();
        reservationManager.setReservationData(new HashMap<>());
        employeeManager.setEmployeeData(new HashMap<>());
        roomManager.setRoomData(new HashMap<>());
    }

    @Test
    public void testGetRevenueAndVisitsForRoom_WithReservations() {
        Room room = new Room("R001", 101, RoomType.ONE_PERSON_ROOM_ONE_SINGLE_BED, RoomStatus.AVAILABLE, null, new HashSet<>());
        roomManager.addRoom(room);
        
        Guest guest = new Guest("G001", "John", "Doe", Gender.MALE, LocalDate.of(1990, 1, 1), "1234567890", "123 Main St", "johndoe", "password");
        Reservation reservation = new Reservation("RES001", LocalDate.now(), room, 100.0, ReservationStatus.APPROVED, guest, LocalDate.now().plusDays(1), LocalDate.now().plusDays(3), RoomType.THREE_PERSON_ROOM_ONE_DOUBLE_BED_ONE_SINGLE_BED, new HashSet<>(), new ArrayList<>());
        reservationManager.addReservation(reservation);

        int[] result = Report.getRevenueAndWisitsForRoom(LocalDate.now(), LocalDate.now().plusDays(5), room);
        assertEquals(1, result[0]); 
        assertEquals(0, result[1]); 
    }

    @Test
    public void testGetRevenueAndVisitsForRoom_NoReservations() {
        Room room = new Room("R001", 101, RoomType.THREE_PERSON_ROOM_ONE_DOUBLE_BED_ONE_SINGLE_BED, RoomStatus.AVAILABLE, null, new HashSet<>());
        roomManager.addRoom(room);

        int[] result = Report.getRevenueAndWisitsForRoom(LocalDate.now(), LocalDate.now().plusDays(5), room);
        assertEquals(0, result[0]); 
        assertEquals(0, result[1]); 
    }

    @Test
    public void testGetRevenue_WithReservations() {
        Guest guest = new Guest("G001", "John", "Doe", Gender.MALE, LocalDate.of(1990, 1, 1), "1234567890", "123 Main St", "johndoe", "password");
        Reservation reservation = new Reservation("RES001", LocalDate.now(), null, 100.0, ReservationStatus.APPROVED, guest, LocalDate.now().plusDays(1), LocalDate.now().plusDays(3), RoomType.ONE_PERSON_ROOM_ONE_SINGLE_BED, new HashSet<>(), new ArrayList<>());
        reservationManager.addReservation(reservation);

        int revenue = Report.getRevenue(LocalDate.now(), LocalDate.now().plusDays(5));
        assertEquals(100, revenue);
    }

    @Test
    public void testGetRevenue_NoReservations() {
        int revenue = Report.getRevenue(LocalDate.now(), LocalDate.now().plusDays(5));
        assertEquals(0, revenue);
    }

    @Test
    public void testGetExpenses_WithEmployees() {
        Employee employee = new Admin("E001", 1000.0, "Jane", "Doe", Gender.FEMALE, LocalDate.of(1985, 1, 1), "0987654321", "456 Elm St", "janedoe", "password", EducationLevel.BACHELORS_DEGREE, 5.0);
        employeeManager.addEmployee(employee);

        int expenses = Report.getExpenses(LocalDate.now(), LocalDate.now().plusMonths(2));
        assertEquals(2000, expenses);
    }

    @Test
    public void testGetExpenses_NoEmployees() {
        int expenses = Report.getExpenses(LocalDate.now(), LocalDate.now().plusMonths(2));
        assertEquals(0, expenses);
    }

    @Test
    public void testReservationReport_WithReservations() {
        Guest guest = new Guest("G001", "John", "Doe", Gender.MALE, LocalDate.of(1990, 1, 1), "1234567890", "123 Main St", "johndoe", "password");
        Reservation reservation1 = new Reservation("RES001", LocalDate.now(), null, 100.0, ReservationStatus.APPROVED, guest, LocalDate.now().plusDays(1), LocalDate.now().plusDays(3), RoomType.TWO_PERSON_ROOM_TWO_SINGLE_BEDS, new HashSet<>(), new ArrayList<>());
        Reservation reservation2 = new Reservation("RES002", LocalDate.now(), null, 150.0, ReservationStatus.PENDING, guest, LocalDate.now().plusDays(4), LocalDate.now().plusDays(6), RoomType.THREE_PERSON_ROOM_THREE_SINGLE_BEDS, new HashSet<>(), new ArrayList<>());
        reservationManager.addReservation(reservation1);
        reservationManager.addReservation(reservation2);

        EnumMap<ReservationStatus, Integer> report = Report.reservationReport(LocalDate.now().minusDays(1), LocalDate.now().plusDays(1));
        assertEquals(1, (int) report.get(ReservationStatus.APPROVED));
        assertEquals(1, (int) report.get(ReservationStatus.PENDING));
        assertEquals(0, (int) report.get(ReservationStatus.CANCELLED));
        assertEquals(0, (int) report.get(ReservationStatus.DENIED));
    }

    @Test
    public void testReservationReport_NoReservations() {
        EnumMap<ReservationStatus, Integer> report = Report.reservationReport(LocalDate.now().minusDays(1), LocalDate.now().plusDays(1));
        assertEquals(0, (int) report.get(ReservationStatus.APPROVED));
        assertEquals(0, (int) report.get(ReservationStatus.PENDING));
        assertEquals(0, (int) report.get(ReservationStatus.CANCELLED));
        assertEquals(0, (int) report.get(ReservationStatus.DENIED));
    }

    @Test
    public void testGetMonthlyRevenueByRoomType_WithReservations() {
        Guest guest = new Guest("G001", "John", "Doe", Gender.MALE, LocalDate.of(1990, 1, 1), "1234567890", "123 Main St", "johndoe", "password");
        Reservation reservation1 = new Reservation("RES001", LocalDate.now().minusMonths(1), null, 100.0, ReservationStatus.APPROVED, guest, LocalDate.now().minusMonths(1).plusDays(1), LocalDate.now().minusMonths(1).plusDays(3), RoomType.ONE_PERSON_ROOM_ONE_SINGLE_BED, new HashSet<>(), new ArrayList<>());
        Reservation reservation2 = new Reservation("RES002", LocalDate.now(), null, 150.0, ReservationStatus.APPROVED, guest, LocalDate.now().plusDays(1), LocalDate.now().plusDays(3), RoomType.THREE_PERSON_ROOM_THREE_SINGLE_BEDS, new HashSet<>(), new ArrayList<>());
        reservationManager.addReservation(reservation1);
        reservationManager.addReservation(reservation2);

        Map<String, List<Double>> revenueData = Report.getMonthlyRevenueByRoomType(3);
        assertEquals(3, revenueData.get("Total").size());
        assertTrue(revenueData.get(RoomType.ONE_PERSON_ROOM_ONE_SINGLE_BED.toString()).contains(100.0));
        assertTrue(revenueData.get(RoomType.THREE_PERSON_ROOM_THREE_SINGLE_BEDS.toString()).contains(150.0));
    }

    @Test
    public void testGetMonthlyRevenueByRoomType_NoReservations() {
        Map<String, List<Double>> revenueData = Report.getMonthlyRevenueByRoomType(3);
        assertEquals(3, revenueData.get("Total").size());
        assertTrue(revenueData.get(RoomType.ONE_PERSON_ROOM_ONE_SINGLE_BED.toString()).stream().allMatch(value -> value == 0.0));
        assertTrue(revenueData.get(RoomType.TWO_PERSON_ROOM_ONE_DOUBLE_BED.toString()).stream().allMatch(value -> value == 0.0));
    }

    @Test
    public void testGetMaidDistribution_WithAssignments() {
        Maid maid1 = new Maid("M001", 2000.0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), "Alice", "Smith", Gender.FEMALE, LocalDate.of(1990, 1, 1), "1234567890", "123 Main St", "alice", "password", EducationLevel.HIGH_SCHOOL, 2.0);
        Maid maid2 = new Maid("M002", 2000.0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), "Bob", "Jones", Gender.MALE, LocalDate.of(1985, 1, 1), "0987654321", "456 Elm St", "bob", "password", EducationLevel.PRIMARY_SCHOOL, 3.0);
        employeeManager.addEmployee(maid1);
        employeeManager.addEmployee(maid2);

        maid1.getRoomsAssignedToClean().add(LocalDate.now().minusDays(1));
        maid1.getRoomsAssignedToClean().add(LocalDate.now());
        maid2.getRoomsAssignedToClean().add(LocalDate.now());

        Map<String, Integer> distribution = Report.getMaidDistribution(7);
        assertEquals(2, (int) distribution.get("Alice"));
        assertEquals(1, (int) distribution.get("Bob"));
    }

    @Test
    public void testGetMaidDistribution_NoAssignments() {
        Maid maid = new Maid("M001", 2000.0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), "Alice", "Smith", Gender.FEMALE, LocalDate.of(1990, 1, 1), "1234567890", "123 Main St", "alice", "password", EducationLevel.BACHELORS_DEGREE, 2.0);
        employeeManager.addEmployee(maid);

        Map<String, Integer> distribution = Report.getMaidDistribution(7);
        assertEquals(0, (int) distribution.get("Alice"));
    }
}