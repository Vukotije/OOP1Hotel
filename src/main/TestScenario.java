package main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import entitiesClasses.AdditionalService;
import entitiesClasses.Admin;
import entitiesClasses.Guest;
import entitiesClasses.Maid;
import entitiesClasses.Pricelist;
import entitiesClasses.Receptionist;
import entitiesClasses.Room;
import entitiesClasses.RoomFacility;
import entitiesEnums.EducationLevel;
import entitiesEnums.Gender;
import entitiesEnums.RoomStatus;
import entitiesEnums.RoomType;
import entitiesManager.AdditionalServiceManager;
import entitiesManager.EmployeeManager;
import entitiesManager.GuestManager;
import entitiesManager.PricelistManager;
import entitiesManager.ReservationManager;
import entitiesManager.RoomFacilityManager;
import entitiesManager.RoomManager;

public class TestScenario {
	
	public static void kontrolnaTacka2() {
		System.out.println();
		System.out.println("=====================================================================================");
		System.out.println("				Kontrolna tacka 2");
		System.out.println("=====================================================================================");
		System.out.println();
		System.out.println();
		
		
		EmployeeManager.getInstance().addEmployee(new Admin("Pera", "Peric", Gender.MALE, LocalDate.of(1999, 9, 9),
				"0612345678", "Futoska 1", "peki", "peki123", EducationLevel.DOCTORATE_DEGREE, 10));
		EmployeeManager.getInstance().addEmployee(new Receptionist("Mika", "Mikic", Gender.MALE, LocalDate.of(2000, 1, 1),
				"0662691468", "Temerinska 1", "miki", "miki123", EducationLevel.HIGH_SCHOOL, 2));
		EmployeeManager.getInstance().addEmployee(new Receptionist("Nikola", "Nikolic", Gender.MALE, LocalDate.of(2006, 3, 7),
				"0692312859", "Solunska 3", "nidza", "nidza123", EducationLevel.PRIMARY_SCHOOL, 1));
		EmployeeManager.getInstance().addEmployee(new Maid("Jana", "Janic", Gender.FEMALE, LocalDate.of(1996, 7, 14),
				"0625871092", "Stevana Sremca 23", "jana", "jana123", EducationLevel.PRIMARY_SCHOOL, 15));
		
//		EmployeeManager.getInstance().readEmployee();
        
		EmployeeManager.getInstance().removeEmployee(EmployeeManager.getInstance().getEmployeeByName("Nikola", "Nikolic").getId());
        
        GuestManager.getInstance().addGuest(new Guest("Milica", "Milic", Gender.FEMALE, LocalDate.of(2004, 6, 25),
        		"0633298476", "Vojvode Supljikca 17", "mica", "mica123"));
        GuestManager.getInstance().addGuest(new Guest("Ana", "Anic", Gender.FEMALE, LocalDate.of(1992, 7, 12),
        		"0667788990", "Vojvode Bojovica 49", "ana", "ana123"));

        RoomFacilityManager.getInstance().addRoomFacility(new RoomFacility("Air Conditioning"));
        RoomFacilityManager.getInstance().addRoomFacility(new RoomFacility("Balcony"));
        RoomFacilityManager.getInstance().addRoomFacility(new RoomFacility("Smoking Allowed"));
        
        Set<RoomFacility> tip1 = new HashSet<>();
        tip1.add(RoomFacilityManager.getInstance().getRoomFacilityByName("Air Conditioning"));
        tip1.add(RoomFacilityManager.getInstance().getRoomFacilityByName("Balcony"));
        tip1.add(RoomFacilityManager.getInstance().getRoomFacilityByName("Smoking Allowed"));
                
        RoomManager.getInstance().addRoom(new Room(101, RoomType.ONE_PERSON_ROOM_ONE_SINGLE_BED, RoomStatus.AVAILABLE, tip1));
        RoomManager.getInstance().addRoom(new Room(102, RoomType.TWO_PERSON_ROOM_TWO_SINGLE_BEDS, RoomStatus.AVAILABLE, tip1));
        RoomManager.getInstance().addRoom(new Room(103, RoomType.TWO_PERSON_ROOM_ONE_DOUBLE_BED, RoomStatus.AVAILABLE, tip1));
        RoomManager.getInstance().addRoom(new Room(104, RoomType.THREE_PERSON_ROOM_ONE_DOUBLE_BED_ONE_SINGLE_BED, RoomStatus.AVAILABLE, tip1));
				
        //RoomManager.getInstance().getRoomByNumber(101).setRoomType(RoomType.THREE_PERSON_ROOM_ONE_DOUBLE_BED_ONE_SINGLE_BED);
        
		AdditionalServiceManager.getInstance().addAdditionalService(new AdditionalService("Breakfast"));
		AdditionalServiceManager.getInstance().addAdditionalService(new AdditionalService("Lunch"));
		AdditionalServiceManager.getInstance().addAdditionalService(new AdditionalService("Dinner"));
		AdditionalServiceManager.getInstance().addAdditionalService(new AdditionalService("Swiming Pool"));
		AdditionalServiceManager.getInstance().addAdditionalService(new AdditionalService("Spa Center"));
		
		AdditionalServiceManager.getInstance().deleteAditionalService(AdditionalServiceManager.getInstance().getAdditionalServiceByName("Spa Center").getId());
		
		EnumMap<RoomType, Double> ceneZaSobe = new EnumMap<>(RoomType.class);
		ceneZaSobe.put(RoomType.ONE_PERSON_ROOM_ONE_SINGLE_BED, 2500.0);
		ceneZaSobe.put(RoomType.TWO_PERSON_ROOM_TWO_SINGLE_BEDS, 3500.0);
		ceneZaSobe.put(RoomType.TWO_PERSON_ROOM_ONE_DOUBLE_BED, 4000.0);
		ceneZaSobe.put(RoomType.THREE_PERSON_ROOM_ONE_DOUBLE_BED_ONE_SINGLE_BED, 5500.0);
		ceneZaSobe.put(RoomType.THREE_PERSON_ROOM_THREE_SINGLE_BEDS, 6000.0);
		
		HashMap<AdditionalService, Double> ceneZaDodatneUsluge = new HashMap<>();
		ceneZaDodatneUsluge.put(AdditionalServiceManager.getInstance().getAdditionalServiceByName("Breakfast"), 300.0);
		ceneZaDodatneUsluge.put(AdditionalServiceManager.getInstance().getAdditionalServiceByName("Lunch"), 400.0);
		ceneZaDodatneUsluge.put(AdditionalServiceManager.getInstance().getAdditionalServiceByName("Dinner"), 400.0);
		ceneZaDodatneUsluge.put(AdditionalServiceManager.getInstance().getAdditionalServiceByName("Swiming Pool"), 6000.0);
		
		PricelistManager.getInstance().addPricelist(new Pricelist(LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 13),
				ceneZaSobe, ceneZaDodatneUsluge));
		
