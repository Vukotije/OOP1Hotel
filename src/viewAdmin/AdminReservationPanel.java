package viewAdmin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import entitiesClasses.AdditionalService;
import entitiesClasses.Reservation;
import entitiesManager.ReservationManager;
import viewBase.BasePanel;

public class AdminReservationPanel extends BasePanel {

	private static final long serialVersionUID = 1L;
	JButton btnAddReservation;
	JButton btnEditReservation;
    JButton btnDeleteReservation;
	DefaultTableModel tableModel;

	/**
	 * Create the panel.
	 */
	public AdminReservationPanel() {
		setLayout(new BorderLayout(0, 0));
        
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(new Color(255, 217, 217));
        btnPanel.setOpaque(true);
        btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 15));
        add(btnPanel, BorderLayout.NORTH);

        btnAddReservation = new JButton("ADD RESERVATION");
        btnAddReservation.setPreferredSize(new Dimension(140, 30));
        btnAddReservation.setEnabled(true);
        btnAddReservation.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.GRAY));
        btnPanel.add(btnAddReservation);
        
        btnEditReservation = new JButton("EDIT RESERVATION");
        btnEditReservation.setPreferredSize(new Dimension(140, 30));
        btnEditReservation.setEnabled(false);
        btnEditReservation.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.GRAY));
        btnPanel.add(btnEditReservation);
        
        btnDeleteReservation = new JButton("DELETE RESERVATION");
        btnDeleteReservation.setPreferredSize(new Dimension(140, 30));
        btnDeleteReservation.setEnabled(false);
        btnDeleteReservation.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.GRAY));
        btnPanel.add(btnDeleteReservation);
                
        String[] columnNames = {"ID", "Start Date", "End Date", "Guest", "Room Type", "Price", "Status", "Additional Services"};
        int[] columnWidths = {1, 40, 40, 40, 240, 50, 60, 200};
        JTable table = new JTable();
        tableModel = setupTableModal(table, columnNames, columnWidths, new Color(220,20,60));
        populateTable();

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        
        JPanel borderPanel = new JPanel(new BorderLayout());
		borderPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		borderPanel.add(new JScrollPane(table), BorderLayout.CENTER);
		add(borderPanel, BorderLayout.CENTER);

		btnAddReservation.addActionListener(e -> {
			AdminReservationDialog dialog = new AdminReservationDialog(null, null, new Color(255, 221, 221)); 
			dialog.setModal(true);
			dialog.setVisible(true);                
            populateTable();
        });

		btnEditReservation.addActionListener(e -> {
			int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String id = (String) tableModel.getValueAt(selectedRow, 0);
				AdminReservationDialog dialog = new AdminReservationDialog(null, id, new Color(255, 221, 221)); 
				dialog.setModal(true);
				dialog.setVisible(true);
	            btnEditReservation.setEnabled(false); 
	            btnDeleteReservation.setEnabled(false);
	            populateTable();
            }
        });

		btnDeleteReservation.addActionListener(e -> {
			int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
              String id = (String) tableModel.getValueAt(selectedRow, 0);
              int answer = JOptionPane.showConfirmDialog(null,
              		"Are you sure you want to delete this reservation?",
                      "Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
              if (answer == JOptionPane.YES_OPTION) {
                  ReservationManager.getInstance().removeReservation(id);
                  JOptionPane.showMessageDialog(this, "Reservation deleted successfully.");
                  btnDeleteReservation.setEnabled(false);
                  btnEditReservation.setEnabled(false); 
                  populateTable();
            }
            }
        });
		
		table.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
				btnEditReservation.setEnabled(true);
				btnDeleteReservation.setEnabled(true);
			}
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
}
