package viewReceptionist;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.HashSet;
import java.util.Set;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import entitiesClasses.AdditionalService;
import entitiesClasses.Room;
import entitiesEnums.RoomType;
import entitiesManager.AdditionalServiceManager;
import entitiesManager.RoomManager;
import viewBase.IDCheckBox;
import viewBase.JNumberTextField;

public class FilterReservationsDialog extends JDialog {

    private static final long serialVersionUID = 1L;

    private JPanel parentPanel; 
    
    private JNumberTextField textBottomPrice;
    private JNumberTextField textTopPrice;
    
    private JPanel additionalServicesPanel;
    private JPanel roomTypePanel;
    private JPanel roomNumberPanel;
    
	Set<AdditionalService> additionalServicesSelected = new HashSet<>();
	Set<Room> roomsSelected = new HashSet<>();
	Set<RoomType> roomTypeSelected = new HashSet<>();
	double botomPriceF = 0;
	double topPriceF = Double.MAX_VALUE;

    public static void main(String[] args) {
        try {
            FilterReservationsDialog dialog = new FilterReservationsDialog(null);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public FilterReservationsDialog(JPanel parentPanel) {
    	this.parentPanel = parentPanel;
        setResizable(false);
        setSize(615, 420);
        setLocationRelativeTo(null);
        setTitle("Filter Reservations");
        getContentPane().setBackground(new Color(225, 255, 225));
        getContentPane().setLayout(null);

        
        // Bottom price
        JLabel lblBottomPrice = new JLabel("Bottom price:");
        lblBottomPrice.setBounds(15, 303, 114, 23);
        lblBottomPrice.setFont(new Font("Tahoma", Font.PLAIN, 12));
        getContentPane().add(lblBottomPrice);

        textBottomPrice = new JNumberTextField();
        textBottomPrice.setBounds(15, 336, 180, 19);
        getContentPane().add(textBottomPrice);
        textBottomPrice.setColumns(10);
        
        // Top price
        JLabel lblTopPrice = new JLabel("Top price:");
        lblTopPrice.setBounds(210, 303, 114, 23);
        lblTopPrice.setFont(new Font("Tahoma", Font.PLAIN, 12));
        getContentPane().add(lblTopPrice);

        textTopPrice = new JNumberTextField();
        textTopPrice.setBounds(210, 336, 180, 19);
        getContentPane().add(textTopPrice);
        textTopPrice.setColumns(10);

        // ROOM TYPE
        JLabel lblRoomType = new JLabel("Room Types:");
        lblRoomType.setBounds(15, 21, 114, 23);
        lblRoomType.setHorizontalAlignment(SwingConstants.LEFT);
        lblRoomType.setFont(new Font("Tahoma", Font.PLAIN, 12));
        getContentPane().add(lblRoomType);

        roomTypePanel = new JPanel();
        roomTypePanel.setLayout(new BoxLayout(roomTypePanel, BoxLayout.Y_AXIS));
        for (RoomType roomType : RoomType.values()) {
            JCheckBox checkBox = new JCheckBox(roomType.toString());
            checkBox.setFont(new Font("Tahoma", Font.PLAIN, 10));
            roomTypePanel.add(checkBox);
            roomTypePanel.add(Box.createVerticalStrut(5));
        }
        JScrollPane roomTypeScrollPane = new JScrollPane(roomTypePanel);
        roomTypeScrollPane.setBounds(15, 50, 180, 231);
        getContentPane().add(roomTypeScrollPane);
        
        // ROOM NUMBER
        JLabel lblRoomNumber = new JLabel("Room Number:");
        lblRoomNumber.setBounds(210, 21, 114, 23);
        lblRoomNumber.setHorizontalAlignment(SwingConstants.LEFT);
        lblRoomNumber.setFont(new Font("Tahoma", Font.PLAIN, 12));
        getContentPane().add(lblRoomNumber);

        roomNumberPanel = new JPanel();
        roomNumberPanel.setLayout(new BoxLayout(roomNumberPanel, BoxLayout.Y_AXIS));
        for (String id : RoomManager.getInstance().getRoomData().keySet()) {
            String roomNumber = ((Integer)RoomManager.getInstance().getRoom(id).getRoomNumber()).toString();
            IDCheckBox checkBox = new IDCheckBox(id, roomNumber);
            checkBox.setFont(new Font("Tahoma", Font.PLAIN, 10));
            roomNumberPanel.add(checkBox);
            roomNumberPanel.add(Box.createVerticalStrut(5));
        }
        JScrollPane roomNumberScrollPane = new JScrollPane(roomNumberPanel);
        roomNumberScrollPane.setBounds(210, 50, 180, 231);
        getContentPane().add(roomNumberScrollPane);
        
        // ADDITIONAL SERVICES
        JLabel lblAdditionalServices = new JLabel("Additional Services:");
        lblAdditionalServices.setBounds(405, 21, 114, 23);
        lblAdditionalServices.setHorizontalAlignment(SwingConstants.LEFT);
        lblAdditionalServices.setFont(new Font("Tahoma", Font.PLAIN, 12));
        getContentPane().add(lblAdditionalServices);

        additionalServicesPanel = new JPanel();
        additionalServicesPanel.setLayout(new BoxLayout(additionalServicesPanel, BoxLayout.Y_AXIS));
        for (String id : AdditionalServiceManager.getInstance().getAdditionalServiceData().keySet()) {
            String serviceName = AdditionalServiceManager.getInstance().getAdditionalService(id).getName();
            IDCheckBox checkBox = new IDCheckBox(id, serviceName);
            checkBox.setFont(new Font("Tahoma", Font.PLAIN, 10));
            additionalServicesPanel.add(checkBox);
            additionalServicesPanel.add(Box.createVerticalStrut(5));
        }
        JScrollPane additionalServicesScrollPane = new JScrollPane(additionalServicesPanel);
        additionalServicesScrollPane.setBounds(405, 50, 180, 231);
        getContentPane().add(additionalServicesScrollPane);
        
        // CANCEL BUTTON
        JButton btnCancel = new JButton("X");
        btnCancel.setForeground(new Color(255, 0, 0));
        btnCancel.setFocusable(false);
        btnCancel.setBackground(new Color(255, 170, 170));
		btnCancel.addActionListener(e -> {
			dispose();
		});
        btnCancel.setBounds(405, 323, 50, 32);
        getContentPane().add(btnCancel);

        // ADD GUEST BUTTON
        JButton btnFilter = new JButton("Filter");
        btnFilter.addActionListener(e -> {
			SubmitForm();
		});		
        btnFilter.setFocusable(false);
        btnFilter.setBounds(471, 323, 114, 32);
        getContentPane().add(btnFilter);
                
    }

    public void SubmitForm() {
        additionalServicesSelected = new HashSet<>();
        for (Component component : additionalServicesPanel.getComponents()) {
            if (component instanceof IDCheckBox && ((IDCheckBox)component).isSelected()) {
                String id = ((IDCheckBox) component).getId();
                additionalServicesSelected.add(AdditionalServiceManager.getInstance().getAdditionalService(id));
            }
        }        
        roomsSelected = new HashSet<>();
        for (Component component : roomNumberPanel.getComponents()) {
            if (component instanceof IDCheckBox && ((IDCheckBox)component).isSelected()) {
                String id = ((IDCheckBox) component).getId();
                roomsSelected.add(RoomManager.getInstance().getRoom(id));
            }
        }        
        roomTypeSelected = new HashSet<>();
        for (Component component : roomTypePanel.getComponents()) {
            if (component instanceof JCheckBox && ((JCheckBox)component).isSelected()) {
                roomTypeSelected.add(RoomType.fromString(((JCheckBox) component).getText()));
            }
        }        
        String bottomPrice = textBottomPrice.getText();
        String topPrice = textTopPrice.getText();
        if (bottomPrice.isEmpty() ^ topPrice.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in both fields for price if you are searching by it.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } 
        
        if (!(textBottomPrice.getText().isEmpty() && textTopPrice.getText().isEmpty())) {
            botomPriceF = Double.parseDouble(textBottomPrice.getText());
            topPriceF = Double.parseDouble(textTopPrice.getText());
            if (botomPriceF >= topPriceF) {
                JOptionPane.showMessageDialog(this, "Bottom price must be lower than top price.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
        ((AllReservationsPanel) parentPanel).filterTable(additionalServicesSelected, roomsSelected,
                roomTypeSelected, botomPriceF, topPriceF);
        dispose();            
    }
}

