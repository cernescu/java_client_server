package mainStuff;

import mainStuff.ClientSideSocket;

public class ClientRunner {
	private static ClientSideSocket client = null;
	
	public static void main(String[] args) {
		
		client = new ClientSideSocket(args);
		client.run();
	}
}
