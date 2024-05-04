package mainclasses;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ChefScreen extends JFrame implements ActionListener{

	public ChefScreen() {
		// Create layout for Chef Screen
		
		initializeUIComponents();
		doTheLayout();
		
		
        

        this.setTitle("Chef Screen");
        this.setSize(300, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null); // Center the window

       
	}
	
	public void initializeUIComponents() {
		
	}
	
	public void doTheLayout() {
		JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Create a label for the Chef Screen
        JLabel label = new JLabel("Chef Screen", SwingConstants.CENTER);
        panel.add(label, BorderLayout.CENTER);
        
        // Add the panel to the ChefScreen
        this.add(panel);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
