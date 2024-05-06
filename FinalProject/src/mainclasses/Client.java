package mainclasses;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import pojo.User;
import utility.Constants.UserType;

public class Client {

	private String hostName;
	private int port;
	private Socket socket;
	ObjectOutputStream clientOutputStream;
	ObjectInputStream clientInputStream;
	private int mainUserId;
	private UserType mainUserType;
	

	public int getMainUserId() {
		return mainUserId;
	}

	public void setMainUserId(int mainUserId) {
		this.mainUserId = mainUserId;
	}

	public UserType getMainUserType() {
		return mainUserType;
	}

	public void setMainUserType(UserType mainUserType) {
		this.mainUserType = mainUserType;
	}

	
	public Client(int port, String hostname) {
		this.port = port;
		this.hostName = hostname;
		
		try {
			connect();
			System.out.println("client Connected");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HomeScreen homeScreen = new HomeScreen(this);
		homeScreen.setVisible(true);
		
	}
	
	public void connect() throws UnknownHostException, IOException {
		System.out.println("host = " + this.hostName + "; port = " + this.port);

		this.socket = new Socket(this.hostName, this.port);
		this.clientOutputStream = new ObjectOutputStream(socket.getOutputStream());
		this.clientInputStream = new ObjectInputStream(socket.getInputStream());
		System.out.println("connected");
	}
	
	
	public static void main(String[] args) {
		
		int port = 8000;
		String hostname = "localhost";
		
		new Client(port, hostname);
		System.out.println("client main");
		

	}
	
	public Object performAction(Object obj) {
		try {
			connect();
			this.clientOutputStream.writeObject(obj);
			
			obj= this.clientInputStream.readObject();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return obj;
	}
}