		PricelistManager.getInstance().getPriceListByStartDate(LocalDate.of(2024, 1, 1))
			.setAdditionalServicePrice(AdditionalServiceManager.getInstance()
			.getAdditionalServiceByName("Breakfast"), 500.0);
				
		ArrayList<AdditionalService> aditionalServices = new ArrayList<>();
		aditionalServices.add(AdditionalServiceManager.getInstance().getAdditionalServiceByName("Breakfast"));
		ReservationManager.getInstance().makeReservation(LocalDate.of(2024, 7, 3), LocalDate.of(2024, 7, 11), GuestManager.getInstance().getGuestByName("Milica", "Milic"), aditionalServices, tip1, RoomType.TWO_PERSON_ROOM_ONE_DOUBLE_BED);
		ReservationManager.getInstance().makeReservation(LocalDate.of(2024, 8, 1), LocalDate.of(2024, 8, 31), GuestManager.getInstance().getGuestByName("Milica", "Milic"), aditionalServices, tip1, RoomType.ONE_PERSON_ROOM_ONE_SINGLE_BED);
		ReservationManager.getInstance().makeReservation(LocalDate.of(2024, 9, 1), LocalDate.of(2024, 9, 4), GuestManager.getInstance().getGuestByName("Milica", "Milic"), aditionalServices, tip1, RoomType.ONE_PERSON_ROOM_ONE_SINGLE_BED);
		ReservationManager.getInstance().makeReservation(LocalDate.of(2024, 7, 9), LocalDate.of(2024, 7, 14), GuestManager.getInstance().getGuestByName("Ana", "Anic"), aditionalServices, tip1, RoomType.ONE_PERSON_ROOM_ONE_SINGLE_BED);
		ReservationManager.getInstance().makeReservation(LocalDate.of(2024, 7, 17), LocalDate.of(2024, 7, 30), GuestManager.getInstance().getGuestByName("Ana", "Anic"), aditionalServices, tip1, RoomType.THREE_PERSON_ROOM_ONE_DOUBLE_BED_ONE_SINGLE_BED);
		ReservationManager.getInstance().makeReservation(LocalDate.of(2024, 8, 9), LocalDate.of(2024, 8, 14), GuestManager.getInstance().getGuestByName("Ana", "Anic"), aditionalServices, tip1, RoomType.THREE_PERSON_ROOM_THREE_SINGLE_BEDS);
		ReservationManager.getInstance().makeReservation(LocalDate.of(2024, 9, 9), LocalDate.of(2024, 9, 24), GuestManager.getInstance().getGuestByName("Ana", "Anic"), aditionalServices, tip1, RoomType.THREE_PERSON_ROOM_ONE_DOUBLE_BED_ONE_SINGLE_BED);
		
		ReservationManager.getInstance().readGuestsReservation(GuestManager.getInstance().getGuestByName("Milica", "Milic"));
		

		
		System.out.println();	
		System.out.println();	
		System.out.println("=====================================================================================");
		System.out.println("=====================================================================================");
		System.out.println();	
	}
}
