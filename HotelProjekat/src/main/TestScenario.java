package main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;

import entitiesClasses.AdditionalService;
import entitiesClasses.Admin;
import entitiesClasses.Guest;
import entitiesClasses.Maid;
import entitiesClasses.Pricelist;
import entitiesClasses.Receptionist;
import entitiesClasses.Reservation;
import entitiesClasses.Room;
import entitiesEnums.EducationLevel;
import entitiesEnums.Gender;
import entitiesEnums.RoomFacilities;
import entitiesEnums.RoomStatus;
import entitiesEnums.RoomType;
import functionalities.Available;
import storage.AdditionalServiceData;
import storage.EmployeeData;
import storage.GuestData;
import storage.PricelistData;
import storage.ReservationData;
import storage.RoomData;

public class TestScenario {
	
	public static void kontrolnaTacka2() {
		System.out.println("Kontrolna tacka 2:\n");
		
		EmployeeData userData = new EmployeeData();
		GuestData guestData = new GuestData();
		RoomData roomData = new RoomData();
		AdditionalServiceData additionalServiceData = new AdditionalServiceData();
		PricelistData pricelistData = new PricelistData();
		ReservationData reservationData = new ReservationData();
		
		Admin peraPeric = new Admin("Pera", "Peric", Gender.MALE, LocalDate.of(1999, 9, 9), "0612345678",
				"Futoska 1", "peki", "supersifra123", EducationLevel.DOCTORATE_DEGREE, 10, 200000 );
		userData.addEmployee(peraPeric);
		Receptionist mikaMikic = new Receptionist("Mika", "Mikic", Gender.MALE, LocalDate.of(2000, 1, 1), "0662691468",
				"Temerinska 1", "miki", "mikicar34", EducationLevel.HIGH_SCHOOL, 2, 70000 );	
		userData.addEmployee(mikaMikic);
		Receptionist nikolaNikolic = new Receptionist("Nikola", "Nikolic", Gender.MALE, LocalDate.of(2006, 3, 7), "0692312859",
				"Solunska 3", "nidza", "nekasifra2020", EducationLevel.PRIMARY_SCHOOL, 1, 65000 );	
		userData.addEmployee(nikolaNikolic);
		Maid janaJanic = new Maid("Jana", "Janic", Gender.FEMALE, LocalDate.of(1996, 7, 14), "0625871092",
				"Stevana Sremca 23", "janili", "nenekretnine43", EducationLevel.PRIMARY_SCHOOL, 15, 60000 );
		userData.addEmployee(janaJanic);
		
		System.out.println("Svi zaposleni: ");
        for (String id : userData.getEmployeesMap().keySet()) {
        	System.out.println(userData.getEmployee(id));
        }
        System.out.println();
        
        userData.removeEmployee(nikolaNikolic.getId());
        
        Guest mlicaMilic = new Guest("Mlica", "Milic", Gender.FEMALE, LocalDate.of(2004, 6, 25),
        		"0633298476", "Vojvode Supljikca 17", "mica", "comili021");
        guestData.addGuest(mlicaMilic);
        Guest anaAnic = new Guest("Ana", "Anic", Gender.FEMALE, LocalDate.of(1992, 7, 12),
        		"0667788990", "Vojvode Bojovica 49", "aniko", "mojanovasifra5454");
        guestData.addGuest(anaAnic);

        EnumMap<RoomFacilities, Boolean> tip1 = new EnumMap<>(RoomFacilities.class);
        tip1.put(RoomFacilities.AC_UNIT, true);
        tip1.put(RoomFacilities.TV, true);
        
        EnumMap<RoomFacilities, Boolean> tip2 = new EnumMap<>(RoomFacilities.class);
        tip2.put(RoomFacilities.BALCONY, true);
        tip2.put(RoomFacilities.SMOKING_ROOM, true);
        
        
        Room soba101 = new Room(101, RoomType.ONE_PERSON_ROOM_ONE_SINGLE_BED, RoomStatus.AVAILABLE, tip1);
        roomData.addRoom(soba101);        
        Room soba102 = new Room(102, RoomType.TWO_PERSON_ROOM_TWO_SINGLE_BEDS, RoomStatus.AVAILABLE, tip2);
        roomData.addRoom(soba102);
        Room soba103 = new Room(103, RoomType.TWO_PERSON_ROOM_ONE_DOUBLE_BED, RoomStatus.AVAILABLE, tip1);
        roomData.addRoom(soba103);
        Room soba104 = new Room(104, RoomType.THREE_PERSON_ROOM_ONE_DOUBLE_BED_ONE_SINGLE_BED, RoomStatus.AVAILABLE, tip2);
		roomData.addRoom(soba104);
				
		soba103.setRoomType(RoomType.THREE_PERSON_ROOM_ONE_DOUBLE_BED_ONE_SINGLE_BED);
				
		AdditionalService dorucak = new AdditionalService("Dorucak");
		additionalServiceData.addAdditionalService(dorucak);
		AdditionalService rucak = new AdditionalService("Rucak");
		additionalServiceData.addAdditionalService(rucak);
		AdditionalService vecera = new AdditionalService("Vecera");
		additionalServiceData.addAdditionalService(vecera);
		AdditionalService bazen = new AdditionalService("Bazen");
		additionalServiceData.addAdditionalService(bazen);
		AdditionalService spaCentar = new AdditionalService("Spa Centar");
		additionalServiceData.addAdditionalService(spaCentar);
		
		additionalServiceData.removeAdditionalService(spaCentar.getId());
		
		EnumMap<RoomType, Double> ceneZaSobe = new EnumMap<>(RoomType.class);
		ceneZaSobe.put(RoomType.ONE_PERSON_ROOM_ONE_SINGLE_BED, 2500.0);
		ceneZaSobe.put(RoomType.TWO_PERSON_ROOM_TWO_SINGLE_BEDS, 3500.0);
		ceneZaSobe.put(RoomType.TWO_PERSON_ROOM_ONE_DOUBLE_BED, 4000.0);
		ceneZaSobe.put(RoomType.THREE_PERSON_ROOM_ONE_DOUBLE_BED_ONE_SINGLE_BED, 5500.0);
		ceneZaSobe.put(RoomType.THREE_PERSON_ROOM_THREE_SINGLE_BEDS, 6000.0);
		
		HashMap<AdditionalService, Double> ceneZaDodatneUsluge = new HashMap<>();
		ceneZaDodatneUsluge.put(dorucak, 300.0);
		ceneZaDodatneUsluge.put(rucak, 400.0);
		ceneZaDodatneUsluge.put(vecera, 400.0);
		ceneZaDodatneUsluge.put(bazen, 6000.0);
		
		Pricelist cenovnik = new Pricelist(LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 13),
				ceneZaSobe, ceneZaDodatneUsluge);
		pricelistData.addPricelist(cenovnik);
		
