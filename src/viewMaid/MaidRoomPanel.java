package viewMaid;

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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import entitiesClasses.Maid;
import entitiesClasses.Room;
import entitiesClasses.RoomFacility;
import viewBase.BasePanel;

public class MaidRoomPanel extends BasePanel {
	Maid maid;

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public MaidRoomPanel(Maid maid) {
		this.maid = maid;
        setLayout(new BorderLayout(0, 0));
        
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(new Color(204, 255, 255));
        btnPanel.setOpaque(true);
        btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 15));
        add(btnPanel, BorderLayout.NORTH);
        
        JButton btnCleanRoom = new JButton("ROOM CLEANED");
        btnCleanRoom.setPreferredSize(new Dimension(140, 30));
        btnCleanRoom.setEnabled(false);
        btnCleanRoom.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.GRAY));
        btnPanel.add(btnCleanRoom);

        String[] columnNames = {"ID", "Room Number", "Room Type", "Room Status", "Room Facilities"};	
        int[] columnWidths = {150, 20, 240, 40, 120};	
        JTable table = new JTable();
        DefaultTableModel tableModel = setupTableModal(table, columnNames, columnWidths, new Color(0x2596be));
        populateTable(tableModel, maid);
        
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

		JPanel borderPanel = new JPanel(new BorderLayout());
		borderPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		borderPanel.add(new JScrollPane(table), BorderLayout.CENTER);
		add(borderPanel, BorderLayout.CENTER);

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                    btnCleanRoom.setEnabled(true);
                }
            }
        });

        btnCleanRoom.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String id = (String) tableModel.getValueAt(selectedRow, 0);
                maid.cleanRoom(id);
                JOptionPane.showMessageDialog(this, "Room cleaned successfully!");
                tableModel.setRowCount(0);
                populateTable(tableModel, maid);
                btnCleanRoom.setEnabled(false);
            }
        });
	}
    
    private void populateTable(DefaultTableModel tableModel, Maid maid) {
        for (Room room : maid.getRoomsLeftToClean()) {
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
                room.getRoomType(),
                room.getRoomStatus(),
                facilities
            };
            tableModel.addRow(rowData);
        }
    
	}

}
