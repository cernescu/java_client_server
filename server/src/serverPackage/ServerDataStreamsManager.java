package serverPackage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServerDataStreamsManager implements Runnable {

	DataInputStream fromClient = null;
	DataOutputStream toClient = null;
	Socket inputSocket;
	Socket outputSocket;
	
	public ServerDataStreamsManager(Socket inputSocket,Socket outputSocket) throws IOException{
		this.inputSocket=inputSocket;
		this.outputSocket=outputSocket;
		
		fromClient = new DataInputStream(inputSocket.getInputStream());
		toClient = new DataOutputStream(outputSocket.getOutputStream());
	}
	
	@Override
	public void run() {
	String fromClientMessage;
		try {
			while ((fromClientMessage = fromClient.readUTF()) != null) {
					System.out.println("Received from " + inputSocket + " : " + fromClientMessage);
					toClient.writeUTF(fromClientMessage);
			}
		} catch (IOException e) {
			System.err.println("[" + this.toString() + "]Usage: message sending/receiving error...");
			System.err.println("StackTrace:");
			e.printStackTrace();
		}
	}
}
