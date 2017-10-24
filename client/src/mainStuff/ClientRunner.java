package mainStuff;

import mainStuff.ClientSocket;

public class ClientRunner {
	private static ClientSocket client = null;
	
	public static void main(String[] args) {
		client = new ClientSocket();
		client.run();
	}
}