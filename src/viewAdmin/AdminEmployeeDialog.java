package viewAdmin;

import java.awt.Color;
import java.awt.Font;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import entitiesClasses.Admin;
import entitiesClasses.Employee;
import entitiesClasses.Maid;
import entitiesClasses.Receptionist;
import entitiesClasses.Room;
import entitiesEnums.EducationLevel;
import entitiesEnums.Gender;
import entitiesEnums.UserRoles;
import entitiesManager.EmployeeManager;
import viewBase.JNumberTextField;

public class AdminEmployeeDialog extends JDialog {

    private static final long serialVersionUID = 1L;
    private String employeeId;
    private String orinalUsername = null;
    
    private JDateChooser birthdateChooser;
    private JComboBox<String> comboBoxGender;
    
    private JTextField textName;
    private JTextField textSurname;
    private JNumberTextField textPhone;
    private JTextField textAddress;
    private JTextField textUsername;
    private JTextField textPassword;

    private JComboBox<String> comboBoxRole;
    private JComboBox<String> comboBoxEducationLevel;
    private JNumberTextField textExperience;
    
    private ArrayList<LocalDate> roomsCleaned = null;
	private ArrayList<LocalDate> roomsAssignedToClean = null;
	private ArrayList<Room> roomsLeftToClean = null;

