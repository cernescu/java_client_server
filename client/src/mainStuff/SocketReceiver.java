package mainStuff;

import java.io.DataInputStream;
import java.io.IOException;

public class SocketReceiver implements Runnable{
	private DataInputStream in_ = null;
	
	public SocketReceiver(DataInputStream in) {
		in_ = in;
	}
	
	private void startReceiving() {
		String fromServer;
		
		try {
			while ((fromServer = in_.readUTF()) != null) {
				if ( fromServer.equals("hello")) {
					System.err.println("Received handshake from server!");
				}
				else {
					System.err.println("Received from server: " + fromServer);
				}
			}
		} catch (IOException e) {
			System.err.println("Exception in communication!");
	        System.exit(1);
		}
	}
	
	
	@Override
	public void run() {
		startReceiving();
	}

}
