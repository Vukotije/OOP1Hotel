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

import entitiesClasses.Guest;
import entitiesManager.GuestManager;
import viewBase.BasePanel;

public class AdminGuestPanel extends BasePanel {

	private static final long serialVersionUID = 1L;
	JButton btnAddGuest;
	JButton btnEditGuest;
    JButton btnDeleteGuest;
	DefaultTableModel tableModel;

	/**
	 * Create the panel.
	 */
	public AdminGuestPanel() {
		setLayout(new BorderLayout(0, 0));
        
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(new Color(255, 217, 217));
        btnPanel.setOpaque(true);
        btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 15));
        add(btnPanel, BorderLayout.NORTH);

        btnAddGuest = new JButton("ADD GUEST");
        btnAddGuest.setPreferredSize(new Dimension(140, 30));
        btnAddGuest.setEnabled(true);
        btnAddGuest.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.GRAY));
        btnPanel.add(btnAddGuest);
        
        btnEditGuest = new JButton("EDIT GUEST");
        btnEditGuest.setPreferredSize(new Dimension(140, 30));
        btnEditGuest.setEnabled(false);
        btnEditGuest.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.GRAY));
        btnPanel.add(btnEditGuest);
        
        btnDeleteGuest = new JButton("DELETE GUEST");
        btnDeleteGuest.setPreferredSize(new Dimension(140, 30));
        btnDeleteGuest.setEnabled(false);
        btnDeleteGuest.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.GRAY));
        btnPanel.add(btnDeleteGuest);
        
                
        String[] columnNames = {"ID", "Name", "Surname", "Gender", "Date of Birth", "Phone", "Address", "Username"};
        int[] columnWidths = {1, 40, 40, 20, 40, 50, 60, 40};
        JTable table = new JTable();
        tableModel = setupTableModal(table, columnNames, columnWidths, new Color(220,20,60));
        
        populateTable();

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        
        JPanel borderPanel = new JPanel(new BorderLayout());
		borderPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		borderPanel.add(new JScrollPane(table), BorderLayout.CENTER);
		add(borderPanel, BorderLayout.CENTER);

		btnAddGuest.addActionListener(e -> {
			AdminGuestDialog dialog = new AdminGuestDialog(new Color(255, 221, 221), null); 
			dialog.setModal(true);
			dialog.setVisible(true);
			tableModel.setRowCount(0);                
            populateTable();
        });

		btnEditGuest.addActionListener(e -> {
			int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String id = (String) tableModel.getValueAt(selectedRow, 0);
				AdminGuestDialog dialog = new AdminGuestDialog(new Color(255, 221, 221), id); 
				dialog.setModal(true);
				dialog.setVisible(true);
				tableModel.setRowCount(0); 
	            btnEditGuest.setEnabled(false); 
	            btnDeleteGuest.setEnabled(false);
	            populateTable();
            }
        });

		btnDeleteGuest.addActionListener(e -> {
			int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
              String id = (String) tableModel.getValueAt(selectedRow, 0);
              int answer = JOptionPane.showConfirmDialog(null,
              		"Are you sure you want to delete this guests account?",
                      "Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
              if (answer == JOptionPane.YES_OPTION) {
                  GuestManager.getInstance().removeGuest(id);
                  JOptionPane.showMessageDialog(this, "Guests account deleted successfully.");
                  btnDeleteGuest.setEnabled(false);
                  btnEditGuest.setEnabled(false); 
                  populateTable();
            }
            }
        });
		
		table.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
				btnEditGuest.setEnabled(true);
				btnDeleteGuest.setEnabled(true);
			}
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
