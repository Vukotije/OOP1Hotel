package viewReceptionist;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import entitiesClasses.Guest;
import entitiesManager.GuestManager;
import viewAdmin.AdminGuestDialog;
import viewBase.BasePanel;

public class ReceptionistsGuestPanel extends BasePanel {

	private static final long serialVersionUID = 1L;
	JButton btnAddGuest;
	DefaultTableModel tableModel;

	/**
	 * Create the panel.
	 */
	public ReceptionistsGuestPanel() {
		setLayout(new BorderLayout(0, 0));
        
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(new Color(223, 253, 234));
        btnPanel.setOpaque(true);
        btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 15));
        add(btnPanel, BorderLayout.NORTH);

        btnAddGuest = new JButton("ADD GUEST");
        btnAddGuest.setPreferredSize(new Dimension(140, 30));
        btnAddGuest.setEnabled(true);
        btnAddGuest.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.GRAY));
        btnPanel.add(btnAddGuest);
                
        String[] columnNames = {"ID", "Name", "Surname", "Gender", "Date of Birth", "Phone", "Address", "Username"};
        int[] columnWidths = {180, 40, 40, 20, 40, 50, 60, 40};
        JTable table = new JTable();
        tableModel = setupTableModal(table, columnNames, columnWidths, new Color(102, 187, 106));
        
        populateTable();

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        
        JPanel borderPanel = new JPanel(new BorderLayout());
		borderPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		borderPanel.add(new JScrollPane(table), BorderLayout.CENTER);
		add(borderPanel, BorderLayout.CENTER);

		
		btnAddGuest.addActionListener(e -> {
			AdminGuestDialog dialog = new AdminGuestDialog(new Color(225, 255, 225), null); 
			dialog.setModal(true);
			dialog.setVisible(true);
			tableModel.setRowCount(0);                
            populateTable();
        });
	}
	
    void populateTable() {
        tableModel.setRowCount(0);
        for (String id : GuestManager.getInstance().getGuestData().keySet()) {
            Guest guest = GuestManager.getInstance().getGuest(id);
            Object[] rowData = {
                guest.getId(),
                guest.getName(),
                guest.getSurname(),
                guest.getGender().toString(),
                guest.getBirthdate().toString(),
                guest.getPhone(),
                guest.getAddress(),
                guest.getUsername()
            };				
            tableModel.addRow(rowData);;
        }
    }

}
