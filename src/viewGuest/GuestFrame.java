package viewGuest;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JTabbedPane;

import entitiesClasses.Guest;
import entitiesManager.GuestManager;
import viewBase.BaseFrame;

public class GuestFrame extends BaseFrame {

    private static final long serialVersionUID = 1L;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                GuestFrame frame = new GuestFrame(GuestManager.getInstance().getGuestByName("Milica", "Milic"));
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public GuestFrame(Guest guest) {
    	System.out.println("GuestFrame.GuestFrame()");
        setTitle(getTitle() + "| Guest | " + guest.getUsername());
        setSize(1000, 600);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setOpaque(true);
        tabbedPane.setBackground(new Color(255, 128, 255));

        getContentPane().add(tabbedPane, BorderLayout.CENTER);
        
        GuestReservationsPanel guestReservationsPanel = new GuestReservationsPanel(guest);
        tabbedPane.addTab("My Reservations", null, guestReservationsPanel, null);
        }
}