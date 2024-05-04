package pojo;

import utility.Constants.UserType;

public class User extends BaseAttributes {
	private int userId;
	private String username;
	private String firstName;
	private String lastName;
	private String password;
	private UserType userType;

	public User() {
		super();
	}

	public User(int userId, String username, String firstName, String lastName, String password) {
		super();
		this.userId = userId;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public UserType getUserType() {
		return userType;
	}
}
