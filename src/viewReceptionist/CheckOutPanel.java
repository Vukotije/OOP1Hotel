package viewReceptionist;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import entitiesClasses.Reservation;
import entitiesClasses.Room;
import entitiesClasses.RoomFacility;
import entitiesEnums.RoomStatus;
import entitiesManager.ReservationManager;
import entitiesManager.RoomManager;
import viewBase.BasePanel;

public class CheckOutPanel extends BasePanel {

	private static final long serialVersionUID = 1L;
	JButton btnCheckOut;
	DefaultTableModel tableModel;

	/**
	 * Create the panel.
	 */
	public CheckOutPanel() {
		setLayout(new BorderLayout(0, 0));
        
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(new Color(223, 253, 234));
        btnPanel.setOpaque(true);
        btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 15));
        add(btnPanel, BorderLayout.NORTH);

        btnCheckOut = new JButton("Check out");
        btnCheckOut.setPreferredSize(new Dimension(140, 30));
        btnCheckOut.setEnabled(false);
        btnCheckOut.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.GRAY));
        btnPanel.add(btnCheckOut);
        
        String[] columnNames = {"ID", "Guest", "Room Number", "Room Type", "Room Facilities", "Room Status", "End Date"};
        int[] columnWidths = {150, 50, 20, 240, 200, 40, 40};
        JTable table = new JTable();
        tableModel = setupTableModal(table, columnNames, columnWidths, new Color(102, 187, 106));
        populateTable();
        
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        
        JPanel borderPanel = new JPanel(new BorderLayout());
		borderPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		borderPanel.add(new JScrollPane(table), BorderLayout.CENTER);
		add(borderPanel, BorderLayout.CENTER);
        
        table.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
				btnCheckOut.setEnabled(true);
			}
		});

        btnCheckOut.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String id = (String) tableModel.getValueAt(selectedRow, 0);
                RoomManager.getInstance().checkOut(id);
                JOptionPane.showMessageDialog(this, "Room checked out successfully!");
                tableModel.setRowCount(0);
                populateTable();
                btnCheckOut.setEnabled(false);
            }
        });
        
	}
	
	 void populateTable() {
		 tableModel.setRowCount(0);
		 for (String key : ReservationManager.getInstance().getReservationData().keySet()) {
			 Reservation reservation = ReservationManager.getInstance().getReservation(key);
			 if (reservation.getRoom() != null && reservation.getStartDate().isAfter(LocalDate.now().minusDays(1))) {
				 Room room = reservation.getRoom();
				 if (room.getRoomStatus().equals(RoomStatus.OCCUPIED)) {
				   String roomFacilities;
					StringBuilder sb = new StringBuilder();
					if (!room.getRoomFacilitiesAvailable().isEmpty()) {
							for (RoomFacility roomFacility : room.getRoomFacilitiesAvailable()) {
								sb.append(roomFacility.getName() + ", ");
							}
							roomFacilities = sb.toString();
					} else {
						roomFacilities = "X";
					}
					 Object[] rowData = {
						room.getId(),
		                reservation.getGuest().getUsername(),
		                room.getRoomNumber(),
		                room.getRoomType(),
		                roomFacilities,
		                room.getRoomStatus(),
		                reservation.getEndDate(),
	                };
	                tableModel.addRow(rowData);
					}    
			 }
            }
		 }

}
