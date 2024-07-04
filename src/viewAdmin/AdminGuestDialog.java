package viewAdmin;

import java.awt.Color;
import java.awt.Font;
import java.time.LocalDate;
import java.time.ZoneId;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import entitiesClasses.Employee;
import entitiesClasses.Guest;
import entitiesEnums.Gender;
import entitiesManager.EmployeeManager;
import entitiesManager.GuestManager;
import viewBase.JNumberTextField;

public class AdminGuestDialog extends JDialog {

    private static final long serialVersionUID = 1L;
    private String guestId;
    private String orinalUsername = null;
    
    private JDateChooser birthdateChooser;
    private JComboBox<String> comboBoxGender;
    
    private JTextField textName;
    private JTextField textSurname;
    private JNumberTextField textPhone;
    private JTextField textAddress;
    private JTextField textUsername;
    private JTextField textPassword;

    public static void main(String[] args) {
        try {
            AdminGuestDialog dialog = new AdminGuestDialog(new Color(225, 255, 225), null);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AdminGuestDialog(Color color, String guestId) {
    	this.guestId = guestId;
        setResizable(false);
        setSize(450, 420);
        setLocationRelativeTo(null);
        setTitle("Add Guest");
        getContentPane().setBackground(color);
        getContentPane().setLayout(null);

        //NAME
        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(18, 32, 114, 23);
        lblName.setFont(new Font("Tahoma", Font.PLAIN, 12));
        getContentPane().add(lblName);

        textName = new JTextField();
        textName.setBounds(18, 56, 181, 19);
        getContentPane().add(textName);
        textName.setColumns(10);
        
        //SURNAME
        JLabel lblSurname = new JLabel("Surname:");
        lblSurname.setBounds(234, 32, 114, 23);
        lblSurname.setFont(new Font("Tahoma", Font.PLAIN, 12));
        getContentPane().add(lblSurname);

        textSurname = new JTextField();
        textSurname.setBounds(234, 56, 181, 19);
        getContentPane().add(textSurname);
        textSurname.setColumns(10);
        
        // DATE OF BIRTH
        JLabel lblDateBirth = new JLabel("Date of Birth:");
        lblDateBirth.setBounds(18, 110, 114, 23);
        lblDateBirth.setFont(new Font("Tahoma", Font.PLAIN, 12));
        getContentPane().add(lblDateBirth);
        
        birthdateChooser = new JDateChooser();
        birthdateChooser.setBounds(18, 133, 181, 20);
        getContentPane().add(birthdateChooser);
                
        // GENDER
        JLabel lblRoomType = new JLabel("Gender:");
        lblRoomType.setBounds(234, 110, 114, 23);
        lblRoomType.setFont(new Font("Tahoma", Font.PLAIN, 12));
        getContentPane().add(lblRoomType);

        comboBoxGender = new JComboBox<>();
		for (Gender gender : Gender.values()) {
			comboBoxGender.addItem(gender.toString());
		}
		comboBoxGender.setBounds(234, 130, 181, 23);
        getContentPane().add(comboBoxGender);
        
        // PHONE
        JLabel lblPhone = new JLabel("Phone:");
        lblPhone.setBounds(18, 185, 114, 23);
        lblPhone.setFont(new Font("Tahoma", Font.PLAIN, 12));
        getContentPane().add(lblPhone);

        textPhone = new JNumberTextField();
        textPhone.setBounds(18, 208, 181, 19);
        getContentPane().add(textPhone);
        textPhone.setColumns(10);
        
        // ADDRESS
        JLabel lblAddress= new JLabel("Address:");
        lblAddress.setBounds(234, 185, 114, 23);
        lblAddress.setFont(new Font("Tahoma", Font.PLAIN, 12));
        getContentPane().add(lblAddress);

        textAddress = new JTextField();
        textAddress.setBounds(234, 208, 181, 19);
        getContentPane().add(textAddress);
        textAddress.setColumns(10);
        
        // USERNAME
        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setBounds(18, 259, 110, 23);
        lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 12));
        getContentPane().add(lblUsername);

        textUsername = new JTextField();
        textUsername.setBounds(18, 282, 181, 19);
        getContentPane().add(textUsername);
        textUsername.setColumns(10);
        
        // PASSWORD
        JLabel lblPassword= new JLabel("Password:");
        lblPassword.setBounds(234, 259, 119, 23);
        lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 12));
        getContentPane().add(lblPassword);

        textPassword = new JTextField();
        textPassword.setBounds(234, 282, 181, 19);
        getContentPane().add(textPassword);
        textPassword.setColumns(10);
        
        if (this.guestId != null) {
        	Guest guest = GuestManager.getInstance().getGuest(guestId);
        	setTitle("Edit Guest");
        	textName.setText(guest.getName());
        	textSurname.setText(guest.getSurname());
        	birthdateChooser.setDate(java.util.Date.from(guest.getBirthdate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        	comboBoxGender.setSelectedItem(guest.getGender().toString());
        	textPhone.setText(guest.getPhone());
        	textAddress.setText(guest.getAddress());
            textUsername.setText(guest.getUsername());
            textPassword.setText(guest.getPassword());
            
            this.orinalUsername = guest.getUsername();
        }
        
        // CANCEL BUTTON
        JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(e -> {
			dispose();
		});
        btnCancel.setFocusable(false);
        btnCancel.setBounds(18, 332, 100, 32);
        getContentPane().add(btnCancel);

        // ADD GUEST BUTTON
        JButton btnAddGuest = new JButton("Submit");
        btnAddGuest.addActionListener(e -> {
			SubmitForm();
		});		
        btnAddGuest.setFocusable(false);
        btnAddGuest.setBounds(315, 332, 100, 32);
        getContentPane().add(btnAddGuest);
        
    }

	public void SubmitForm() {
		
		if (checkIfFieldsEpty()) {
			JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Add Guest", JOptionPane.ERROR_MESSAGE);
		} else {
			String name = textName.getText();
			String surname = textSurname.getText();
			String address = textAddress.getText();

			String phone = textPhone.getText();
			if (!phone.matches("[0-9]+") || phone.length() < 6) {
				JOptionPane.showMessageDialog(null,
						"Phone number must contain only numbers and be at least 6 digits long!", "Add Guest",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
						
			String username = textUsername.getText();
			for (String Id: GuestManager.getInstance().getGuestData().keySet()) {
				Guest guest = GuestManager.getInstance().getGuest(Id);
				if (guest.getUsername().equals(username) && !username.equals(this.orinalUsername)) {
					JOptionPane.showMessageDialog(null, "Username already exists!", "Add Guest",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
			for (String Id: EmployeeManager.getInstance().getEmployeeData().keySet()) {
				Employee employee = EmployeeManager.getInstance().getEmployee(Id);
				if (employee.getUsername().equals(username)) {
					JOptionPane.showMessageDialog(null, "Username already exists!", "Add Guest",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
			
			String password = textPassword.getText();
			if (password.length() < 8 || !password.matches(".*\\d.*")) {
				JOptionPane.showMessageDialog(null, "Password must be at least 8 characters long and contain a number!",
						"Add Guest", JOptionPane.ERROR_MESSAGE);
				return;
			}

			Gender gender = Gender.fromString(comboBoxGender.getSelectedItem().toString());
			
			LocalDate birthdate = birthdateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			if (birthdate.isAfter(LocalDate.now())) {
				JOptionPane.showMessageDialog(null, "Birthdate can't be in future!", "Add Guest",
						JOptionPane.ERROR_MESSAGE);
				return;
			} else if (birthdate.isAfter(LocalDate.now().minusYears(18))) {
				JOptionPane.showMessageDialog(null, "Guest has to be of age!", "Add Guest", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if (this.guestId == null) {
				GuestManager.getInstance().addGuest(new Guest(name, surname, gender, birthdate, phone, address, username, password));
			} else {
				GuestManager.getInstance().addGuest(new Guest(this.guestId, name, surname, gender, birthdate, phone, address, username, password));
			}
			JOptionPane.showMessageDialog(null, "Guest successfully registrated.", "Add Guest", JOptionPane.INFORMATION_MESSAGE);
			dispose();
			
		}
	}
	
	private boolean checkIfFieldsEpty() {
		if (textName.getText().isEmpty() || textSurname.getText().isEmpty() || textPhone.getText().isEmpty()
				|| textAddress.getText().isEmpty() || textUsername.getText().isEmpty() || textPassword.getText().isEmpty() 
				|| birthdateChooser.getDate() == null || comboBoxGender.getSelectedItem() == null) {
			return true;
		}
		return false;
	}

}