    public static void main(String[] args) {
        try {
            AdminEmployeeDialog dialog = new AdminEmployeeDialog(null);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AdminEmployeeDialog(String employeeId) {
    	this.employeeId = employeeId;
        setResizable(false);
        setSize(450, 510);
        setLocationRelativeTo(null);
        setTitle("Add Employee");
        getContentPane().setBackground(new Color(255, 217, 217));
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
        JLabel lblGenderType = new JLabel("Gender:");
        lblGenderType.setBounds(234, 110, 114, 23);
        lblGenderType.setFont(new Font("Tahoma", Font.PLAIN, 12));
        getContentPane().add(lblGenderType);

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

        // ROLE
        JLabel lblRole = new JLabel("Role:");
        lblRole.setBounds(18, 321, 114, 23);
        lblRole.setFont(new Font("Tahoma", Font.PLAIN, 12));
        getContentPane().add(lblRole);

        comboBoxRole = new JComboBox<>();
		for (UserRoles role : UserRoles.values()) {
			if (!role.equals(UserRoles.GUEST)) {
				comboBoxRole.addItem(role.toString());
			}
		}
		comboBoxRole.setBounds(18, 346, 181, 23);
        getContentPane().add(comboBoxRole);
        
        // EDUCATION LEVEL
        JLabel lblEducationLeveLabel = new JLabel("Education level:");
        lblEducationLeveLabel.setBounds(234, 321, 114, 23);
        lblEducationLeveLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        getContentPane().add(lblEducationLeveLabel);

        comboBoxEducationLevel = new JComboBox<>();
		for (EducationLevel educationLevel : EducationLevel.values()) {
			comboBoxEducationLevel.addItem(educationLevel.toString());
		}
		comboBoxEducationLevel.setBounds(234, 346, 181, 23);
        getContentPane().add(comboBoxEducationLevel);
        
        // EXPERIENCE
        JLabel lblExperience = new JLabel("Years of experience:");
        lblExperience.setBounds(18, 394, 114, 23);
        lblExperience.setFont(new Font("Tahoma", Font.PLAIN, 12));
        getContentPane().add(lblExperience);

        textExperience = new JNumberTextField();
        textExperience.setBounds(18, 415, 181, 19);
        getContentPane().add(textExperience);
        textExperience.setColumns(10);
                
        if (this.employeeId != null) {
        	Employee employee = EmployeeManager.getInstance().getEmployee(employeeId);
        	setTitle("Edit Employee");
        	textName.setText(employee.getName());
        	textSurname.setText(employee.getSurname());
        	birthdateChooser.setDate(java.util.Date.from(employee.getBirthdate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        	comboBoxGender.setSelectedItem(employee.getGender().toString());
        	textPhone.setText(employee.getPhone());
        	textAddress.setText(employee.getAddress());
            textUsername.setText(employee.getUsername());
            textPassword.setText(employee.getPassword());
            comboBoxRole.setSelectedItem(employee.getRole().toString());
            comboBoxEducationLevel.setSelectedItem(employee.getEducationLevel().toString());
			textExperience.setText(String.valueOf(employee.getExperience()));
            
            this.orinalUsername = employee.getUsername();
			if (employee instanceof Maid) {
				roomsCleaned = ((Maid) employee).getRoomsCleaned();
				roomsAssignedToClean = ((Maid) employee).getRoomsAssignedToClean();
				roomsLeftToClean = ((Maid) employee).getRoomsLeftToClean();
			}
        }
        
        // CANCEL BUTTON
        JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(e -> {
			dispose();
		});
        btnCancel.setFocusable(false);
        btnCancel.setBounds(234, 408, 80, 32);
        getContentPane().add(btnCancel);

        // ADD EMPLOYEE BUTTON
        JButton btnAddEmployee = new JButton("Submit");
        btnAddEmployee.addActionListener(e -> {
			SubmitForm();
		});		
        btnAddEmployee.setFocusable(false);
        btnAddEmployee.setBounds(335, 408, 80, 32);
        getContentPane().add(btnAddEmployee);
        
    }

	public void SubmitForm() {
		
		if (checkIfFieldsEpty()) {
			JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Add Employee", JOptionPane.ERROR_MESSAGE);
		} else {
			String name = textName.getText();
			String surname = textSurname.getText();
			String address = textAddress.getText();

			String phone = textPhone.getText();
			if (!phone.matches("[0-9]+") || phone.length() < 6) {
				JOptionPane.showMessageDialog(null,
						"Phone number must contain only numbers and be at least 6 digits long!", "Add Employee",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
						
			String username = textUsername.getText();
			for (String Id: EmployeeManager.getInstance().getEmployeeData().keySet()) {
				Employee employee = EmployeeManager.getInstance().getEmployee(Id);
				if (employee.getUsername().equals(username) && !username.equals(this.orinalUsername)) {
					JOptionPane.showMessageDialog(null, "Username already exists!", "Add Employee",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
			for (String Id: EmployeeManager.getInstance().getEmployeeData().keySet()) {
				Employee employee = EmployeeManager.getInstance().getEmployee(Id);
				if (employee.getUsername().equals(username)) {
					JOptionPane.showMessageDialog(null, "Username already exists!", "Add Employee",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
			
			String password = textPassword.getText();
			if (password.length() < 8 || !password.matches(".*\\d.*")) {
				JOptionPane.showMessageDialog(null, "Password must be at least 8 characters long and contain a number!",
						"Add Employee", JOptionPane.ERROR_MESSAGE);
				return;
			}

			Gender gender = Gender.fromString(comboBoxGender.getSelectedItem().toString());
			
			LocalDate birthdate = birthdateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			if (birthdate.isAfter(LocalDate.now())) {
				JOptionPane.showMessageDialog(null, "Birthdate can't be in future!", "Add Employee",
						JOptionPane.ERROR_MESSAGE);
				return;
			} else if (birthdate.isAfter(LocalDate.now().minusYears(18))) {
				JOptionPane.showMessageDialog(null, "Employee has to be of age!", "Add Employee", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			UserRoles role = UserRoles.fromString(comboBoxRole.getSelectedItem().toString());
			EducationLevel educationLevel = EducationLevel.fromString(comboBoxEducationLevel.getSelectedItem().toString());
			double experience = Double.parseDouble(textExperience.getText());
			
			if (this.employeeId == null) {
		        if (role.equals(UserRoles.ADMIN)) {
		            EmployeeManager.getInstance().addEmployee(new Admin(name, surname,
		                gender, birthdate, phone, address, username, password, educationLevel, experience));
		        } else if (role.equals(UserRoles.RECEPTIONIST)) {
		            EmployeeManager.getInstance().addEmployee(new Receptionist(name, surname,
		                gender, birthdate, phone, address, username, password, educationLevel, experience));
		        } else {
		            EmployeeManager.getInstance().addEmployee(new Maid(name, surname,
		                gender, birthdate, phone, address, username, password, educationLevel, experience));
		        }
		    } else {
		        if (role.equals(UserRoles.ADMIN)) {
		            EmployeeManager.getInstance().addEmployee(new Admin(employeeId, name, surname,
		                gender, birthdate, phone, address, username, password, educationLevel, experience));
		        } else if (role.equals(UserRoles.RECEPTIONIST)) {
		            EmployeeManager.getInstance().addEmployee(new Receptionist(employeeId, name, surname,
		                gender, birthdate, phone, address, username, password, educationLevel, experience));
		        } else {
		            EmployeeManager.getInstance().addEmployee(new Maid(employeeId,
		                roomsCleaned, roomsAssignedToClean, roomsLeftToClean,
		                name, surname, gender, birthdate, phone, address, username, password, educationLevel, experience));
		        }
		    }
			JOptionPane.showMessageDialog(null, "Employee successfully registrated.", "Add Employee", JOptionPane.INFORMATION_MESSAGE);
			dispose();
			
		}
	}
	
	private boolean checkIfFieldsEpty() {
		if (textName.getText().isEmpty() || textSurname.getText().isEmpty() || textPhone.getText().isEmpty()
				|| textAddress.getText().isEmpty() || textUsername.getText().isEmpty() || textPassword.getText().isEmpty() 
				|| birthdateChooser.getDate() == null || comboBoxGender.getSelectedItem() == null || comboBoxRole.getSelectedItem() == null
				|| comboBoxEducationLevel.getSelectedItem() == null || textExperience.getText().isEmpty()) {
			return true;
		}
		return false;
	}

}

