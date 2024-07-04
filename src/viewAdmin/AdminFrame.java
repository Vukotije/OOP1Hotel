package viewAdmin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JTabbedPane;

import entitiesClasses.Admin;
import entitiesManager.EmployeeManager;
import viewBase.BaseFrame;

public class AdminFrame extends BaseFrame {

	private static final long serialVersionUID = 1L;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminFrame frame = new AdminFrame((Admin) EmployeeManager.getInstance().getEmployeeByName("Pera", "Peric"));
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
	public AdminFrame(Admin admin) {
        setTitle(getTitle() + "| Admin | " + admin.getUsername());
        setSize(1200, 600);
        setLocationRelativeTo(null);
        setVisible(true);
        
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setOpaque(true);
        tabbedPane.setBackground(new Color(255, 179, 179));
        
        getContentPane().add(tabbedPane, BorderLayout.CENTER);        

        AdminGuestPanel adminGuestPanel = new AdminGuestPanel();
        AdminEmployeePanel adminEmployeePanel = new AdminEmployeePanel();
        AdminAdditionalServicePanel adminAdditionalServicePanel = new AdminAdditionalServicePanel();
        AdminRoomFacilityPanel adminRoomFacilityPanel = new AdminRoomFacilityPanel();
        AdminRoomPanel adminRoomPanel = new AdminRoomPanel();
        AdminReservationPanel adminReservationPanel = new AdminReservationPanel();
        AdminPricelistPanel adminPricelistPanel = new AdminPricelistPanel();
        ReportsPanel reportPanel = new ReportsPanel();
        ChartsPanel chartPanel = new ChartsPanel();

        tabbedPane.addTab("Guests", null, adminGuestPanel, null);
        tabbedPane.addTab("Employees", null, adminEmployeePanel, null);
        tabbedPane.addTab("Additional Services", null, adminAdditionalServicePanel, null);
        tabbedPane.addTab("Room Facilities", null, adminRoomFacilityPanel, null); 
        tabbedPane.addTab("Rooms", null, adminRoomPanel, null);
        tabbedPane.addTab("Reservations", null, adminReservationPanel, null);
        tabbedPane.addTab("Pricelist", null, adminPricelistPanel, null);
        tabbedPane.addTab("Reports", null, reportPanel, null);    
        tabbedPane.addTab("Charts", null, chartPanel, null);

		tabbedPane.addChangeListener(e -> {
			int selectedIndex = tabbedPane.getSelectedIndex();
			switch (selectedIndex) {
			case 0:
				adminGuestPanel.populateTable();
				break;
			case 1:
				adminEmployeePanel.populateTable();
				break;
			case 2:
				adminAdditionalServicePanel.populateTable();
				break;
			case 3:
				adminRoomFacilityPanel.populateTable();
				break;
			case 4:
				adminRoomPanel.populateTable();
				break;
			case 5:
				adminReservationPanel.populateTable();
				break;
			case 6:
				adminPricelistPanel.populateTable();
				break;
			}
		});
       

       
        

    }
    
}
