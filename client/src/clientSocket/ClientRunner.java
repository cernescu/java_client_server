package clientSocket;

import clientSocket.ClientSocket;
import gUserInterface.*;

public class ClientRunner {
	private static ClientSocket client = null;
	private static GUI gui = null;
	
	public static void main(String[] args) {
		client = new ClientSocket();
		gui = new GUI();
		
		Thread clientThread = new Thread(client);
		clientThread.start();
		
		Thread guiThread = new Thread(gui);
		guiThread.start();	
	}
}