package viewMaid;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JTabbedPane;

import entitiesClasses.Maid;
import entitiesManager.EmployeeManager;
import viewBase.BaseFrame;

public class MaidFrame extends BaseFrame {

    private static final long serialVersionUID = 1L;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MaidFrame frame = new MaidFrame((Maid) EmployeeManager.getInstance().getEmployeeByName("Jana", "Janic"));
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public MaidFrame(Maid maid) {
        setTitle(getTitle() + "| Maid | " + maid.getUsername());
        setSize(900, 500);
        setLocationRelativeTo(null);
        
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setOpaque(true);
        tabbedPane.setBackground(new Color(153, 204, 204));
        getContentPane().add(tabbedPane, BorderLayout.CENTER);

        MaidRoomPanel myRoomsPanel = new MaidRoomPanel(maid);
        tabbedPane.addTab("My Rooms", null, myRoomsPanel, null);
        
    }
}
