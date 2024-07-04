package viewAdmin;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import com.toedter.calendar.JDateChooser;
import entitiesClasses.Room;
import entitiesManager.RoomManager;
import reports.Report;
import viewBase.BasePanel;
import java.time.LocalDate;
import java.util.EnumMap;
import entitiesEnums.ReservationStatus;
import entitiesClasses.Employee;
import entitiesClasses.Maid;
import entitiesEnums.UserRoles;
import entitiesManager.EmployeeManager;

public class ReportsPanel extends BasePanel {

    private static final long serialVersionUID = 1L;
    private JButton btnGenerateReports;
    private JDateChooser startDateChooser, endDateChooser;
    private LocalDate startDate, endDate;
    
    private JTable roomTable, maidTable, reservationStatusTable;
    private DefaultTableModel roomTableModel, maidTableModel, reservationStatusTableModel;
    
    private JTextField revenueField, expensesField;

    public ReportsPanel() {
        setLayout(new BorderLayout());
        
        JPanel topPanel = createTopPanel();
        add(topPanel, BorderLayout.NORTH);
        
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(createSummaryPanel(), BorderLayout.NORTH);
        centerPanel.add(createTablesPanel(), BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        btnGenerateReports.addActionListener(e -> generateReports());
    }
    
    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        panel.setBackground(new Color(255, 217, 217));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        panel.add(new JLabel("Start Date:"));
        startDateChooser = new JDateChooser();
        startDateChooser.setPreferredSize(new Dimension(110, 25));
        panel.add(startDateChooser);

        panel.add(new JLabel("End Date:"));
        endDateChooser = new JDateChooser();
        endDateChooser.setPreferredSize(new Dimension(110, 25));
        panel.add(endDateChooser);
        
        btnGenerateReports = new JButton("Generate Reports");
        btnGenerateReports.setPreferredSize(new Dimension(150, 30));
        btnGenerateReports.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.GRAY));
        panel.add(btnGenerateReports);
        
        return panel;
    }
    
    private JPanel createSummaryPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        
        JLabel revenueLabel = new JLabel("Total Revenue:");
        revenueField = new JTextField(15);
        revenueField.setEditable(false);
        
        JLabel expensesLabel = new JLabel("Total Expenses:");
        expensesField = new JTextField(15);
        expensesField.setEditable(false);
        
        panel.add(revenueLabel);
        panel.add(revenueField);
        panel.add(expensesLabel);
        panel.add(expensesField);
        
        return panel;
    }
    
    private JPanel createTablesPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 10, 10);

        // Room Reports table
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.weightx = 0.6;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        roomTable = new JTable();
        panel.add(createTablePanel("Room Reports", 
            new String[]{"ID", "Room Number", "Room Type", "Visits", "Revenue"}, 
            new int[]{50, 100, 150, 100, 100},
            table -> roomTable = table), gbc);

        // Maid Reports table
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.weightx = 0.4;
        gbc.weighty = 0.5;
        maidTable = new JTable();
        panel.add(createTablePanel("Maid Reports", 
            new String[]{"ID", "Name", "Rooms Cleaned"}, 
            new int[]{50, 150, 100},
            table -> maidTable = table), gbc);

        // Reservation Status Reports table
        gbc.gridx = 1;
        gbc.gridy = 1;
        reservationStatusTable = new JTable();
        panel.add(createTablePanel("Reservation Status Reports", 
            new String[]{"x", "Status", "Count"}, 
            new int[]{1, 150, 100},
            table -> reservationStatusTable = table), gbc);

        return panel;
    }
    
    private JPanel createTablePanel(String title, String[] columnNames, int[] columnWidths, java.util.function.Consumer<JTable> tableConsumer) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(title));
        
        JTable table = new JTable();
        DefaultTableModel model = setupTableModal(table, columnNames, columnWidths, new Color(220, 20, 60));
        tableConsumer.accept(table);
        
        if (table == roomTable) {
            roomTableModel = model;
        } else if (table == maidTable) {
            maidTableModel = model;
        } else if (table == reservationStatusTable) {
            reservationStatusTableModel = model;
        }
        
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }

    private void generateReports() {
        if (startDateChooser.getDate() == null || endDateChooser.getDate() == null) {
            JOptionPane.showMessageDialog(this, "You need to input both start and end date!", "Report Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (startDateChooser.getDate().after(endDateChooser.getDate())) {
            JOptionPane.showMessageDialog(this, "Start date must be before end date!", "Report Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        startDate = startDateChooser.getDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        endDate = endDateChooser.getDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        
        populateReportTableForRooms();
        populateReportTableForMaids();
        populateReservationReportTable();
        updateRevenue();
        updateExpenses();
    }

    private void populateReportTableForRooms() {
        roomTableModel.setRowCount(0);
        RoomManager roomManager = RoomManager.getInstance();
        for (String key : roomManager.getRoomData().keySet()) {
            Room room = roomManager.getRoom(key);
            int[] daysAndRevenue = Report.getRevenueAndWisitsForRoom(startDate, endDate, room);
            roomTableModel.addRow(new Object[]{
                room.getId(),
                room.getRoomNumber(),
                room.getRoomType().toString(),
                daysAndRevenue[0],
                daysAndRevenue[1]
            });
        }
    }

    private void populateReportTableForMaids() {
        maidTableModel.setRowCount(0);
        for (String key : EmployeeManager.getInstance().getEmployeeData().keySet()) {
            Employee employee = EmployeeManager.getInstance().getEmployee(key);
            if (employee.getRole().equals(UserRoles.MAID)) {
                Maid maid = (Maid) employee;
                int roomsCleaned = maid.roomsCleanedForPeriod(startDate, endDate);
                maidTableModel.addRow(new Object[]{
                    maid.getId(),
                    maid.getName(),
                    roomsCleaned
                });
            }
        }
    }

    private void populateReservationReportTable() {
        reservationStatusTableModel.setRowCount(0);
        EnumMap<ReservationStatus, Integer> report = Report.reservationReport(startDate, endDate);
        for (ReservationStatus status : report.keySet()) {
            reservationStatusTableModel.addRow(new Object[]{
            		"x",
                status.toString(),
                report.get(status)
            });
        }
    }

    private void updateRevenue() {
        int revenue = Report.getRevenue(startDate, endDate);
        revenueField.setText(String.valueOf(revenue));
    }

    private void updateExpenses() {
        int expenses = Report.getExpenses(startDate, endDate);
        expensesField.setText("-" + expenses);
    }
}