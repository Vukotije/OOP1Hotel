package viewReceptionist;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import entitiesClasses.AdditionalService;
import entitiesClasses.Room;
import entitiesManager.AdditionalServiceManager;
import entitiesManager.ReservationManager;
import viewBase.IDCheckBox;

public class CheckInDialog extends JDialog {

    private static final long serialVersionUID = 1L;
    private String id;

    public static void main(String[] args) {
        try {
        	CheckInDialog dialog = new CheckInDialog("1");
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public CheckInDialog(String id) {
        this.id = id;
        setResizable(false);
        setSize(270, 370);
        setLocationRelativeTo(null);
        setTitle("Check in");
        getContentPane().setBackground(new Color(172, 249, 199));
        getContentPane().setLayout(null);

        // ADDITIONAL SERVICES
        JLabel lblAdditionalServices = new JLabel("New Additional Services:");
        lblAdditionalServices.setBounds(59, 10, 148, 23);
        lblAdditionalServices.setHorizontalAlignment(SwingConstants.LEFT);
        lblAdditionalServices.setFont(new Font("Tahoma", Font.PLAIN, 12));
        getContentPane().add(lblAdditionalServices);

        // Create a JScrollPane to contain the additional services panel
        JScrollPane additionalServicesScrollPane = new JScrollPane();
        additionalServicesScrollPane.setBounds(8, 43, 238, 229);
        getContentPane().add(additionalServicesScrollPane);
        
        JPanel additionalServicesPanel = new JPanel();
        additionalServicesScrollPane.setViewportView(additionalServicesPanel);
        additionalServicesPanel.setLayout(new BoxLayout(additionalServicesPanel, BoxLayout.Y_AXIS)); // Vertical layout

        // Iterate through the hashmap and create checkboxes
        ArrayList<AdditionalService> selectedServices = ReservationManager.getInstance().getReservation(id).getAdditionalServicesWanted();
        for (String key : AdditionalServiceManager.getInstance().getAdditionalServiceData().keySet()) {
        	AdditionalService service = AdditionalServiceManager.getInstance().getAdditionalService(key);
        	if (!selectedServices.contains(service)) {
	            String serviceName = service.getName();
	            IDCheckBox checkBox = new IDCheckBox(key, serviceName);
	            checkBox.setFont(new Font("Tahoma", Font.PLAIN, 10)); 
	            additionalServicesPanel.add(checkBox);
	            additionalServicesPanel.add(Box.createVerticalStrut(5));
        	}
        }

        JButton btnCheckIn = new JButton("Check in");
        btnCheckIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkInProces();
			}
		});
        btnCheckIn.setFocusable(false);
        btnCheckIn.setBounds(75, 282, 114, 32);
        getContentPane().add(btnCheckIn);
                
    }

	public void checkInProces() {
		
		ArrayList<AdditionalService> selectedServices = new ArrayList<>();
		Component[] serviceComponents = ((JPanel) ((JScrollPane) getContentPane().getComponent(1)).getViewport().getView()).getComponents();

	    for (Component component : serviceComponents) {
	        if (component instanceof IDCheckBox && ((IDCheckBox) component).isSelected()) {
	            selectedServices.add(AdditionalServiceManager.getInstance().getAdditionalService(((IDCheckBox) component).getId()));
	        }
	    }
	    
	    Room result = ReservationManager.getInstance().checkInReservation(id, selectedServices);
	    if (result == null) {
        	JOptionPane.showMessageDialog(null, "Error checking in reservation, no available rooms!", "Check in error", JOptionPane.ERROR_MESSAGE);
	    }
        else {
        	JOptionPane.showMessageDialog(null,
        			"Check in successful. The room is " + result.getRoomNumber() +" and the price is " + ReservationManager.getInstance().getReservation(id).getPrice(),
        			"Check in", JOptionPane.INFORMATION_MESSAGE);
        }
		dispose();
	}	
}

