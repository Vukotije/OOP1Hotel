package viewAdmin;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
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
import javax.swing.SwingConstants;

import com.toedter.calendar.JDateChooser;

import entitiesClasses.AdditionalService;
import entitiesClasses.Guest;
import entitiesClasses.Reservation;
import entitiesClasses.RoomFacility;
import entitiesEnums.RoomType;
import entitiesManager.AdditionalServiceManager;
import entitiesManager.GuestManager;
import entitiesManager.ReservationManager;
import entitiesManager.RoomFacilityManager;
import viewBase.IDCheckBox;

public class AdminReservationDialog extends JDialog {

    private static final long serialVersionUID = 1L;
    private JDateChooser startDateChooser;
    private JDateChooser endDateChooser;
    
    private JPanel roomFacilitiesPanel;
    private JPanel additionalServicesPanel;
    
    private JComboBox<String> comboBoxRoomType;
    private JButton btnGenerate;
    private JComboBox<String> comboBoxGuest;
    
    private LocalDate startDate;
    private LocalDate endDate;
    private Set<RoomFacility> selectedFacilities;
    
    private String reservationID;

    public static void main(String[] args) {
        try {
            AdminReservationDialog dialog = new AdminReservationDialog(null, null, Color.WHITE);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AdminReservationDialog(Guest guest, String reservationID, Color color) {
    	this.reservationID = reservationID;
        setResizable(false);
        setSize(450, 600);
        setLocationRelativeTo(null);
        setTitle("Make Reservation");
        getContentPane().setBackground(color);
        getContentPane().setLayout(null);

        // START DATE
        JLabel lblStartDate = new JLabel("Start Date:");
        lblStartDate.setBounds(28, 23, 114, 23);
        lblStartDate.setFont(new Font("Tahoma", Font.PLAIN, 12));
        getContentPane().add(lblStartDate);
        
        startDateChooser = new JDateChooser();
        startDateChooser.setBounds(28, 50, 150, 20);
        startDateChooser.addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                comboBoxRoomType.removeAllItems();
            }
        });
        getContentPane().add(startDateChooser);

        // END DATE
        JLabel lblEndDate = new JLabel("End Date:");
        lblEndDate.setBounds(242, 23, 114, 23);
        lblEndDate.setFont(new Font("Tahoma", Font.PLAIN, 12));
        getContentPane().add(lblEndDate);

