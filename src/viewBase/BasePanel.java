package viewBase;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class BasePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public BasePanel() {

	}
	
	
public DefaultTableModel setupTableModal(JTable table, String[] columnNames, int[] columnWidths, Color headingColor) {
    // Make table uneditable
    DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
        private static final long serialVersionUID = 1L;

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    table.setModel(tableModel);

    table.setRowHeight(30);
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(JLabel.CENTER);
    table.setDefaultRenderer(Object.class, centerRenderer);

    for (int i = 0; i < columnWidths.length; i++) {
        TableColumn column = table.getColumnModel().getColumn(i);
        column.setPreferredWidth(columnWidths[i]);
    }
    DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
    headerRenderer.setBackground(headingColor);
    headerRenderer.setForeground(Color.WHITE);
    for (int i = 0; i < table.getModel().getColumnCount(); i++) {
        table.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
    }
    table.removeColumn(table.getColumnModel().getColumn(0));
    return tableModel;
	}


}
