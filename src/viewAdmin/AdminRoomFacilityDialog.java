package viewAdmin;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import entitiesClasses.RoomFacility;
import entitiesManager.RoomFacilityManager;

public class AdminRoomFacilityDialog extends JDialog {

    private static final long serialVersionUID = 1L;
    private String rFacilityId;
    private String originalName = null;
    
    private JTextField textName;

    public static void main(String[] args) {
        try {
            AdminRoomFacilityDialog dialog = new AdminRoomFacilityDialog(null);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AdminRoomFacilityDialog(String rFacilityId) {
    	this.rFacilityId = rFacilityId;
        setResizable(false);
        setSize(270, 150);
        setLocationRelativeTo(null);
        setTitle("Add RoomFacility");
        getContentPane().setBackground(new Color(255, 221, 221));
        getContentPane().setLayout(null);

        //NAME
        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(8, 10, 114, 23);
        lblName.setFont(new Font("Tahoma", Font.PLAIN, 12));
        getContentPane().add(lblName);

        textName = new JTextField();
        textName.setBounds(8, 38, 235, 19);
        getContentPane().add(textName);
        textName.setColumns(10);
                
        if (this.rFacilityId != null) {
        	RoomFacility rFacility = RoomFacilityManager.getInstance().getRoomFacility(rFacilityId);
        	setTitle("Edit RoomFacility");
        	textName.setText(rFacility.getName());
        	
            this.originalName = rFacility.getName();
        }
        
        // CANCEL BUTTON
        JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(e -> {
			dispose();
		});
        btnCancel.setFocusable(false);
        btnCancel.setBounds(8, 69, 87, 23);
        getContentPane().add(btnCancel);

        // ADD ROOM FACILITY BUTTON
        JButton btnAddRoomFacility = new JButton("Submit");
        btnAddRoomFacility.addActionListener(e -> {
			SubmitForm();
		});		
        btnAddRoomFacility.setFocusable(false);
        btnAddRoomFacility.setBounds(156, 67, 87, 23);
        getContentPane().add(btnAddRoomFacility);
        
    }

	public void SubmitForm() {
		
		if (textName.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Please fill in the field.", "Add Room Facility", JOptionPane.ERROR_MESSAGE);
		} else {
			String name = textName.getText();
			for (String Id: RoomFacilityManager.getInstance().getRoomFacilityData().keySet()) {
				RoomFacility rFacility = RoomFacilityManager.getInstance().getRoomFacility(Id);
				if (rFacility.getName().equals(name) && !name.equals(this.originalName)) {
					JOptionPane.showMessageDialog(null, "This already exists!", "Add RoomFacility",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
			}			
			if (this.rFacilityId == null) {
				RoomFacilityManager.getInstance().addRoomFacility(new RoomFacility(name));
			} else {
				RoomFacilityManager.getInstance().addRoomFacility(new RoomFacility(name, this.rFacilityId));
			}
			JOptionPane.showMessageDialog(null, "Room Facility successfully added.", "Add Room Facility", JOptionPane.INFORMATION_MESSAGE);
			dispose();
			
		}
	}
}

