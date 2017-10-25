package serverPackage;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerInputStream implements Runnable {

	DataInputStream fromClient = null;
	Socket socket;
	
	public ServerInputStream(Socket socket) throws IOException{
		fromClient= new DataInputStream(socket.getInputStream());
		this.socket=socket;
	}
	
	@Override
	public void run() {
	String fromClientMessage;
		
		try {
			while ((fromClientMessage = fromClient.readUTF()) != null) {
				if ( fromClientMessage.equals("hello")) {
					System.err.println("Received handshake from " + socket.getInetAddress());
				}
				else {
					System.out.println("Received from " + socket.getInetAddress() + " : " + fromClientMessage);
				}
			}
		} catch (IOException e) {
			System.err.println("Usage: message from client error...");
			System.err.println("StackTrace:");
			e.printStackTrace();
		}
	}

}
