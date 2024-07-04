package viewAdmin;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import entitiesClasses.AdditionalService;
import entitiesManager.AdditionalServiceManager;

public class AdminAdditionalServiceDialog extends JDialog {

    private static final long serialVersionUID = 1L;
    private String aServiceId;
    private String originalName = null;
    
    private JTextField textName;

    public static void main(String[] args) {
        try {
            AdminAdditionalServiceDialog dialog = new AdminAdditionalServiceDialog(null);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AdminAdditionalServiceDialog(String aServiceId) {
    	this.aServiceId = aServiceId;
        setResizable(false);
        setSize(270, 150);
        setLocationRelativeTo(null);
        setTitle("Add AdditionalService");
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
                
        if (this.aServiceId != null) {
        	AdditionalService aService = AdditionalServiceManager.getInstance().getAdditionalService(aServiceId);
        	setTitle("Edit AdditionalService");
        	textName.setText(aService.getName());
        	
            this.originalName = aService.getName();
        }
        
        // CANCEL BUTTON
        JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(e -> {
			dispose();
		});
        btnCancel.setFocusable(false);
        btnCancel.setBounds(8, 69, 87, 23);
        getContentPane().add(btnCancel);

        // ADD ADDITIONAL SERVICE BUTTON
        JButton btnAddAdditionalService = new JButton("Submit");
        btnAddAdditionalService.addActionListener(e -> {
			SubmitForm();
		});		
        btnAddAdditionalService.setFocusable(false);
        btnAddAdditionalService.setBounds(156, 67, 87, 23);
        getContentPane().add(btnAddAdditionalService);
        
    }

	public void SubmitForm() {
		
		if (textName.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Please fill in the field.", "Add AdditionalService", JOptionPane.ERROR_MESSAGE);
		} else {
			String name = textName.getText();
			for (String Id: AdditionalServiceManager.getInstance().getAdditionalServiceData().keySet()) {
				AdditionalService aService = AdditionalServiceManager.getInstance().getAdditionalService(Id);
				if (aService.getName().equals(name) && !name.equals(this.originalName)) {
					JOptionPane.showMessageDialog(null, "This already exists!", "Add AdditionalService",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
			}			
			if (this.aServiceId == null) {
				AdditionalServiceManager.getInstance().addAdditionalService(new AdditionalService(name));
			} else {
				AdditionalServiceManager.getInstance().addAdditionalService(new AdditionalService(name, this.aServiceId));
			}
			JOptionPane.showMessageDialog(null, "AdditionalService successfully added.", "Add AdditionalService", JOptionPane.INFORMATION_MESSAGE);
			dispose();
			
		}
	}
}