		ceneZaDodatneUsluge.put(dorucak, 500.0);
				
		System.out.println("Tipovi soba koje su dostupne izmedju 01.08.2024. i 31.08.2024 : ");
		ArrayList<RoomType> availableRoomTypes1 = Available.betweenDates(LocalDate.of(2024, 8, 1), LocalDate.of(2024, 8, 31), reservationData);
		for (RoomType roomType : availableRoomTypes1) {
			System.out.println(roomType);
		}		
		System.out.println();
		
		ArrayList<AdditionalService> uslugeZaMilicu = new ArrayList<>();
		uslugeZaMilicu.add(dorucak);
		uslugeZaMilicu.add(vecera);		
		Reservation reservation1 = new Reservation(mlicaMilic, LocalDate.of(2024, 8, 13), LocalDate.of(2024, 8, 23),
										RoomType.THREE_PERSON_ROOM_ONE_DOUBLE_BED_ONE_SINGLE_BED, uslugeZaMilicu, pricelistData);
		reservationData.addReservation(reservation1);
		
		System.out.println("Tipovi soba koje su dostupne izmedju 01.06.2024. i 30.06.2024 : ");
		ArrayList<RoomType> availableRoomTypes2 = Available.betweenDates(LocalDate.of(2024, 6, 1), LocalDate.of(2024, 6, 30), reservationData);
		for (RoomType roomType : availableRoomTypes2) {
			System.out.println(roomType);
		}		
		System.out.println();
		
		ArrayList<AdditionalService> uslugeZaAnu = new ArrayList<>();
		Reservation reservation2 = new Reservation(anaAnic, LocalDate.of(2024, 6, 6), LocalDate.of(2024, 6, 12),
										RoomType.TWO_PERSON_ROOM_TWO_SINGLE_BEDS, uslugeZaAnu, pricelistData);
		reservationData.addReservation(reservation2);
		
		System.out.println("Rezervacije za Mlicu Milic: ");
		for (String id : reservationData.getReservationMap().keySet()) {
			if (reservationData.getReservation(id).getGuest().equals(mlicaMilic)) {
				System.out.println(reservationData.getReservation(id));
				
			}
		}
		System.out.println();	
	}
}
