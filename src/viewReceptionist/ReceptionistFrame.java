package viewReceptionist;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JTabbedPane;

import entitiesClasses.Receptionist;
import entitiesManager.EmployeeManager;
import viewBase.BaseFrame;

public class ReceptionistFrame extends BaseFrame {

	private static final long serialVersionUID = 1L;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReceptionistFrame frame = new ReceptionistFrame((Receptionist) EmployeeManager.getInstance().getEmployeeByName("Mika", "Mikic"));
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ReceptionistFrame(Receptionist receptionist) {
        setTitle(getTitle() + "| Receptionist | " + receptionist.getUsername());
        setSize(1200, 600);
        setLocationRelativeTo(null);
        setVisible(true);
        
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setOpaque(true);
        tabbedPane.setBackground(new Color(152, 211, 170));
        
        getContentPane().add(tabbedPane, BorderLayout.CENTER);
        

        AllReservationsPanel reservationsPanel = new AllReservationsPanel();
        CheckInPanel checkInPanel = new CheckInPanel();
        CheckOutPanel checkOutsPanel = new CheckOutPanel();
        RoomAvailabilityPanel roomAvailabilityPanel = new RoomAvailabilityPanel();
        ReceptionistsGuestPanel receptionistsGuestPanel = new ReceptionistsGuestPanel();

        tabbedPane.addTab("Reservations", null, reservationsPanel, null);
        tabbedPane.addTab("Today ariving / Check In", null, checkInPanel, null);
        tabbedPane.addTab("Today leaving / Check Out", null, checkOutsPanel, null);
        tabbedPane.addTab("Room Availability", null, roomAvailabilityPanel, null);
        tabbedPane.addTab("Guests", null, receptionistsGuestPanel, null);

		tabbedPane.addChangeListener(e -> {
			int selectedIndex = tabbedPane.getSelectedIndex();
			switch (selectedIndex) {
			case 0:
				reservationsPanel.populateTable();
				break;
			case 1:
				checkInPanel.populateTable();
				break;
			case 2:
				checkOutsPanel.populateTable();
				break;
			case 3:
				roomAvailabilityPanel.populateTable();
				break;
			case 4:
				receptionistsGuestPanel.populateTable();
				break;
			}
		});
       


        
        

    }
    
}
