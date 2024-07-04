package viewAdmin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import entitiesClasses.Employee;
import entitiesManager.EmployeeManager;
import viewBase.BasePanel;

public class AdminEmployeePanel extends BasePanel {

	private static final long serialVersionUID = 1L;
	JButton btnAddEmployee;
	JButton btnEditEmployee;
    JButton btnDeleteEmployee;
	DefaultTableModel tableModel;

	/**
	 * Create the panel.
	 */
	public AdminEmployeePanel() {
		setLayout(new BorderLayout(0, 0));
        
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(new Color(255, 217, 217));
        btnPanel.setOpaque(true);
        btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 15));
        add(btnPanel, BorderLayout.NORTH);

        btnAddEmployee = new JButton("ADD EMPLOYEE");
        btnAddEmployee.setPreferredSize(new Dimension(140, 30));
        btnAddEmployee.setEnabled(true);
        btnAddEmployee.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.GRAY));
        btnPanel.add(btnAddEmployee);
        
        btnEditEmployee = new JButton("EDIT EMPLOYEE");
        btnEditEmployee.setPreferredSize(new Dimension(140, 30));
        btnEditEmployee.setEnabled(false);
        btnEditEmployee.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.GRAY));
        btnPanel.add(btnEditEmployee);
        
        btnDeleteEmployee = new JButton("DELETE EMPLOYEE");
        btnDeleteEmployee.setPreferredSize(new Dimension(140, 30));
        btnDeleteEmployee.setEnabled(false);
        btnDeleteEmployee.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.GRAY));
        btnPanel.add(btnDeleteEmployee);
        
                
        String[] columnNames = {"ID", "Name", "Surname", "Gender", "Date of Birth", "Phone",
        		"Address", "Username", "Education Level", "Experience", "Salary", "Role"};
        int[] columnWidths = {1, 40, 40, 20, 40, 50, 60, 40, 40, 40, 40, 40};
        JTable table = new JTable();
        tableModel = setupTableModal(table, columnNames, columnWidths, new Color(220,20,60));
        
        populateTable();

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        
        JPanel borderPanel = new JPanel(new BorderLayout());
		borderPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		borderPanel.add(new JScrollPane(table), BorderLayout.CENTER);
		add(borderPanel, BorderLayout.CENTER);


		btnAddEmployee.addActionListener(e -> {
			AdminEmployeeDialog dialog = new AdminEmployeeDialog(null); 
			dialog.setModal(true);
			dialog.setVisible(true);
			tableModel.setRowCount(0);                
            populateTable();
        });

		btnEditEmployee.addActionListener(e -> {
			int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String id = (String) tableModel.getValueAt(selectedRow, 0);
				AdminEmployeeDialog dialog = new AdminEmployeeDialog(id); 
				dialog.setModal(true);
				dialog.setVisible(true);
				tableModel.setRowCount(0); 
	            btnEditEmployee.setEnabled(false); 
	            btnDeleteEmployee.setEnabled(false);
	            populateTable();
            }
        });

		btnDeleteEmployee.addActionListener(e -> {
			int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
              String id = (String) tableModel.getValueAt(selectedRow, 0);
              int answer = JOptionPane.showConfirmDialog(null,
              		"Are you sure you want to delete this employees account?",
                      "Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
              if (answer == JOptionPane.YES_OPTION) {
                  EmployeeManager.getInstance().removeEmployee(id);
                  JOptionPane.showMessageDialog(this, "Employees account deleted successfully.");
                  btnDeleteEmployee.setEnabled(false);
                  btnEditEmployee.setEnabled(false); 
                  populateTable();
            }
            }
        });
		
		table.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
				btnEditEmployee.setEnabled(true);
				btnDeleteEmployee.setEnabled(true);
			}
		});
	}
	
	void populateTable() {
	    tableModel.setRowCount(0);
	    for (String id : EmployeeManager.getInstance().getEmployeeData().keySet()) {
	        Employee employee = EmployeeManager.getInstance().getEmployee(id);
	        Object[] rowData = {
	            employee.getId(),
	            employee.getName(),
	            employee.getSurname(),
	            employee.getGender().toString(),
	            employee.getBirthdate().toString(),
	            employee.getPhone(),
	            employee.getAddress(),
	            employee.getUsername(),
	            employee.getEducationLevel().toString(),
	            employee.getExperience(),
	            employee.getSalary(),
	            employee.getRole()
	        };
	        tableModel.addRow(rowData);
	    }
	}

}
