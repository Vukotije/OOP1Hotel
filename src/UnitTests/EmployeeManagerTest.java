package UnitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import entitiesClasses.Admin;
import entitiesClasses.Maid;
import entitiesClasses.Room;
import entitiesEnums.EducationLevel;
import entitiesEnums.Gender;
import entitiesEnums.RoomStatus;
import entitiesEnums.RoomType;
import entitiesManager.EmployeeManager;

public class EmployeeManagerTest {

    private EmployeeManager employeeManager;

    @Before
    public void setUp() {
        employeeManager = EmployeeManager.getInstance();
        employeeManager.setEmployeeData(new HashMap<>());
    }

    @Test
    public void testAddEmployee() {
        Admin admin = new Admin("A001", 5000.0, "John", "Doe", Gender.MALE, 
                                LocalDate.of(1980, 1, 1), "1234567890", "123 Main St", 
                                "johndoe", "password", EducationLevel.BACHELORS_DEGREE, 5.0);
        employeeManager.addEmployee(admin);
        
        assertEquals(admin, employeeManager.getEmployee("A001"));
    }

    @Test
    public void testRemoveEmployee() {
        Admin admin = new Admin("A001", 5000.0, "John", "Doe", Gender.MALE, 
                                LocalDate.of(1980, 1, 1), "1234567890", "123 Main St", 
                                "johndoe", "password", EducationLevel.DOCTORATE_DEGREE, 5.0);
        employeeManager.addEmployee(admin);
        employeeManager.removeEmployee("A001");
        
        assertNull(employeeManager.getEmployee("A001"));
    }

    @Test
    public void testGetEmployeeByName() {
        Admin admin = new Admin("A001", 5000.0, "John", "Doe", Gender.MALE, 
                                LocalDate.of(1980, 1, 1), "1234567890", "123 Main St", 
                                "johndoe", "password", EducationLevel.HIGH_SCHOOL, 5.0);
        employeeManager.addEmployee(admin);
        
        assertEquals(admin, employeeManager.getEmployeeByName("John", "Doe"));
    }

    @Test
    public void testAssignRoomToMaid() {
        Maid maid = new Maid("M001", 3000.0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                             "Jane", "Doe", Gender.FEMALE, LocalDate.of(1990, 1, 1),
                             "0987654321", "456 Elm St", "janedoe", "password",
                             EducationLevel.PRIMARY_SCHOOL, 2.0);
        employeeManager.addEmployee(maid);

        Room room = new Room("R001", 101, RoomType.THREE_PERSON_ROOM_ONE_DOUBLE_BED_ONE_SINGLE_BED, RoomStatus.AVAILABLE, null, new HashSet<>());
        employeeManager.assignRoomToMaid(room);

        assertTrue(maid.getRoomsLeftToClean().contains(room));
    }
}