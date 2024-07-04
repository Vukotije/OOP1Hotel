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

import entitiesClasses.RoomFacility;
import entitiesManager.RoomFacilityManager;
import viewBase.BasePanel;

public class AdminRoomFacilityPanel extends BasePanel {

	private static final long serialVersionUID = 1L;
	JButton btnAddRoomFacility;
	JButton btnEditRoomFacility;
    JButton btnDeleteRoomFacility;
	DefaultTableModel tableModel;

	/**
	 * Create the panel.
	 */
	public AdminRoomFacilityPanel() {
		setLayout(new BorderLayout(0, 0));
        
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(new Color(255, 217, 217));
        btnPanel.setOpaque(true);
        btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 15));
        add(btnPanel, BorderLayout.NORTH);

        btnAddRoomFacility = new JButton("ADD ROOM FACILITY");
        btnAddRoomFacility.setPreferredSize(new Dimension(140, 30));
        btnAddRoomFacility.setEnabled(true);
        btnAddRoomFacility.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.GRAY));
        btnPanel.add(btnAddRoomFacility);
        
        btnEditRoomFacility = new JButton("EDIT ROOM FACILITY");
        btnEditRoomFacility.setPreferredSize(new Dimension(140, 30));
        btnEditRoomFacility.setEnabled(false);
        btnEditRoomFacility.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.GRAY));
        btnPanel.add(btnEditRoomFacility);
        
        btnDeleteRoomFacility = new JButton("DELETE ROOM FACILITY");
        btnDeleteRoomFacility.setPreferredSize(new Dimension(140, 30));
        btnDeleteRoomFacility.setEnabled(false);
        btnDeleteRoomFacility.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.GRAY));
        btnPanel.add(btnDeleteRoomFacility);
        
                
        String[] columnNames = {"ID", "Name"};
        int[] columnWidths = {1, 40};
        JTable table = new JTable();
        tableModel = setupTableModal(table, columnNames, columnWidths, new Color(220,20,60));
        
        populateTable();

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        
        JPanel borderPanel = new JPanel(new BorderLayout());
		borderPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		borderPanel.add(new JScrollPane(table), BorderLayout.CENTER);
		add(borderPanel, BorderLayout.CENTER);

		btnAddRoomFacility.addActionListener(e -> {
			AdminRoomFacilityDialog dialog = new AdminRoomFacilityDialog(null); 
			dialog.setModal(true);
			dialog.setVisible(true);               
            populateTable();
        });

		btnEditRoomFacility.addActionListener(e -> {
			int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
            	String id = (String) tableModel.getValueAt(selectedRow, 0);
            	AdminRoomFacilityDialog dialog = new AdminRoomFacilityDialog(id); 
				dialog.setModal(true);
				dialog.setVisible(true);
	            btnEditRoomFacility.setEnabled(false); 
	            btnDeleteRoomFacility.setEnabled(false);
	            populateTable();
            }
        });

		btnDeleteRoomFacility.addActionListener(e -> {
			int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
              String id = (String) tableModel.getValueAt(selectedRow, 0);
              int answer = JOptionPane.showConfirmDialog(null,
              		"Are you sure you want to delete this room facility?",
                      "Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
              if (answer == JOptionPane.YES_OPTION) {
                  RoomFacilityManager.getInstance().deleteRoomFacility(id);
                  JOptionPane.showMessageDialog(this, "RoomFacilitys deleted successfully.");
                  btnDeleteRoomFacility.setEnabled(false);
                  btnEditRoomFacility.setEnabled(false); 
                  populateTable();
            }
            }
        });
		
		table.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
				btnEditRoomFacility.setEnabled(true);
				btnDeleteRoomFacility.setEnabled(true);
			}
		});
	}
	
    void populateTable() {
        tableModel.setRowCount(0);
        for (String id : RoomFacilityManager.getInstance().getRoomFacilityData().keySet()) {
            RoomFacility rFacility = RoomFacilityManager.getInstance().getRoomFacility(id);
            Object[] rowData = {
                rFacility.getId(),
                rFacility.getName(),
            };				
            tableModel.addRow(rowData);;
        }
    }

}
