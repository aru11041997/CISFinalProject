package mainclasses;

public class Client {

	Client() {
		System.out.println("client constructor");
	}
	public static void main(String[] args) {
		System.out.println("client main");
		HomeScreen homeScreen = new HomeScreen();
		homeScreen.setVisible(true);

	}
}

