package reports;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entitiesClasses.Employee;
import entitiesClasses.Maid;
import entitiesClasses.Reservation;
import entitiesClasses.Room;
import entitiesEnums.ReservationStatus;
import entitiesEnums.RoomType;
import entitiesManager.EmployeeManager;
import entitiesManager.ReservationManager;

public class Report {
	
	
//==============================================================================================
//											REPORTS
//==============================================================================================

	
	public static int[] getRevenueAndWisitsForRoom(LocalDate startDate, LocalDate endDate, Room room) {
		int days = 0;
		int revenue = 0;
		ReservationManager reservationManager = ReservationManager.getInstance();
		for (String id : reservationManager.getReservationData().keySet()) {
			Reservation reservation = reservationManager.getReservation(id);
			if (reservation.getRoom() != null){
				if (reservation.getRoom().equals(room)) {
					for (LocalDate movingDate = startDate; !movingDate.isAfter(endDate); movingDate = movingDate.plusDays(1)) {
						if (reservation.getStartDate().isBefore(movingDate)
							&& reservation.getEndDate().isAfter(movingDate)) {
								days++;
									if (reservation.getStartDate().equals(movingDate)) {
										revenue += reservation.getPrice();
							}
						}
					}
				}
			}
		}
		return new int[] {days, revenue};
}
	
	public static int getRevenue(LocalDate startDate, LocalDate endDate) {
		int revenue = 0;
		ReservationManager reservationManager = ReservationManager.getInstance();
		for (String id : reservationManager.getReservationData().keySet()) {
			Reservation reservation = reservationManager.getReservation(id);
			if (reservation.getStartDate().isAfter(startDate) 
					&& reservation.getEndDate().isBefore(endDate)
					&& !reservation.getStatus().equals(ReservationStatus.DENIED)) {
				revenue += reservation.getPrice();
			}
		}
		return revenue;
	}
	
	public static int getExpenses(LocalDate startDate, LocalDate endDate) {
		int expenses = 0;
		EmployeeManager employeeManager = EmployeeManager.getInstance();
		int months = countFirstOfTheMonthDates(startDate, endDate);
		for (String id : employeeManager.getEmployeeData().keySet()) {
			expenses += employeeManager.getEmployee(id).getSalary() * months;
		}
		return expenses;
	}
	
	public static int countFirstOfTheMonthDates(LocalDate startDate, LocalDate endDate) {
	    int count = 0;
	    for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
	        if (date.getDayOfMonth() == 1) {
	            count++;
	        }
	    }
	    return count;
	}
	
	public static EnumMap<ReservationStatus, Integer> reservationReport(LocalDate startDate, LocalDate endDate){
		EnumMap<ReservationStatus, Integer> report = new EnumMap<>(ReservationStatus.class);
		report.put(ReservationStatus.PENDING, 0);
		report.put(ReservationStatus.APPROVED, 0);
		report.put(ReservationStatus.CANCELLED, 0);
		report.put(ReservationStatus.DENIED, 0);
		
		ReservationManager reservationManager = ReservationManager.getInstance();
		for (String id : reservationManager.getReservationData().keySet()) {
			Reservation reservation = reservationManager.getReservation(id);
			if (reservation.getCreationDate().isAfter(startDate) && reservation.getCreationDate().isBefore(endDate)) {
				ReservationStatus status = reservation.getStatus();
				report.put(status, report.get(status) + 1);
			}
		}
		return report;
	}
	
	public static Map<String, List<Double>> getMonthlyRevenueByRoomType(int months) {
        Map<String, List<Double>> revenueData = new HashMap<>();
        revenueData.put("Total", new ArrayList<>());
        for (RoomType type : RoomType.values()) {
            revenueData.put(type.toString(), new ArrayList<>());
        }

        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusMonths(months - 1).withDayOfMonth(1);

        while (!startDate.isAfter(endDate)) {
            double totalProfit = 0;
            for (RoomType type : RoomType.values()) {
                double revenue = calculateRevenueForRoomType(type, startDate, startDate.plusMonths(1));
                revenueData.get(type.toString()).add(revenue);
                totalProfit += revenue;
            }
            revenueData.get("Total").add(totalProfit);
            startDate = startDate.plusMonths(1);
        }

        return revenueData;
    }

    private static double calculateRevenueForRoomType(RoomType type, LocalDate start, LocalDate end) {
        double revenue = 0;
        ReservationManager reservationManager = ReservationManager.getInstance();
        for (Reservation reservation : reservationManager.getReservationData().values()) {
            if (reservation.getRoomType() == type &&
                !reservation.getStatus().equals(ReservationStatus.DENIED) &&
                (reservation.getStartDate().isAfter(start) &&
                reservation.getStartDate().isBefore(end))) {
                revenue += reservation.getPrice();
            }
        }
        return revenue;
    }

    public static Map<String, Integer> getMaidDistribution(int days) {
        Map<String, Integer> distribution = new HashMap<>();
        LocalDate startDate = LocalDate.now().minusDays(days);

        for (Employee employee : EmployeeManager.getInstance().getEmployeeData().values()) {
            if (employee instanceof Maid) {
                Maid maid = (Maid) employee;
                int count = (int) maid.getRoomsAssignedToClean().stream()
                    .filter(date -> !date.isBefore(startDate))
                    .count();
                distribution.put(maid.getName(), count);
            }
        }

        return distribution;
    }
	
	
}
