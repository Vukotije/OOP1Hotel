package viewAdmin;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.HashSet;
import java.util.Set;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import entitiesClasses.Room;
import entitiesClasses.RoomFacility;
import entitiesEnums.RoomStatus;
import entitiesEnums.RoomType;
import entitiesManager.RoomFacilityManager;
import entitiesManager.RoomManager;
import viewBase.IDCheckBox;
import viewBase.JNumberTextField;

public class AdminRoomDialog extends JDialog {

    private static final long serialVersionUID = 1L;
    private String roomId;
    private Integer orinalRoomNumber = null;

    private JNumberTextField textRoomNumber;
    private JComboBox<String> comboBoxRoomType;
    private JComboBox<String> comboBoxRoomStatus;
    private JPanel roomFacilitiesPanel;

    public static void main(String[] args) {
        try {
            AdminRoomDialog dialog = new AdminRoomDialog(null);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AdminRoomDialog(String roomId) {
    	this.roomId = roomId;
        setResizable(false);
        setSize(450, 330);
        setLocationRelativeTo(null);
        setTitle("Add Room");
        getContentPane().setBackground(new Color(255, 221, 221));
        getContentPane().setLayout(null);
                
        // PHONE
        JLabel lblRoomNumber= new JLabel("Room Number:");
        lblRoomNumber.setBounds(18, 31, 114, 23);
        lblRoomNumber.setFont(new Font("Tahoma", Font.PLAIN, 12));
        getContentPane().add(lblRoomNumber);

        textRoomNumber = new JNumberTextField();
        textRoomNumber.setBounds(18, 54, 181, 19);
        getContentPane().add(textRoomNumber);
        textRoomNumber.setColumns(10);
        
        // ROOM TYPE
        JLabel lblRoomType = new JLabel("Room Type:");
        lblRoomType.setBounds(18, 175, 114, 23);
        lblRoomType.setFont(new Font("Tahoma", Font.PLAIN, 12));
        getContentPane().add(lblRoomType);

        comboBoxRoomType = new JComboBox<>();
		for (RoomType roomType : RoomType.values()) {
			comboBoxRoomType.addItem(roomType.toString());
		}
		comboBoxRoomType.setBounds(18, 195, 181, 23);
        getContentPane().add(comboBoxRoomType);

        
        // ROOM STATUS
        JLabel lblRoomStatus = new JLabel("Room Status:");
        lblRoomStatus.setBounds(18, 102, 114, 23);
        lblRoomStatus.setFont(new Font("Tahoma", Font.PLAIN, 12));
        getContentPane().add(lblRoomStatus);

        comboBoxRoomStatus = new JComboBox<>();
		for (RoomStatus roomStatus : RoomStatus.values()) {
			comboBoxRoomStatus.addItem(roomStatus.toString());
		}
		comboBoxRoomStatus.setBounds(18, 125, 181, 23);
        getContentPane().add(comboBoxRoomStatus);
        
        // ROOM FACILITIES
        JLabel lblRoomFacilities = new JLabel("Room Facilities:");
        lblRoomFacilities.setBounds(240, 31, 114, 23);
        lblRoomFacilities.setFont(new Font("Tahoma", Font.PLAIN, 12));
        getContentPane().add(lblRoomFacilities);

        roomFacilitiesPanel = new JPanel();
        roomFacilitiesPanel.setLayout(new BoxLayout(roomFacilitiesPanel, BoxLayout.Y_AXIS));
        for (String id : RoomFacilityManager.getInstance().getRoomFacilityData().keySet()) {
            String facilityName = RoomFacilityManager.getInstance().getRoomFacility(id).getName();
            IDCheckBox checkBox = new IDCheckBox(id, facilityName);
            checkBox.setFont(new Font("Tahoma", Font.PLAIN, 10));
            roomFacilitiesPanel.add(checkBox);
            roomFacilitiesPanel.add(Box.createVerticalStrut(5));
        }
        JScrollPane roomFacilityScrollPane = new JScrollPane(roomFacilitiesPanel);
        roomFacilityScrollPane.setBounds(240, 54, 170, 163);
        getContentPane().add(roomFacilityScrollPane);
                                        
        if (this.roomId != null) {
        	Room room = RoomManager.getInstance().getRoom(roomId);
        	setTitle("Edit Room");
        	textRoomNumber.setText(String.valueOf(room.getRoomNumber()));
        	comboBoxRoomType.setSelectedItem(room.getRoomType().toString());
        	comboBoxRoomStatus.setSelectedItem(room.getRoomStatus().toString());

			for (Component component : roomFacilitiesPanel.getComponents()) {
				if (component instanceof IDCheckBox) {
					IDCheckBox checkBox = (IDCheckBox) component;
					checkBox.setSelected(room.getRoomFacilitiesAvailable()
							.contains(RoomFacilityManager.getInstance().getRoomFacility(checkBox.getId())));
				}
			}        	
        	this.orinalRoomNumber = room.getRoomNumber();
        }
                
        // CANCEL BUTTON
        JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(e -> {
			dispose();
		});
        btnCancel.setFocusable(false);
        btnCancel.setBounds(18, 239, 100, 32);
        getContentPane().add(btnCancel);

        // ADD ROOM BUTTON
        JButton btnAddRoom = new JButton("Submit");
        btnAddRoom.addActionListener(e -> {
			SubmitForm();
		});		
        btnAddRoom.setFocusable(false);
        btnAddRoom.setBounds(310, 239, 100, 32);
        getContentPane().add(btnAddRoom);
        
    }

	public void SubmitForm() {
		
		if (checkIfFieldsEpty()) {
			JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Add Room", JOptionPane.ERROR_MESSAGE);
		} else {
			
			Integer roomNumber = Integer.parseInt(textRoomNumber.getText());
			for (String Id: RoomManager.getInstance().getRoomData().keySet()) {
				Room room = RoomManager.getInstance().getRoom(Id);
				if ((room.getRoomNumber() == roomNumber) && (!roomNumber.equals(this.orinalRoomNumber)))  {
					JOptionPane.showMessageDialog(null, "Room already exists!", "Add Room",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
			}			

			RoomStatus roomStatus = RoomStatus.fromString(comboBoxRoomStatus.getSelectedItem().toString());
			RoomType roomType = RoomType.fromString(comboBoxRoomType.getSelectedItem().toString());
			
			Set<RoomFacility> roomFacilities = new HashSet<>();
			for (Component component : roomFacilitiesPanel.getComponents()) {
				if (component instanceof IDCheckBox) {
					IDCheckBox checkBox = (IDCheckBox) component;
					if (checkBox.isSelected()) {
						roomFacilities.add(RoomFacilityManager.getInstance().getRoomFacility(checkBox.getId()));
					}
				}
			}
			
			if (this.roomId == null) {
				RoomManager.getInstance().addRoom(new Room(roomNumber, roomType, roomStatus, roomFacilities));
			} else {
				RoomManager.getInstance().addRoom(new Room(roomId, roomNumber, roomType, roomStatus, roomFacilities));
			}
			JOptionPane.showMessageDialog(null, "Room successfully registrated.", "Add Room", JOptionPane.INFORMATION_MESSAGE);
			dispose();
			
		}
	}
	
	private boolean checkIfFieldsEpty() {
		if (textRoomNumber.getText().isEmpty() ||
				comboBoxRoomType.getSelectedItem().toString().isEmpty() ||
				comboBoxRoomStatus.getSelectedItem().toString().isEmpty()) {
			return true;
		}
		return false;
	}

}

