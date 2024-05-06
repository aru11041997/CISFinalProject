package mainclasses;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import pojo.User;
import utility.Constants.UserType;

public class HomeScreen extends JFrame implements ActionListener {

	// login attributes
	private JLabel lblUsername;
	private JLabel lblPassword;
	private JLabel lblUserType;
	private JLabel lblSignUpText;
	private JTextField txtUsername;
	private JTextField txtPassword;

//	JComboBox cmbUserType;

	private JButton btnLogin;
	private JButton btnSignUp;

	Client client;
	User user;

	public HomeScreen(Client client) {

		this.client = client;

		initializeUIComponents();
		doTheLayout();

		this.btnLogin.addActionListener(this);
		this.btnSignUp.addActionListener(this);

		this.setTitle("Home Screen");
		// this.setSize(600, 600);
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

//		String userTypes[] = {"","Customer","Chef", "DBA"};
//		this.cmbUserType = new JComboBox<String>(userTypes);
//		this.cmbUserType.setSelectedIndex(1);

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

//		row3.add(this.lblUserType);
//		row3.add(this.cmbUserType);

		row4.add(this.btnLogin);

		row5.add(this.lblSignUpText);
		row5.add(this.btnSignUp);

		center.setLayout(new GridLayout(4, 1));
		center.add(row1);
		center.add(row2);
//		center.add(row3);
		center.add(row4);
		center.add(row5);

		this.setLayout(new BorderLayout());
		this.add(new JPanel(), BorderLayout.NORTH);
		this.add(center, BorderLayout.CENTER);
		this.add(bottom, BorderLayout.SOUTH);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == this.btnLogin) {
			LoginButtonClicked();
		} else if (e.getSource() == this.btnSignUp) {
			SignUpScreen signup = new SignUpScreen(this.client);
			signup.setVisible(true);
			dispose();
		}

	}

	public void LoginButtonClicked() {
		System.out.println("LoginButtonClicked");
		try {
			if (!validateFeilds())
				return;
			String username = this.txtUsername.getText();
			String password = this.txtPassword.getText();
			// TODO
			// validations

			this.user = new User(0, username, "", "", password, null, 2, "", null);

			this.user = (User) this.client.performAction(this.user);

			if (this.user != null && this.user.getOptType() > 0) {
				System.out.println("Message = " + this.user.getMessage());

				UserType uType = this.user.getUserType();
				// System.out.println(uType.toString());

				this.client.setMainUserId(this.user.getUserId());
				this.client.setMainUserType(uType);

				if (uType.equals(UserType.ADMIN)) {
					DbaScreen dbaScreen = new DbaScreen(this.client);
					dbaScreen.setVisible(true);
					dispose();
				} else if (uType.equals(UserType.CHEF)) {
					ChefScreen chefScreen = new ChefScreen(this.client);
					chefScreen.setVisible(true);
					dispose();
				} else if (uType.equals(UserType.USER)) {
					CustomerScreen customerScreen = new CustomerScreen(this.client);
					customerScreen.setVisible(true);
					dispose();
				}

			} else {
				JOptionPane.showMessageDialog(null, "Invalid Credentials. Please try again");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean validateFeilds() {
		String username = this.txtUsername.getText();
		if (username.isBlank()) {
			JOptionPane.showMessageDialog(null, "Please enter the username", null, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		String password = this.txtPassword.getText();
		if (password.isBlank()) {
			JOptionPane.showMessageDialog(null, "Please enter the username", null, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
	}

}
