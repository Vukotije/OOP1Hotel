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

import entitiesClasses.AdditionalService;
import entitiesManager.AdditionalServiceManager;
import viewBase.BasePanel;

public class AdminAdditionalServicePanel extends BasePanel {

	private static final long serialVersionUID = 1L;
	JButton btnAddAdditionalService;
	JButton btnEditAdditionalService;
    JButton btnDeleteAdditionalService;
	DefaultTableModel tableModel;

	/**
	 * Create the panel.
	 */
	public AdminAdditionalServicePanel() {
		setLayout(new BorderLayout(0, 0));
        
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(new Color(255, 217, 217));
        btnPanel.setOpaque(true);
        btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 15));
        add(btnPanel, BorderLayout.NORTH);

        btnAddAdditionalService = new JButton("ADD ADDITIONAL SERVICE");
        btnAddAdditionalService.setPreferredSize(new Dimension(140, 30));
        btnAddAdditionalService.setEnabled(true);
        btnAddAdditionalService.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.GRAY));
        btnPanel.add(btnAddAdditionalService);
        
        btnEditAdditionalService = new JButton("EDIT ADDITIONAL SERVICE");
        btnEditAdditionalService.setPreferredSize(new Dimension(140, 30));
        btnEditAdditionalService.setEnabled(false);
        btnEditAdditionalService.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.GRAY));
        btnPanel.add(btnEditAdditionalService);
        
        btnDeleteAdditionalService = new JButton("DELETE ADDITIONAL SERVICE");
        btnDeleteAdditionalService.setPreferredSize(new Dimension(140, 30));
        btnDeleteAdditionalService.setEnabled(false);
        btnDeleteAdditionalService.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.GRAY));
        btnPanel.add(btnDeleteAdditionalService);
        
                
        String[] columnNames = {"ID", "Name"};
        int[] columnWidths = {1, 40};
        JTable table = new JTable();
        tableModel = setupTableModal(table, columnNames, columnWidths, new Color(220,20,60));
        
        populateTable();

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        
        JPanel borderPanel = new JPanel(new BorderLayout());
		borderPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		borderPanel.add(new JScrollPane(table), BorderLayout.CENTER);
		add(borderPanel, BorderLayout.CENTER);

		btnAddAdditionalService.addActionListener(e -> {
			AdminAdditionalServiceDialog dialog = new AdminAdditionalServiceDialog(null); 
			dialog.setModal(true);
			dialog.setVisible(true);               
            populateTable();
        });

		btnEditAdditionalService.addActionListener(e -> {
			int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
            	String id = (String) tableModel.getValueAt(selectedRow, 0);
				AdminAdditionalServiceDialog dialog = new AdminAdditionalServiceDialog(id); 
				dialog.setModal(true);
				dialog.setVisible(true);
	            btnEditAdditionalService.setEnabled(false); 
	            btnDeleteAdditionalService.setEnabled(false);
	            populateTable();
            }
        });

		btnDeleteAdditionalService.addActionListener(e -> {
			int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
              String id = (String) tableModel.getValueAt(selectedRow, 0);
              int answer = JOptionPane.showConfirmDialog(null,
              		"Are you sure you want to delete this additional service?",
                      "Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
              if (answer == JOptionPane.YES_OPTION) {
                  AdditionalServiceManager.getInstance().deleteAditionalService(id);
                  JOptionPane.showMessageDialog(this, "AdditionalServices deleted successfully.");
                  btnDeleteAdditionalService.setEnabled(false);
                  btnEditAdditionalService.setEnabled(false); 
                  populateTable();
            }
            }
        });
		
		table.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
				btnEditAdditionalService.setEnabled(true);
				btnDeleteAdditionalService.setEnabled(true);
			}
		});
	}
	
    void populateTable() {
        tableModel.setRowCount(0);
        for (String id : AdditionalServiceManager.getInstance().getAdditionalServiceData().keySet()) {
            AdditionalService aService = AdditionalServiceManager.getInstance().getAdditionalService(id);
            Object[] rowData = {
                aService.getId(),
                aService.getName(),
            };				
            tableModel.addRow(rowData);;
        }
    }

}
