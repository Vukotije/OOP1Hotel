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

import entitiesClasses.Room;
import entitiesClasses.RoomFacility;
import entitiesManager.RoomManager;
import viewBase.BasePanel;

public class AdminRoomPanel extends BasePanel {

	private static final long serialVersionUID = 1L;
	JButton btnAddRoom;
	JButton btnEditRoom;
    JButton btnDeleteRoom;
	DefaultTableModel tableModel;

	/**
	 * Create the panel.
	 */
	public AdminRoomPanel() {
		setLayout(new BorderLayout(0, 0));
        
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(new Color(255, 217, 217));
        btnPanel.setOpaque(true);
        btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 15));
        add(btnPanel, BorderLayout.NORTH);

        btnAddRoom = new JButton("ADD ROOM");
        btnAddRoom.setPreferredSize(new Dimension(140, 30));
        btnAddRoom.setEnabled(true);
        btnAddRoom.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.GRAY));
        btnPanel.add(btnAddRoom);
        
        btnEditRoom = new JButton("EDIT ROOM");
        btnEditRoom.setPreferredSize(new Dimension(140, 30));
        btnEditRoom.setEnabled(false);
        btnEditRoom.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.GRAY));
        btnPanel.add(btnEditRoom);
        
        btnDeleteRoom = new JButton("DELETE ROOM");
        btnDeleteRoom.setPreferredSize(new Dimension(140, 30));
        btnDeleteRoom.setEnabled(false);
        btnDeleteRoom.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.GRAY));
        btnPanel.add(btnDeleteRoom);
                
        String[] columnNames = {"ID", "Room Number", "Room Type", "Room Status","Facilities"};
        int[] columnWidths = {1, 40, 250, 40, 200};
        JTable table = new JTable();
        tableModel = setupTableModal(table, columnNames, columnWidths, new Color(220,20,60));
        populateTable();

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        
        JPanel borderPanel = new JPanel(new BorderLayout());
		borderPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		borderPanel.add(new JScrollPane(table), BorderLayout.CENTER);
		add(borderPanel, BorderLayout.CENTER);

		btnAddRoom.addActionListener(e -> {
			AdminRoomDialog dialog = new AdminRoomDialog(null); 
			dialog.setModal(true);
			dialog.setVisible(true);
			tableModel.setRowCount(0);                
            populateTable();
        });

		btnEditRoom.addActionListener(e -> {
			int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String id = (String) tableModel.getValueAt(selectedRow, 0);
				AdminRoomDialog dialog = new AdminRoomDialog(id); 
				dialog.setModal(true);
				dialog.setVisible(true);
				tableModel.setRowCount(0); 
	            btnEditRoom.setEnabled(false); 
	            btnDeleteRoom.setEnabled(false);
	            populateTable();
            }
        });

		btnDeleteRoom.addActionListener(e -> {
			int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
              String id = (String) tableModel.getValueAt(selectedRow, 0);
              int answer = JOptionPane.showConfirmDialog(null,
              		"Are you sure you want to delete this room?",
                      "Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
              if (answer == JOptionPane.YES_OPTION) {
                  RoomManager.getInstance().removeRoom(id);
                  JOptionPane.showMessageDialog(this, "Room deleted successfully.");
                  btnDeleteRoom.setEnabled(false);
                  btnEditRoom.setEnabled(false); 
                  populateTable();
            }
            }
        });
		
		table.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
				btnEditRoom.setEnabled(true);
				btnDeleteRoom.setEnabled(true);
			}
		});
	}
	
	void populateTable() {
		 tableModel.setRowCount(0);
		 for (String key : RoomManager.getInstance().getRoomData().keySet()) {
			 Room room = RoomManager.getInstance().getRoom(key);
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
               facilities
			 };
			 tableModel.addRow(rowData);
			 }    
		 }

}
