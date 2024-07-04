package viewReceptionist;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import entitiesClasses.Reservation;
import entitiesClasses.Room;
import entitiesClasses.RoomFacility;
import entitiesManager.RoomManager;
import viewBase.BasePanel;

public class RoomAvailabilityPanel extends BasePanel {

	private static final long serialVersionUID = 1L;
	DefaultTableModel tableModel;

	/**
	 * Create the panel.
	 */
	public RoomAvailabilityPanel() {
		setLayout(new BorderLayout(0, 0));
                
        String[] columnNames = {"ID", "Room Number", "Room Type", "Room Status", "Start Date", "End Date", "Facilities"};
        int[] columnWidths = {1, 40, 250, 40, 40, 40, 200};
        JTable table = new JTable();
        tableModel = setupTableModal(table, columnNames, columnWidths, new Color(102, 187, 106));
        populateTable();
        
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        
        JPanel borderPanel = new JPanel(new BorderLayout());
		borderPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		borderPanel.add(new JScrollPane(table), BorderLayout.CENTER);
		add(borderPanel, BorderLayout.CENTER);
        
        
	}
	
	 void populateTable() {
		 tableModel.setRowCount(0);
		 for (String key : RoomManager.getInstance().getRoomData().keySet()) {
			 Room room = RoomManager.getInstance().getRoom(key);
			 Reservation reservation = room.getReservation(room.getReservationId());
			 String startDate;
			 String endDate;
			 if (reservation != null) {
				 startDate = reservation.getStartDate().toString();
				 endDate = reservation.getEndDate().toString();
				} else {
					startDate = "X";
					endDate = "X";
				}
			 String facilities;
	        	StringBuilder sb = new StringBuilder();
	        	if (!room.getRoomFacilitiesAvailable().isEmpty()) {
					for (RoomFacility facility : room.getRoomFacilitiesAvailable()) {
						sb.append(facility.getName() + ", ");
					}
					facilities = sb.toString();
	        	} else {
	        		facilities = "X";
	        	}
			 Object[] rowData = {
				room.getId(),
                room.getRoomNumber(),
                room.getRoomType().toString(),
                room.getRoomStatus().toString(),
                startDate,
                endDate,
                facilities
			 };
			 tableModel.addRow(rowData);
			 }    
		 }

}
