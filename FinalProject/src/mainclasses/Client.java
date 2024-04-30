package mainclasses;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Client {

	JFrame mainFrame = new JFrame("Login");

	// login attributes
	JLabel lblUsername;
	JLabel lblPassword;
	JTextField txtUsername;
	JTextField txtPassword;
	JButton btnLogin;
	JButton btnSignUp;
	
	// signup attributes
	JLabel lblUsernameSignUp;
	JLabel lblPasswordSignUp;
	JLabel lblFirstNameSignUp;;
	JLabel lblLastNameSignUp;

	
	
	Client() {
		initializeUI();
	}

	public void initializeUI() {

	}

	public static void main(String[] args) {
		Client client = new Client();
	}
}
