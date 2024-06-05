package managerClasses;

import java.io.PrintWriter;

public class HandleManagers {
	
	public static void writeData() {
		AdditionalServiceManager.getInstance().writeToCSV();
		GuestManager.getInstance().writeToCSV();
		EmployeeManager.getInstance().writeToCSV();
		PricelistManager.getInstance().writeToCSV();
		RoomManager.getInstance().writeToCSV();
		ReservationManager.getInstance().writeToCSV();
    }
	
	// only used in testing
	 public static void clearData() {
	    try {
	        new PrintWriter("storage/additionalServiceData.csv").close();
	        new PrintWriter("storage/guestData.csv").close();
	        new PrintWriter("storage/employeeData.csv").close();
	        new PrintWriter("storage/pricelistData.csv").close();
	        new PrintWriter("storage/roomData.csv").close();
	        new PrintWriter("storage/reservationData.csv").close();
	    } catch (Exception e) {
	        System.err.println("Error clearing files: " + e.getMessage());
	    }
	}
}
