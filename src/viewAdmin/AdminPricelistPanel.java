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

import entitiesClasses.Pricelist;
import entitiesManager.PricelistManager;
import viewBase.BasePanel;

public class AdminPricelistPanel extends BasePanel {

	private static final long serialVersionUID = 1L;
	JButton btnViewPricelist;
	JButton btnAddPricelist;
	JButton btnEditPricelist;
    JButton btnDeletePricelist;
	DefaultTableModel tableModel;

	/**
	 * Create the panel.
	 */
	public AdminPricelistPanel() {
		setLayout(new BorderLayout(0, 0));
        
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(new Color(255, 217, 217));
        btnPanel.setOpaque(true);
        btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 15));
        add(btnPanel, BorderLayout.NORTH);

        btnViewPricelist = new JButton("VIEW PRICELIST");
        btnViewPricelist.setPreferredSize(new Dimension(140, 30));
        btnViewPricelist.setEnabled(false);
        btnViewPricelist.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.GRAY));
        btnPanel.add(btnViewPricelist);
        
        btnAddPricelist = new JButton("ADD PRICELIST");
        btnAddPricelist.setPreferredSize(new Dimension(140, 30));
        btnAddPricelist.setEnabled(true);
        btnAddPricelist.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.GRAY));
        btnPanel.add(btnAddPricelist);
        
        btnEditPricelist = new JButton("EDIT PRICELIST");
        btnEditPricelist.setPreferredSize(new Dimension(140, 30));
        btnEditPricelist.setEnabled(false);
        btnEditPricelist.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.GRAY));
        btnPanel.add(btnEditPricelist);
        
        btnDeletePricelist = new JButton("DELETE PRICELIST");
        btnDeletePricelist.setPreferredSize(new Dimension(140, 30));
        btnDeletePricelist.setEnabled(false);
        btnDeletePricelist.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.GRAY));
        btnPanel.add(btnDeletePricelist);
                
        String[] columnNames = {"ID", "Start Date", "End Date"};
        int[] columnWidths = {1, 250, 250};
        JTable table = new JTable();
        tableModel = setupTableModal(table, columnNames, columnWidths, new Color(220,20,60));
        populateTable();

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        
        JPanel borderPanel = new JPanel(new BorderLayout());
		borderPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		borderPanel.add(new JScrollPane(table), BorderLayout.CENTER);
		add(borderPanel, BorderLayout.CENTER);

		btnAddPricelist.addActionListener(e -> {
			AdminPricelistDialog dialog = new AdminPricelistDialog(null, true); 
			dialog.setModal(true);
			dialog.setVisible(true);
			tableModel.setRowCount(0);                
            populateTable();
        });

		btnEditPricelist.addActionListener(e -> {
			int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String id = (String) tableModel.getValueAt(selectedRow, 0);
				AdminPricelistDialog dialog = new AdminPricelistDialog(id, true);
				dialog.setModal(true);
				dialog.setVisible(true);
				tableModel.setRowCount(0); 
	            btnEditPricelist.setEnabled(false); 
	            btnDeletePricelist.setEnabled(false);
                btnViewPricelist.setEnabled(false);
	            populateTable();
            }
        });

		btnViewPricelist.addActionListener(e -> {
			int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String id = (String) tableModel.getValueAt(selectedRow, 0);
				AdminPricelistDialog dialog = new AdminPricelistDialog(id, false);
				dialog.setModal(true);
				dialog.setVisible(true);
				tableModel.setRowCount(0); 
	            btnEditPricelist.setEnabled(false); 
	            btnDeletePricelist.setEnabled(false);
                btnViewPricelist.setEnabled(false);
	            populateTable();
            }
        });

		btnDeletePricelist.addActionListener(e -> {
			int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
              String id = (String) tableModel.getValueAt(selectedRow, 0);
              int answer = JOptionPane.showConfirmDialog(null,
              		"Are you sure you want to delete this pricelist?",
                      "Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
              if (answer == JOptionPane.YES_OPTION) {
                  PricelistManager.getInstance().removePricelist(id);
                  JOptionPane.showMessageDialog(this, "Pricelist deleted successfully.");
                  btnDeletePricelist.setEnabled(false);
                  btnEditPricelist.setEnabled(false); 
                  btnViewPricelist.setEnabled(false);
                  populateTable();
            }
            }
        });
		
		table.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
				btnEditPricelist.setEnabled(true);
				btnDeletePricelist.setEnabled(true);
                btnViewPricelist.setEnabled(true);
			}
		});
	}
	
	void populateTable() {
		 tableModel.setRowCount(0);
		 for (String key : PricelistManager.getInstance().getPricelistData().keySet()) {
			 Pricelist pricelist = PricelistManager.getInstance().getPricelist(key);
			 Object[] rowData = {
				pricelist.getId(),
                pricelist.getStartDate(),
                pricelist.getEndDate()
			 };
			 tableModel.addRow(rowData);
			 }    
		 }

}
