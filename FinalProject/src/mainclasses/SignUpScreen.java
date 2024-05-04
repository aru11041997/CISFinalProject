package mainclasses;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SignUpScreen extends JFrame implements ActionListener {

	
	private JLabel lblUserName;
	private JLabel lblFirstName;
	private JLabel lblLastName;
	private JLabel lblPassword;
	private JLabel lblConfirmPassword;
	
	private JTextField txtUserName;
	private JTextField txtFirstName;
	private JTextField txtLastName;
	private JTextField txtPassword;
	private JTextField txtConfirmPassword;
	
	JButton btnSignUp;
	
	
	public SignUpScreen() {
		
		initializeUIComponents();
		doTheLayout();
		
		this.btnSignUp.addActionListener(this);
		
		this.setTitle("SignUp Screen");
        //this.setSize(400, 400);
		this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null); // Center the window
        
	}
	
	public void initializeUIComponents() {
		
		this.lblFirstName = new JLabel("First Name");
		this.lblLastName = new JLabel("Last Name");
		this.lblUserName = new JLabel("Username");
		this.lblPassword = new JLabel("Password");
		this.lblConfirmPassword = new JLabel("Confirm Password");
		
		
		this.txtFirstName = new JTextField(10);
		this.txtLastName = new JTextField(10);
		this.txtUserName = new JTextField(10);
		this.txtPassword = new JTextField(10);
		this.txtConfirmPassword = new JTextField(10);
		
		this.btnSignUp = new JButton("Sign Up");
		
	}
	
	public void doTheLayout() {
		
		final JPanel row1 = new JPanel();
		final JPanel row2 = new JPanel();
		final JPanel row3 = new JPanel();
		final JPanel row4 = new JPanel();
		final JPanel row5 = new JPanel();
		final JPanel row6 = new JPanel();
		final JPanel center = new JPanel();
		
		row1.add(this.lblFirstName);
		row1.add(this.txtFirstName);
		
		row2.add(this.lblLastName);
		row2.add(this.txtLastName);
		
		row3.add(this.lblUserName);
		row3.add(this.txtUserName);
		
		row4.add(this.lblPassword);
		row4.add(this.txtPassword);
		
		row5.add(this.lblConfirmPassword);
		row5.add(this.txtConfirmPassword);
		
		row6.add(this.btnSignUp);
		
		center.setLayout(new GridLayout(6,1));
		center.add(row1);
		center.add(row2);
		center.add(row3);
		center.add(row4);
		center.add(row5);
		center.add(row6);
		
		this.setLayout(new BorderLayout());
		this.add(center, BorderLayout.CENTER);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == this.btnSignUp) {
			SignUpButtonClicked();
		}
	}

	public void SignUpButtonClicked() {
		//TODO
		System.out.println("SignUpButtonClicked");
	}
}
