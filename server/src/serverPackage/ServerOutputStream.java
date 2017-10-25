package serverPackage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerOutputStream implements Runnable{

	DataOutputStream toClient = null;
	
	public ServerOutputStream(Socket socket) throws IOException{
		toClient= new DataOutputStream(socket.getOutputStream());
	}
	
	@Override
	public void run() {
		//TODO Send message only to the other client in the conversation
		
	}
	
	
}