        endDateChooser = new JDateChooser();
        endDateChooser.setBounds(242, 50, 150, 20);
        endDateChooser.addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                comboBoxRoomType.removeAllItems();
            }
        });
        getContentPane().add(endDateChooser);
                
        // ROOM FACILITIES
        JLabel lblRoomFacilities = new JLabel("Room Facilities:");
        lblRoomFacilities.setBounds(28, 80, 114, 23);
        lblRoomFacilities.setFont(new Font("Tahoma", Font.PLAIN, 12));
        getContentPane().add(lblRoomFacilities);

        roomFacilitiesPanel = new JPanel();
        roomFacilitiesPanel.setLayout(new BoxLayout(roomFacilitiesPanel, BoxLayout.Y_AXIS));
        for (String id : RoomFacilityManager.getInstance().getRoomFacilityData().keySet()) {
            String facilityName = RoomFacilityManager.getInstance().getRoomFacility(id).getName();
            IDCheckBox checkBox = new IDCheckBox(id, facilityName);
            checkBox.setFont(new Font("Tahoma", Font.PLAIN, 10));
            checkBox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    comboBoxRoomType.removeAllItems();
                }
            });
            roomFacilitiesPanel.add(checkBox);
            roomFacilitiesPanel.add(Box.createVerticalStrut(5));
        }
        JScrollPane roomFacilityScrollPane = new JScrollPane(roomFacilitiesPanel);
        roomFacilityScrollPane.setBounds(27, 113, 150, 231);
        getContentPane().add(roomFacilityScrollPane);
        
        // ADDITIONAL SERVICES
        JLabel lblAdditionalServices = new JLabel("Additional Services:");
        lblAdditionalServices.setBounds(242, 80, 114, 23);
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
        additionalServicesScrollPane.setBounds(242, 113, 150, 231);
        getContentPane().add(additionalServicesScrollPane);

        // ROOM TYPE
        JLabel lblRoomType = new JLabel("Room Type:");
        lblRoomType.setBounds(28, 385, 114, 23);
        lblRoomType.setFont(new Font("Tahoma", Font.PLAIN, 12));
        getContentPane().add(lblRoomType);

        comboBoxRoomType = new JComboBox<>();
        comboBoxRoomType.setBounds(28, 418, 364, 23);
        comboBoxRoomType.setEnabled(false);
        getContentPane().add(comboBoxRoomType);

        btnGenerate = new JButton("Generate");
        btnGenerate.setFocusable(false);
        btnGenerate.setBounds(285, 386, 107, 23);
        btnGenerate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                generateRoomTypes();
            }
        });
        getContentPane().add(btnGenerate);
        
        //GUEST
        JLabel lblGuest = new JLabel("Guest:");
        lblGuest.setBounds(28, 451, 114, 23);
        lblGuest.setFont(new Font("Tahoma", Font.PLAIN, 12));
        getContentPane().add(lblGuest);

        comboBoxGuest = new JComboBox<>();
        for (String id : GuestManager.getInstance().getGuestData().keySet()) {
            String guestUsername = GuestManager.getInstance().getGuest(id).getUsername();
        	comboBoxGuest.addItem(guestUsername.toString());
		}
        if (guest != null) {
        	comboBoxGuest.setEnabled(false);
        	comboBoxGuest.setSelectedItem(guest.getUsername());
        }
        comboBoxGuest.setBounds(28, 484, 364, 19);
        getContentPane().add(comboBoxGuest);
               
        // EDIT RESERVATION
        if (this.reservationID != null) {
        	Reservation reservation = ReservationManager.getInstance().getReservation(reservationID);
        	setTitle("Edit Reservation");
        	startDateChooser.setDate(java.util.Date.from(reservation.getStartDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        	endDateChooser.setDate(java.util.Date.from(reservation.getEndDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        	for (Component component : additionalServicesPanel.getComponents()) {
                if (component instanceof IDCheckBox) {
					IDCheckBox checkBox = (IDCheckBox) component;
					checkBox.setSelected(reservation.getAdditionalServicesWanted().contains
							(AdditionalServiceManager.getInstance().getAdditionalService(checkBox.getId())));
                }
        	}
        	for (Component component : roomFacilitiesPanel.getComponents()) {
                if (component instanceof IDCheckBox) {
                    IDCheckBox checkBox = (IDCheckBox) component;
                    checkBox.setSelected(reservation.getRoomFacilitiesWanted().contains
                    		(RoomFacilityManager.getInstance().getRoomFacility(checkBox.getId())));
                }
        	}
        	comboBoxRoomType.setSelectedItem(reservation.getRoomType().toString());
        	comboBoxGuest.setSelectedItem(reservation.getGuest().getUsername());
        }

        JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
        btnCancel.setFocusable(false);
        btnCancel.setBounds(28, 519, 114, 32);
        getContentPane().add(btnCancel);

        JButton btnMakeReservation = new JButton("Make");
		btnMakeReservation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (comboBoxRoomType.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(null, "Please generate room types first.", "Generate Room Types",
							JOptionPane.ERROR_MESSAGE);
				} else {
					SubmitReservation();
				}
			}
		});
        btnMakeReservation.setFocusable(false);
        btnMakeReservation.setBounds(285, 519, 114, 32);
        getContentPane().add(btnMakeReservation);

    }

    private void generateRoomTypes() {
        selectedFacilities = new HashSet<>();

        Component[] components = roomFacilitiesPanel.getComponents();
		for (Component component : components) {
		    if (component instanceof IDCheckBox && ((IDCheckBox)component).isSelected()) {
		        String id = ((IDCheckBox) component).getId();
		        selectedFacilities.add(RoomFacilityManager.getInstance().getRoomFacility(id));
		    }
		}
		if (startDateChooser.getDate() == null) {
			JOptionPane.showMessageDialog(null, "Please select a start date.", "Imput Start Date", JOptionPane.ERROR_MESSAGE);
			return;
		}
		else if (endDateChooser.getDate() == null) {
			JOptionPane.showMessageDialog(null, "Please select an end date.", "Imput End Date",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		startDate = startDateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        endDate = endDateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
       
		if (startDate.isAfter(endDate)) {
			JOptionPane.showMessageDialog(null, "Start date must be before end date.", "Invalid Date",
					JOptionPane.ERROR_MESSAGE);
		}
		else if (startDate.isBefore(LocalDate.now())) {
			JOptionPane.showMessageDialog(null, "Start date must be after today.", "Invalid Date",
					JOptionPane.ERROR_MESSAGE);
		} else {
			ArrayList<RoomType> availableRoomTypes = ReservationManager.getInstance().getAvailableRoomTypes(startDate,
					endDate, selectedFacilities);
			if (availableRoomTypes.isEmpty()) {
				JOptionPane.showMessageDialog(null, "No available rooms for selected dates and facilities.",
						"No Available Rooms", JOptionPane.ERROR_MESSAGE);
			}
			else {
				updateComboBox(availableRoomTypes);
			}
		}
    }

    private void updateComboBox(ArrayList<RoomType> availableRoomTypes) {
        comboBoxRoomType.removeAllItems();
        for (RoomType roomType : availableRoomTypes) {
            comboBoxRoomType.addItem(roomType.toString());
        }
        comboBoxRoomType.setEnabled(!availableRoomTypes.isEmpty());
    }
    

	public void SubmitReservation() {		
		RoomType roomType = RoomType.fromString(comboBoxRoomType.getSelectedItem().toString());
		ArrayList<AdditionalService> selectedServices = new ArrayList<>();
		Component[] components = additionalServicesPanel.getComponents();
		
	    for (Component component : components) {
	        if (component instanceof IDCheckBox && ((IDCheckBox) component).isSelected()) {
	            selectedServices.add(AdditionalServiceManager.getInstance().getAdditionalService(((IDCheckBox) component).getId()));
	        }
	    }	    
	    String guestUsername = comboBoxGuest.getSelectedItem().toString();
	    
	    Guest guestF = null;
        for (String id : GuestManager.getInstance().getGuestData().keySet()) {
        	if (GuestManager.getInstance().getGuest(id).getUsername().equals(guestUsername)) {
        		guestF = GuestManager.getInstance().getGuest(id);
        	}
        }
        
	    int answer = JOptionPane.showConfirmDialog(null,
	    		"Do you want to continue? \nThe price will be " + Reservation.generatePrice(selectedServices, roomType, startDate, endDate),
	    		"Make Reservation", JOptionPane.YES_NO_OPTION);
		if (answer == JOptionPane.YES_OPTION) {
			ReservationManager.getInstance().makeReservation(startDate, endDate, guestF, selectedServices, selectedFacilities, roomType);
			JOptionPane.showMessageDialog(null, "Reservation successfully made.", "Make Reservation",
					JOptionPane.INFORMATION_MESSAGE);
			dispose();
		}
	}

}

