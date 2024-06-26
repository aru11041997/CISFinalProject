package pojo;

import java.io.Serializable;

import utility.Constants.UserType;

public class BaseAttributes implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int optType;
	private String message;
	private int mainUserId;
	private UserType mainUserType;

	public BaseAttributes() {
		super();
	}

	public BaseAttributes(int op, String msg, int userId, UserType type) {
		this.optType = op;
		this.message = msg;
		this.mainUserId = userId;
		this.mainUserType = type;
	}

	public int getOptType() {
		return optType;
	}

	public void setOptType(int optType) {
		this.optType = optType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setMainUserId(int mainUserId) {
		this.mainUserId = mainUserId;
	}

	public int getMainUserId() {
		return mainUserId;
	}

	public void setMainUserType(UserType mainUserType) {
		this.mainUserType = mainUserType;
	}

	public UserType getMainUserType() {
		return mainUserType;
	}

	@Override
	public String toString() {
		return "BaseAttributes [optType=" + optType + ", message=" + message + "]";
	}

}
