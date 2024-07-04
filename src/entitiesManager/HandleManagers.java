package entitiesManager;

import java.io.PrintWriter;

public class HandleManagers {
	
	public static void writeData() {
		System.out.println("Writing data to CSV files...");
		AdditionalServiceManager.getInstance().writeToCSV();
		GuestManager.getInstance().writeToCSV();
		EmployeeManager.getInstance().writeToCSV();
		PricelistManager.getInstance().writeToCSV();
		RoomManager.getInstance().writeToCSV();
		ReservationManager.getInstance().writeToCSV();
		RoomFacilityManager.getInstance().writeToCSV();
    }
	
	 public static void clearData() {
	    try {
	    	System.out.println("Clearing data...");
	        new PrintWriter("storage/additionalServiceData.csv").close();
	        new PrintWriter("storage/guestData.csv").close();
	        new PrintWriter("storage/employeeData.csv").close();
	        new PrintWriter("storage/pricelistData.csv").close();
	        new PrintWriter("storage/roomData.csv").close();
	        new PrintWriter("storage/reservationData.csv").close();
	        new PrintWriter("storage/roomFacilityData.csv").close();
	    } catch (Exception e) {
	        System.err.println("Error clearing files: " + e.getMessage());
	    }
	}
	 
	 public static void readData() {
        System.out.println("Reading data...");
        AdditionalServiceManager.getInstance().readAdditionalServiceData();
        GuestManager.getInstance().readGuest();
        EmployeeManager.getInstance().readEmployeeData();
        PricelistManager.getInstance().readPricelistData();
        ReservationManager.getInstance().readAllReservations();
        RoomFacilityManager.getInstance().readRoomFacilityData();
        RoomManager.getInstance().readRoomData();
     }
}
