package viewReceptionist;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import entitiesClasses.AdditionalService;
import entitiesClasses.Reservation;
import entitiesEnums.ReservationStatus;
import entitiesManager.ReservationManager;
import viewBase.BasePanel;

public class CheckInPanel extends BasePanel {

	private static final long serialVersionUID = 1L;
	JButton btnCheckIn;
	DefaultTableModel tableModel;

	/**
	 * Create the panel.
	 */
	public CheckInPanel() {
		setLayout(new BorderLayout(0, 0));
        
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(new Color(223, 253, 234));
        btnPanel.setOpaque(true);
        btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 15));
        add(btnPanel, BorderLayout.NORTH);

        btnCheckIn = new JButton("Check in");
        btnCheckIn.setPreferredSize(new Dimension(140, 30));
        btnCheckIn.setEnabled(false);
        btnCheckIn.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.GRAY));
        btnPanel.add(btnCheckIn);
        
        String[] columnNames = {"ID", "Start Date", "End Date", "Guest", "Room Type", "Additional Services" , "Price", "Status"};
        int[] columnWidths = {1, 40, 40, 40, 240, 200, 50, 60};
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
				btnCheckIn.setEnabled(true);
			}
		});

        btnCheckIn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String id = (String) tableModel.getValueAt(selectedRow, 0);
                CheckInDialog checkInDialog = new CheckInDialog(id);
                checkInDialog.setModal(true);
                checkInDialog.setVisible(true);
                populateTable();
                btnCheckIn.setEnabled(false);
            }
        });

	}
	
    void populateTable() {
    	tableModel.setRowCount(0);
        for (String id : ReservationManager.getInstance().getReservationData().keySet()) {
        	Reservation reservation = ReservationManager.getInstance().getReservation(id);
        	if (reservation.getStatus().equals(ReservationStatus.APPROVED) 
        			&& reservation.getRoom() == null 
        			&& reservation.getStartDate().isEqual(LocalDate.now())) {

    		    String additionalServices;
    			StringBuilder sb = new StringBuilder();
    			if (!reservation.getAdditionalServicesWanted().isEmpty()) {
    					for (AdditionalService additionalService : reservation.getAdditionalServicesWanted()) {
    						sb.append(additionalService.getName() + ", ");
    					}
    					additionalServices = sb.toString();
    			} else {
    				additionalServices = "X";
    			}
	            Object[] rowData = {
	                reservation.getId(),
	                reservation.getStartDate(),
	                reservation.getEndDate(),
	                reservation.getGuest().getUsername(),
	                reservation.getRoomType(),
	                additionalServices,
	                reservation.getPrice(),
	                reservation.getStatus()
	            };
				if (reservation.getRoom() != null) {
					rowData[rowData.length-1] = reservation.getRoom().getRoomNumber();
				}
	            tableModel.addRow(rowData);
        	}
        }
        
    }

}
