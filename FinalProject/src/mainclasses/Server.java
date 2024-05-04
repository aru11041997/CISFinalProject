package mainclasses;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import pojo.Menu;
import pojo.User;
import utility.CardValidation;
import utility.Constants.MenuType;
import utility.Constants.UserType;
import utility.HashCode;

public class Server {
	private final int port;
	private Connection conn;
	private HashCode hashCode;
	private CardValidation cardValidation;
	private ServerSocket serverSocket;

	Server(int port) {
		this.port = port;
		hashCode = new HashCode();
		cardValidation = new CardValidation();
		initialize();
	}

	private void initialize() {
		// create the server
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("Server started. Listening on port " + port);

			// Connect to your database using your credentials
			final String url = "jdbc:mysql://BusCISMySQL01:3306/finalProject"; // team db team29DB
			final String username = "C836817374";
			final String password = "c611c!17374";
			conn = DriverManager.getConnection(url, username, password);
			System.out.println("Connected to database");

			// Create a statement object

			// loops for ever waiting for the client connection requests
			// create a thread for each client connection request using Runnable class
			// HandleAClient
			while (true) {
				final Socket socket = serverSocket.accept();
				System.out.println("Client connected from " + socket.getInetAddress().getHostName());

				// Create a thread for each client connection request using Runnable class
				final HandleAClient task = new HandleAClient(socket);
				new Thread(task).start();
			}
		} catch (final Exception e) {
			e.printStackTrace();
			return;
		}
	}

	public static void main(String[] args) {
		new Server(8000);
	}

	class HandleAClient implements Runnable {
		private final Socket socket; // A connected socket

		/** Construct a thread */
		public HandleAClient(final Socket socket) {
			this.socket = socket;
		}

		/** Run a thread */
		public void run() {

			// write the code to call a proper method to process the client request
			try {
				final ObjectInputStream inputFromClient = new ObjectInputStream(socket.getInputStream());
				final ObjectOutputStream outputToClient = new ObjectOutputStream(socket.getOutputStream());
				Object object = inputFromClient.readObject();
				if (object instanceof User) {
					User user = (User) object;
					PreparedStatement preparedStatement = null;
					ResultSet resultSet = null;
					int count;
					switch (user.getOptType()) {
					case 1:
						preparedStatement = conn
								.prepareStatement("INSERT INTO USER(username, firstname, lastname, password, usertype) "
										+ "VALUES(?,?,?,?,?)");
						preparedStatement.setString(1, user.getUsername());
						preparedStatement.setString(2, user.getFirstName());
						preparedStatement.setString(3, user.getLastName());
						preparedStatement.setString(4, hashCode.getHashCode(user.getPassword()));
						preparedStatement.setString(5, user.getUserType().toString());
						count = preparedStatement.executeUpdate();
						if (count == 1) {
							user.setMessage("Insert Successfully");
							object = user;
						} else {
							user.setMessage("Unable to insert.");
							user.setOptType(-1);
							object = user;
						}
						preparedStatement.close();
						break;

					case 2:
						preparedStatement = conn
								.prepareStatement("SELECT * FROM USER WHERE username = ? AND password = ?");
						preparedStatement.setString(1, user.getUsername());
						preparedStatement.setString(2, hashCode.getHashCode(user.getPassword()));
						resultSet = preparedStatement.executeQuery();
						if (resultSet.next()) {
							user.setFirstName(resultSet.getString("firstname"));
							user.setLastName(resultSet.getString("lastname"));
							user.setUserId(resultSet.getInt("userid"));

							String userType = resultSet.getString("usertype");
							UserType type = null;
							if (userType.equals("ADMIN"))
								type = UserType.ADMIN;
							else if (userType.equals("USER"))
								type = UserType.USER;
							else if (userType.equals("CHEF"))
								type = UserType.CHEF;

							user.setUserType(type);
							user.setMessage("user found");
							object = user;
						} else {
							user.setMessage("Invalid credentails.");
							user.setOptType(-1);
							object = user;
						}
						preparedStatement.close();
						break;
					}
				}

				if (object instanceof Menu) {
					Menu menu = (Menu) object;
					PreparedStatement preparedStatement = null;
					ResultSet resultSet = null;
					int count;
					switch (menu.getOptType()) {
					case 1:
						preparedStatement = conn.prepareStatement("SELECT * FROM menu");
						resultSet = preparedStatement.executeQuery();
						List<Menu> menus = new ArrayList<>();
						while (resultSet.next()) {
							Menu menu2 = new Menu();
							menu2.setMenuId(resultSet.getInt("menuid"));
							menu2.setName(resultSet.getString("name"));
							String menuType = resultSet.getString("type");

							MenuType type = null;
							if (menuType.equals("VEG"))
								type = MenuType.VEG;
							else if (menuType.equals("NONVEG"))
								type = MenuType.NONVEG;
							else if (menuType.equals("VEGAN"))
								type = MenuType.VEGAN;
							menu2.setMenuType(type);

							menu2.setDescription(resultSet.getString("description"));
							menus.add(menu2);
						}
						object = menus;
						preparedStatement.close();
						break;

					case 2:
						preparedStatement = conn
								.prepareStatement("INSERT INTO MENU(name, type, description) VALUES(?,?,?)");
						preparedStatement.setString(1, menu.getName());
						preparedStatement.setString(2, menu.getMenuType().toString());
						preparedStatement.setString(3, menu.getDescription());
						count = preparedStatement.executeUpdate();
						if (count == 1) {
							menu.setMessage("Insert Successfully");
							object = menu;
						} else {
							menu.setMessage("Unable to insert.");
							menu.setOptType(-2);
							object = menu;
						}
						preparedStatement.close();
						object = menu;
						break;

					case 3:
						preparedStatement = conn.prepareStatement(
								"UPDATE MENU SET name = ?, type = ?, description = ? WHERE menuid = ?");
						preparedStatement.setString(1, menu.getName());
						preparedStatement.setString(2, menu.getMenuType().toString());
						preparedStatement.setString(3, menu.getDescription());
						preparedStatement.setInt(4, menu.getMenuId());
						count = preparedStatement.executeUpdate();
						if (count == 1) {
							menu.setMessage("Update Successfully");
							object = menu;
						} else {
							menu.setMessage("Unable to update.");
							menu.setOptType(-3);
							object = menu;
						}
						preparedStatement.close();
						object = menu;
						break;

					case 4:
						preparedStatement = conn.prepareStatement("DELETE FROM MENU WHERE menuid = ?");
						preparedStatement.setInt(1, menu.getMenuId());
						count = preparedStatement.executeUpdate();
						if (count == 1) {
							menu.setMessage("Delet Successfully");
							object = menu;
						} else {
							menu.setMessage("Unable to delete.");
							menu.setOptType(-4);
							object = menu;
						}
						preparedStatement.close();
						object = menu;
						break;
					}
				}

				outputToClient.writeObject(object);
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
	}// end of class Runnable
}
