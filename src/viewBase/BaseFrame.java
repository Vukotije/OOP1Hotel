package viewBase;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import entitiesManager.HandleManagers;

public class BaseFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	public BaseFrame() {
		setTitle("VuHotel ");
		setIconImage(new ImageIcon("assets/pictures/VuHotelLogoNoText.png").getImage());
		setSize(500, 400);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(null);
        setVisible(true);
        
		addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	int answer = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
				if (answer == JOptionPane.YES_OPTION) {
			    	HandleManagers.writeData();
			        dispose();
				}
		    }
		  });
    }
	

}
