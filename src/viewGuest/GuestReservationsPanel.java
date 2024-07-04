package viewGuest;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import entitiesClasses.AdditionalService;
import entitiesClasses.Guest;
import entitiesClasses.Reservation;
import entitiesEnums.ReservationStatus;
import entitiesManager.ReservationManager;
import viewAdmin.AdminReservationDialog;
import viewBase.BasePanel;

public class GuestReservationsPanel extends BasePanel {

	private static final long serialVersionUID = 1L;
	private Guest guest;
	private JTextField txtPrice;
	private JTable table;

	/**
	 * Create the panel.
	 */
	public GuestReservationsPanel(Guest guest) {
		this.guest = guest;
        setLayout(new BorderLayout(0, 0));

        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(new Color(255, 213, 255));
        btnPanel.setOpaque(true);
        btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 15));
        add(btnPanel, BorderLayout.NORTH);

        JButton btnMakeReservation = new JButton("MAKE RESERVATION");
        btnMakeReservation.setFocusable(false);
        btnMakeReservation.setPreferredSize(new Dimension(160, 30));
        btnMakeReservation.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.GRAY));
        btnPanel.add(btnMakeReservation);

        JButton btnCancelReservation = new JButton("CANCEL RESERVATION");
        btnCancelReservation.setFocusable(false);
        btnCancelReservation.setPreferredSize(new Dimension(160, 30));
        btnCancelReservation.setEnabled(false);
        btnCancelReservation.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.GRAY));
        btnPanel.add(btnCancelReservation);

        String[] columnNames = {"ID", "Start Date", "End Date", "Status", "Additional Services", "Price"};
        int[] columnWidths = {1, 80, 80, 80, 200, 80};
        table = new JTable();
        DefaultTableModel tableModel = setupTableModal(table, columnNames, columnWidths, new Color(166, 120, 173));
        populateTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        
        JPanel borderPanel = new JPanel(new BorderLayout());
		borderPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		borderPanel.add(new JScrollPane(table), BorderLayout.CENTER);
		add(borderPanel, BorderLayout.CENTER);

	    JPanel pricePanel = new JPanel();
	    pricePanel.setBackground(new Color(255, 213, 255));
	    pricePanel.setOpaque(true);
	    add(pricePanel, BorderLayout.SOUTH);
	    pricePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
	    
	    JLabel priceLabel = new JLabel("Net spent:");
	    pricePanel.add(priceLabel);
	    
	    txtPrice = new JTextField();
	    txtPrice.setColumns(10);
	    txtPrice.setEditable(false);
	    txtPrice.setText(ReservationManager.getInstance().getNetPriceForGuest(guest) + " RSD");
	    pricePanel.add(txtPrice);
	    
        
		table.getSelectionModel().addListSelectionListener(e -> {
			int selectedRow = table.getSelectedRow();
			if (!e.getValueIsAdjusting() && selectedRow != -1
					&& (tableModel.getValueAt(selectedRow, 3).equals(ReservationStatus.APPROVED)
							|| tableModel.getValueAt(selectedRow, 3).equals(ReservationStatus.PENDING))) {
				btnCancelReservation.setEnabled(true);
			}
		});

        btnCancelReservation.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String id = (String) tableModel.getValueAt(selectedRow, 0);
                int answer = JOptionPane.showConfirmDialog(null,
                		"Are you sure you want to cancel this reservation? The refund is not possible!",
                        "Cancel Reservation", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (answer == JOptionPane.YES_OPTION) {
                    ReservationManager.getInstance().cancelReservation(id);
                    JOptionPane.showMessageDialog(this, "Reservation canceled successfully.");
                    btnCancelReservation.setEnabled(false);
                    tableModel.setRowCount(0);
                    populateTable(tableModel);
                }
            }
        });
        
		btnMakeReservation.addActionListener(e -> {
		   AdminReservationDialog dialog = new AdminReservationDialog(guest, null, new Color(255, 217, 255)); 
		   dialog.setModal(true);
		   dialog.setVisible(true);
		   tableModel.setRowCount(0);
		   populateTable(tableModel);
		   txtPrice.setText(ReservationManager.getInstance().getNetPriceForGuest(guest) + " RSD");
		});
    }

    private void populateTable(DefaultTableModel tableModel) {
        HashMap<String, Reservation> usersReservations = ReservationManager.getInstance().getUsersResrevations(guest);
        for (String id : usersReservations.keySet()) {
            Reservation reservation = usersReservations.get(id);
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
                    reservation.getStatus(),
                    additionalServices,
                    reservation.getPrice()
            };
            tableModel.addRow(rowData);
        }
    }

}


