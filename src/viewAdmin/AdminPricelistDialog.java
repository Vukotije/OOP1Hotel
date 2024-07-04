package viewAdmin;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.toedter.calendar.JDateChooser;

import entitiesClasses.AdditionalService;
import entitiesClasses.Pricelist;
import entitiesEnums.RoomType;
import entitiesManager.AdditionalServiceManager;
import entitiesManager.PricelistManager;
import viewBase.JNumberTextField;

public class AdminPricelistDialog extends JDialog {

    private static final long serialVersionUID = 1L;
    private String pricelistId = null;

    private JDateChooser startDateChooser;
    private JDateChooser endDateChooser;
    
    private JPanel additionalServicesPanel;
    private JPanel roomTypesPanel;    
    

    public static void main(String[] args) {
        try {
            AdminPricelistDialog dialog = new AdminPricelistDialog(null, true);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AdminPricelistDialog(String pricelistId, boolean editable) {
    	this.pricelistId = pricelistId;
        setResizable(false);
        setSize(600, 350);
        setLocationRelativeTo(null);
        setTitle("Add Pricelist");
        getContentPane().setBackground(new Color(255, 221, 221));
        getContentPane().setLayout(null);
        
        // START DATE
        JLabel lblDateStart = new JLabel("Start Date:");
        lblDateStart.setBounds(450, 50, 114, 23);
        lblDateStart.setFont(new Font("Tahoma", Font.PLAIN, 12));
        getContentPane().add(lblDateStart);
        
        startDateChooser = new JDateChooser();
        startDateChooser.setBounds(450, 76, 114, 32);
        getContentPane().add(startDateChooser);
        
        // END DATE
        JLabel lblDateEnd = new JLabel("End Date:");
        lblDateEnd.setBounds(450, 129, 114, 23);
        lblDateEnd.setFont(new Font("Tahoma", Font.PLAIN, 12));
        getContentPane().add(lblDateEnd);
        
        endDateChooser = new JDateChooser();
        endDateChooser.setBounds(450, 150, 114, 32);
        getContentPane().add(endDateChooser);
        
        // ADDITIONAL SERVICES
        JLabel lblAdditionalServices = new JLabel("Additional Services:");
        lblAdditionalServices.setBounds(18, 24, 115, 23);
        lblAdditionalServices.setHorizontalAlignment(SwingConstants.LEFT);
        lblAdditionalServices.setFont(new Font("Tahoma", Font.PLAIN, 12));
        getContentPane().add(lblAdditionalServices);

        additionalServicesPanel = new JPanel();
        additionalServicesPanel.setLayout(new BoxLayout(additionalServicesPanel, BoxLayout.Y_AXIS));
        for (String id : AdditionalServiceManager.getInstance().getAdditionalServiceData().keySet()) {
            AdditionalService service = AdditionalServiceManager.getInstance().getAdditionalService(id);
            
            JLabel lblService = new JLabel(service.getName());
            lblService.setFont(new Font("Tahoma", Font.PLAIN, 10));
            additionalServicesPanel.add(lblService);
            additionalServicesPanel.add(Box.createVerticalStrut(5));
            
            JNumberTextField numberTextField = new JNumberTextField();
            if (this.pricelistId != null) {
            	Pricelist pricelist = PricelistManager.getInstance().getPricelist(pricelistId);
            	numberTextField.setText(String.valueOf((int)pricelist.getAdditionalServicePrice(service)));
            }
            if (!editable) {
                numberTextField.setEditable(editable);
            }
            numberTextField.setFont(new Font("Tahoma", Font.PLAIN, 10));
            additionalServicesPanel.add(numberTextField);
            additionalServicesPanel.add(Box.createVerticalStrut(5));
        }
        JScrollPane additionalServicesScrollPane = new JScrollPane(additionalServicesPanel);
        additionalServicesScrollPane.setBounds(15, 50, 115, 231);
        getContentPane().add(additionalServicesScrollPane);
                     
        
        // ROOM TYPES
        JLabel lblRoomType = new JLabel("Room Type:");
        lblRoomType.setBounds(145, 24, 114, 23);
        lblRoomType.setHorizontalAlignment(SwingConstants.LEFT);
        lblRoomType.setFont(new Font("Tahoma", Font.PLAIN, 12));
        getContentPane().add(lblRoomType);

        roomTypesPanel = new JPanel();
        roomTypesPanel.setLayout(new BoxLayout(roomTypesPanel, BoxLayout.Y_AXIS));
        for (RoomType roomType: RoomType.values()) {     
        	
            JLabel lblType = new JLabel(roomType.toString());
            lblType.setFont(new Font("Tahoma", Font.PLAIN, 10));
            roomTypesPanel.add(lblType);
            roomTypesPanel.add(Box.createVerticalStrut(5));
            
            JNumberTextField numberTextField = new JNumberTextField();
            if (this.pricelistId != null) {
            	Pricelist pricelist = PricelistManager.getInstance().getPricelist(pricelistId);
            	numberTextField.setText(String.valueOf((int)pricelist.getRoomTypePrice(roomType)));
            }
			if (!editable) {
				numberTextField.setEditable(editable);
			}
            numberTextField.setFont(new Font("Tahoma", Font.PLAIN, 10));
            roomTypesPanel.add(numberTextField);
            roomTypesPanel.add(Box.createVerticalStrut(5));
        }
        JScrollPane roomTypeScrollPane = new JScrollPane(roomTypesPanel);
        roomTypeScrollPane.setBounds(145, 50, 290, 231);
        getContentPane().add(roomTypeScrollPane);
                                     
        // CANCEL BUTTON
        JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(e -> {
			dispose();
		});
        btnCancel.setFocusable(false);
        btnCancel.setBounds(450, 249, 114, 32);
        getContentPane().add(btnCancel);

        // ADD PRICELIST BUTTON
        JButton btnAddPricelist = new JButton("Submit");
        btnAddPricelist.addActionListener(e -> {
			SubmitForm();
		});		
        btnAddPricelist.setFocusable(false);
        btnAddPricelist.setBounds(450, 207, 114, 32);
        getContentPane().add(btnAddPricelist);
        
        // EDIT PRICELIST (OR VIEW PRICELIST)
        if (this.pricelistId != null) {
        	Pricelist pricelist = PricelistManager.getInstance().getPricelist(pricelistId);
        	setTitle("Edit Pricelist");
        	startDateChooser.setDate(java.util.Date.from(pricelist.getStartDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        	endDateChooser.setDate(java.util.Date.from(pricelist.getEndDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));        	
        }
        if (!editable) {
			setTitle("View Pricelist");
			((JTextField)startDateChooser.getDateEditor()).setEditable(false);
			((JTextField)endDateChooser.getDateEditor()).setEditable(false);
			startDateChooser.setComponentPopupMenu(null);
			endDateChooser.setComponentPopupMenu(null);
			btnAddPricelist.setVisible(false);
			btnCancel.setVisible(false);
        }
    }

	public void SubmitForm() {
		
		if (checkIfFieldsEpty()) {
			JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Add Pricelist", JOptionPane.ERROR_MESSAGE);
		} else {
			
			LocalDate stardDate = startDateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate endDate = endDateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			if (stardDate.isAfter(endDate)) {
				JOptionPane.showMessageDialog(null, "End date must be after start date.", "Add Pricelist",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			// ADDITIONAL SERVICES
			ArrayList<AdditionalService> additionalServices = new ArrayList<>();
			ArrayList<Double> additionalServicesPrice = new ArrayList<>();
			for (Component component : additionalServicesPanel.getComponents()) {
				if (component instanceof JLabel) {
					additionalServices.add(AdditionalServiceManager.getInstance().getAdditionalServiceByName(((JLabel) component).getText()));
				}
				if (component instanceof JNumberTextField) {
					additionalServicesPrice.add(Double.parseDouble(((JNumberTextField) component).getText().strip()));
				}
			}
			HashMap<AdditionalService, Double> additionalServicesMap = new HashMap<>();
			for (int i = 0; i < additionalServices.size(); i++) {
				additionalServicesMap.put(additionalServices.get(i), additionalServicesPrice.get(i));
			}
			
			// ROOM TYPES
			ArrayList<Double> roomTypesPrice = new ArrayList<>();
			ArrayList<RoomType> roomTypes = new ArrayList<>();
			for (Component component : roomTypesPanel.getComponents()) {
				if (component instanceof JLabel) {
					roomTypes.add(RoomType.fromString(((JLabel) component).getText().strip()));
				}
				if (component instanceof JNumberTextField) {
					roomTypesPrice.add(Double.parseDouble(((JNumberTextField) component).getText()));
				}
			}
			EnumMap<RoomType, Double> roomTypesMap = new EnumMap<>(RoomType.class);
			for (int i = 0; i < roomTypes.size(); i++) {
				roomTypesMap.put(roomTypes.get(i), roomTypesPrice.get(i));
			}
			if (this.pricelistId == null) {
				PricelistManager.getInstance().addPricelist(new Pricelist(stardDate, endDate, roomTypesMap, additionalServicesMap));
			} else {
				PricelistManager.getInstance().addPricelist(new Pricelist(pricelistId, stardDate, endDate, roomTypesMap, additionalServicesMap));
			}
			JOptionPane.showMessageDialog(null, "Pricelist successfully registrated.", "Add Pricelist", JOptionPane.INFORMATION_MESSAGE);
			dispose();
		}
		
	}
	private boolean checkIfFieldsEpty() {
		if (startDateChooser.getDate() == null || endDateChooser.getDate() == null) {
			return true;			
		}
		for (Component component : additionalServicesPanel.getComponents()) {
			if (component instanceof JNumberTextField) {
				if (((JNumberTextField) component).getText().isEmpty()) {
					return true;
				}
			}
		}
		for (Component component : roomTypesPanel.getComponents()) {
			if (component instanceof JNumberTextField) {
				if (((JNumberTextField) component).getText().isEmpty()) {
					return true;
				}
			}
		}
		return false;
	}

}

