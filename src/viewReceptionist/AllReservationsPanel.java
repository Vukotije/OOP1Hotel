package viewReceptionist;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import entitiesClasses.AdditionalService;
import entitiesClasses.Reservation;
import entitiesClasses.Room;
import entitiesEnums.ReservationStatus;
import entitiesEnums.RoomType;
import entitiesManager.ReservationManager;
import viewBase.BasePanel;

public class AllReservationsPanel extends BasePanel {

	private static final long serialVersionUID = 1L;
	JButton btnApprove;
	JButton btnDeny;
	JButton btnFilter;
	JButton btnRefreshe;
	DefaultTableModel tableModel;

	/**
	 * Create the panel.
	 */
	public AllReservationsPanel() {
		setLayout(new BorderLayout(0, 0));
        
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(new Color(223, 253, 234));
        btnPanel.setOpaque(true);
        btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 15));
        add(btnPanel, BorderLayout.NORTH);

        String[] columnNames = {"ID", "Start Date", "End Date", "Guest", "Room Type", "Price", "Status", "Additional Services"};
        int[] columnWidths = {1, 40, 40, 40, 240, 50, 60, 200};
        JTable table = new JTable();
        tableModel = setupTableModal(table, columnNames, columnWidths, new Color(102, 187, 106));
        
        populateTable();

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        
        JPanel borderPanel = new JPanel(new BorderLayout());
		borderPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		borderPanel.add(new JScrollPane(table), BorderLayout.CENTER);
		add(borderPanel, BorderLayout.CENTER);
		

        btnApprove = new JButton("APPROVE");
        btnApprove.setPreferredSize(new Dimension(140, 30));
        btnApprove.setEnabled(false);
        btnApprove.setFocusable(false);
        btnApprove.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.GRAY));
        btnPanel.add(btnApprove);

        btnDeny = new JButton("DENY");
        btnDeny.setPreferredSize(new Dimension(140, 30));
        btnDeny.setEnabled(false);
        btnDeny.setFocusable(false);
        btnDeny.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.GRAY));
        btnPanel.add(btnDeny);
        
        btnFilter = new JButton("Filter");
        btnFilter.setPreferredSize(new Dimension(140, 30));
        btnFilter.setFocusable(false);
        btnFilter.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.GRAY));
        btnPanel.add(btnFilter);
        

        btnRefreshe = new JButton("Refresh");
        btnRefreshe.setPreferredSize(new Dimension(140, 30));
        btnRefreshe.setFocusable(false);
        btnRefreshe.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.GRAY));
        btnPanel.add(btnRefreshe);
        

		table.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = table.getSelectedRow();
			if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1
					 && tableModel.getValueAt(selectedRow, 6).toString().trim().
					 equals(ReservationStatus.PENDING.toString().trim())) {
				btnApprove.setEnabled(true);
				btnDeny.setEnabled(true);
			}
		});


        btnApprove.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String id = (String) tableModel.getValueAt(selectedRow, 0);
                Boolean result = ReservationManager.getInstance().approveReservation(id);
				if (!result) {
					JOptionPane.showMessageDialog(null, "Reservation could not be approved, it is automaticaly denied", "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "Reservation succssfully approved!", "Success", JOptionPane.INFORMATION_MESSAGE);
				}
                populateTable();
                btnApprove.setEnabled(false);
                btnDeny.setEnabled(false);
            }
        });

        btnDeny.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String id = (String) tableModel.getValueAt(selectedRow, 0);
                ReservationManager.getInstance().denyReservation(id);
                int answer = JOptionPane.showConfirmDialog(null,
                		"Are you sure you want to deny this reservation?",
                		"Deny Reservation", JOptionPane.YES_NO_OPTION);
                if (answer==JOptionPane.YES_OPTION) {
                	JOptionPane.showMessageDialog(null, "Reservation denied successfully!", "Deny Reservation", JOptionPane.WARNING_MESSAGE);
                    populateTable();
                    btnApprove.setEnabled(false);
                    btnDeny.setEnabled(false);
                }                
            }
        });
        
		btnFilter.addActionListener(e -> {
			FilterReservationsDialog filterReservationsDialog = new FilterReservationsDialog(this);
			filterReservationsDialog.setModal(true);
			filterReservationsDialog.setVisible(true); 
		});
		
		btnRefreshe.addActionListener(e -> {
			populateTable();
		});

	}

    void populateTable() {
        tableModel.setRowCount(0);
        for (String id : ReservationManager.getInstance().getReservationData().keySet()) {
            Reservation reservation = ReservationManager.getInstance().getReservation(id);

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
                reservation.getPrice(),
                reservation.getStatus(),
                additionalServices
            };
            tableModel.addRow(rowData);;
        }
    }

    void filterTable(Set<AdditionalService> additionalServicesSelected, Set<Room> roomsSelected,
            Set<RoomType> roomTypeSelected, double bottomPriceF, double topPriceF) {

		tableModel.setRowCount(0);
		if (additionalServicesSelected.isEmpty() && roomsSelected.isEmpty() && roomTypeSelected.isEmpty()
		       && bottomPriceF == 0 && topPriceF == Double.MAX_VALUE) {
		   populateTable();
		   return;
		}
		
		ReservationManager reservationManager = ReservationManager.getInstance();
		Set<String> filteredReservationsID = new HashSet<>(reservationManager.getReservationData().keySet());
		
		for (String id : reservationManager.getReservationData().keySet()) {
		   Reservation reservation = reservationManager.getReservation(id);
		   
		   if (!additionalServicesSelected.isEmpty() && !reservation.getAdditionalServicesWanted().containsAll(additionalServicesSelected)) {
		       filteredReservationsID.remove(id);
		       continue;
		   }
		   
		   if (!roomsSelected.isEmpty() && (reservation.getRoom() == null || !roomsSelected.contains(reservation.getRoom()))) {
		       filteredReservationsID.remove(id);
		       continue;
		   }
		   
		   if (!roomTypeSelected.isEmpty() && !roomTypeSelected.contains(reservation.getRoomType())) {
		       filteredReservationsID.remove(id);
		       continue;
		   }
		   
		   if (reservation.getPrice() < bottomPriceF || reservation.getPrice() > topPriceF) {
		       filteredReservationsID.remove(id);
		       continue;
		   }
		}
		
		for (String id : filteredReservationsID) {
		   Reservation reservation = reservationManager.getReservation(id);
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
		       reservation.getPrice(),
		       reservation.getStatus(),
		       additionalServices
		   };
		   tableModel.addRow(rowData);
		}
		}

}
