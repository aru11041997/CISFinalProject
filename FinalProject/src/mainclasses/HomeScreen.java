package mainclasses;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class HomeScreen extends JFrame implements ActionListener {

	// login attributes
	JLabel lblUsername;
	JLabel lblPassword;
	JLabel lblUserType;
	JLabel lblSignUpText;
	JTextField txtUsername;
	JTextField txtPassword;

	JComboBox cmbUserType;
	
	JButton btnLogin;
	JButton btnSignUp;
	
	JButton btnChefScreen;
	JButton btnCustomerScreen;
	JButton btnDbaScreen;

	public HomeScreen() {

		initializeUIComponents();
		doTheLayout();
		
		this.btnChefScreen.addActionListener(this);
		this.btnCustomerScreen.addActionListener(this);
		this.btnDbaScreen.addActionListener(this);
		this.btnLogin.addActionListener(this);
		this.btnSignUp.addActionListener(this);
		
		
		this.setTitle("Home Screen");
        //this.setSize(600, 600);
		this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null); // Center the window
        
	}

	public void initializeUIComponents() {

		this.lblUsername = new JLabel("Username");
		this.lblPassword = new JLabel("Password");
		this.lblUserType = new JLabel("User Type");
		this.lblSignUpText = new JLabel("Don't have an account?");
		
		this.txtUsername = new JTextField(10);
		this.txtPassword = new JTextField(10);
		
		String userTypes[] = {"","Customer","Chef", "DBA"};
		this.cmbUserType = new JComboBox<String>(userTypes);
		this.cmbUserType.setSelectedIndex(1);
		
		this.btnChefScreen = new JButton("Chef Screen");
		this.btnCustomerScreen = new JButton("Customer Screen");
		this.btnDbaScreen = new JButton("DBA Screen");
		this.btnLogin = new JButton("Login");
		this.btnSignUp = new JButton("Sign Up");
		
		
	}
	
	public void doTheLayout() {
		final JPanel row1 = new JPanel();
		final JPanel row2 = new JPanel();
		final JPanel row3 = new JPanel();
		final JPanel row4 = new JPanel();
		final JPanel row5 = new JPanel();
		
		
		final JPanel center = new JPanel();
		final JPanel bottom = new JPanel();
		
		row1.add(this.lblUsername);
		row1.add(this.txtUsername);
		
		row2.add(this.lblPassword);
		row2.add(this.txtPassword);
		
		row3.add(this.lblUserType);
		row3.add(this.cmbUserType);
		
		row4.add(this.btnLogin);
		
		row5.add(this.lblSignUpText);
		row5.add(this.btnSignUp);
		
		center.setLayout(new GridLayout(5,1));
		center.add(row1);
		center.add(row2);
		center.add(row3);
		center.add(row4);
		center.add(row5);
		
		
		bottom.add(this.btnChefScreen);
		bottom.add(this.btnCustomerScreen);
		bottom.add(this.btnDbaScreen);
		
		
		this.setLayout(new BorderLayout());
		this.add(new JPanel(), BorderLayout.NORTH);
		this.add(center,  BorderLayout.CENTER);
		this.add(bottom, BorderLayout.SOUTH);
		
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this.btnChefScreen) {
			 ChefScreen chefScreen = new ChefScreen();
             chefScreen.setVisible(true);
             // Close the current HomeScreen
             dispose();
		}else if(e.getSource() == this.btnCustomerScreen) {
			
		}else if(e.getSource() == this.btnDbaScreen) {
			
		}else if(e.getSource() == this.btnLogin) {
			
		}else if(e.getSource() == this.btnSignUp) {
			
		}
			
	}

}
