package pojo;

public class BaseAttributes {
	private int optType;
	private String message;

	public BaseAttributes() {
		super();
	}
	public BaseAttributes(int op, String msg) {
		this.optType = op;
		this.message = msg;
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

}
