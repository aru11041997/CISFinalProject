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

import doa.ItemDetailDoa;
import doa.UserDoa;
import pojo.ItemDetail;
import pojo.Order;
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
	private UserDoa userDoa;
	private ItemDetailDoa itemDetailDoa;

	Server(int port) {
		this.port = port;
		hashCode = new HashCode();
		cardValidation = new CardValidation();
		userDoa = new UserDoa();
		itemDetailDoa = new ItemDetailDoa();

		//
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
					switch (user.getOptType()) {
					case 1:
						user = userDoa.registration(conn, user, hashCode);
						object = user;
						break;

					case 2:
						user = userDoa.login(conn, user, hashCode);
						object = user;
						break;
					}
				}

				if (object instanceof ItemDetail) {
					ItemDetail menu = (ItemDetail) object;
					switch (menu.getOptType()) {
					case 1:
						List<ItemDetail> menus = itemDetailDoa.getAllItem(conn, menu);
						object = menus;
						break;

					case 2:
						menu = itemDetailDoa.addItem(conn, menu);
						object = menu;
						break;

					case 3:
						menu = itemDetailDoa.updateItem(conn, menu);
						object = menu;
						break;

					case 4:
						menu = itemDetailDoa.deleteItem(conn, menu);
						object = menu;
						break;
					}
				}

				if (object instanceof Order) {
					Order order = (Order) object;
					switch (order.getOptType()) {
					case 1:
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
