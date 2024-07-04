package viewBase;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import entitiesClasses.Admin;
import entitiesClasses.Guest;
import entitiesClasses.Maid;
import entitiesClasses.Receptionist;
import entitiesClasses.User;
import entitiesEnums.UserRoles;
import entitiesManager.EmployeeManager;
import entitiesManager.GuestManager;
import viewAdmin.AdminFrame;
import viewGuest.GuestFrame;
import viewMaid.MaidFrame;
import viewReceptionist.ReceptionistFrame;

public class LoginFrame extends BaseFrame {

    private static final long serialVersionUID = 1L;

    public LoginFrame() {
        setTitle(getTitle() + "| Login");
        JPanel panel = new JPanel();
        arrangePanelContent(panel);
        add(panel);

        setVisible(true);
    }

    private void arrangePanelContent(JPanel panel) {

        panel.setBackground(new Color(255, 245, 238));
        
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        ImageIcon logoIcon = new ImageIcon("assets/pictures/VuHotelLogoWithTextResized.png");
        JLabel logoLabel = new JLabel(logoIcon);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5, 5, 20, 5);
        panel.add(logoLabel, gbc);

        gbc.insets = new Insets(5, 5, 5, 5);
        
        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(usernameLabel, gbc);

        JTextField usernameText = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(usernameText, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(passwordLabel, gbc);

        JPasswordField passwordText = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(passwordText, gbc);

        JButton loginButton = new JButton("Login");
        loginButton.setFocusable(false);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(loginButton, gbc);

//      FOR TESTING PURPOSES ONLY:
//      passwordText.setText("jana123");
//      usernameText.setText("jana");
//		passwordText.setText("mica123");
//		usernameText.setText("mica");
//		passwordText.setText("miki123");
//		usernameText.setText("miki");
//		passwordText.setText("peki123");
//		usernameText.setText("peki");
    
        loginButton.addActionListener(e -> {
            String username = usernameText.getText();
            String password = new String(passwordText.getPassword());
			
            GuestManager guestManager = GuestManager.getInstance();            
			for (String id : guestManager.getGuestData().keySet()) {
				if (guestManager.getGuest(id).getUsername().equals(username) &&
                		guestManager.getGuest(id).getPassword().equals(password)) {
                	redirectToRoleScreen(guestManager.getGuest(id));
                    return;
                }
            }
            EmployeeManager employeeManager = EmployeeManager.getInstance();
			for (String id : employeeManager.getEmployeeData().keySet()) {
				if (employeeManager.getEmployee(id).getUsername().equals(username)
						&& employeeManager.getEmployee(id).getPassword().equals(password)) {
					redirectToRoleScreen(employeeManager.getEmployee(id));
					return;
				}
			}
			if (username.isEmpty()) {
				JOptionPane.showMessageDialog(panel, "You didn't input an username!", "Empy Username", JOptionPane.ERROR_MESSAGE);
			}
			else if (password.isEmpty()) {
				JOptionPane.showMessageDialog(panel, "You didn't input a password!", "Empy Password", JOptionPane.ERROR_MESSAGE);			
            } else {
            	JOptionPane.showMessageDialog(panel, "Invalid username or password!", "Invalid Credentials", JOptionPane.ERROR_MESSAGE);
        	}
        });
    }

    private void redirectToRoleScreen(User user) {
	    this.dispose();
        UserRoles role = user.getRole();
	    switch (role) {
	        case GUEST:
	            new GuestFrame((Guest)user);
	            break;
	        case MAID:
	            new MaidFrame((Maid) user);
	            break;
	        case RECEPTIONIST:
	            new ReceptionistFrame((Receptionist)user);
	            break;
	        case ADMIN:
	        	new AdminFrame((Admin)user);
	            break;
	    }
    }
    
    
}
