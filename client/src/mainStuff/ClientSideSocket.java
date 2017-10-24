package mainStuff;

import java.io.*;
import java.net.*;

public class ClientSideSocket implements Runnable{

	private static String DEFAULT_HOST = "localhost";//"192.168.0.169";
	private static int DEFAULT_PORT = 8031;
	private static String DEFAULT_HANDSHAKE = "hello";
	
	private String host_;
	private int port_;
	
	private DataOutputStream out_;
	private DataInputStream in_;
	
	private Socket socket_ = null;
	
	public ClientSideSocket(String[] args) {
		if (args.length != 1)
		{
			System.err.println("Running with no arguments. Assuming default hostName and Port!");
			host_ = DEFAULT_HOST;
			port_ = DEFAULT_PORT;
		}
	}
	
	private void createSocket()
	{
	    try {
	    	socket_ = new Socket(host_, port_);
	    	System.out.println("Connected to: " + socket_.toString());
	    } catch (UnknownHostException e) {
	        System.err.println("Don't know about host " + host_);
	        System.exit(1);
	    } catch (IOException e) {
	        System.err.println("Couldn't get I/O for the connection to " +
	        		host_);
	        System.exit(1);
	    }
	}
	
	private void createIOStreams() {
		try {
			in_ = new DataInputStream(socket_.getInputStream());
			out_ = new DataOutputStream(socket_.getOutputStream());
		}
		catch(IOException e) {
			System.err.println("I/O exception for streams!");
	        System.exit(1);
		}
	}
	
	private void setup() {
		createSocket();
		createIOStreams();
	}
	
	private void startReceiving() {
		String fromServer;
		String fromUser = "Handshake acknowledged!";
		
		try {
			while ((fromServer = in_.readUTF()) != null) {
				System.err.println("Received from server: " + fromServer);
				if ( fromServer.equals(DEFAULT_HANDSHAKE)) {
					System.err.println("Received handshake from server!");
					out_.writeUTF(fromUser);
				}
			}
		} catch (IOException e) {
			System.err.println("Exception in communication!");
	        System.exit(1);
		}
	}
	
	private void startSending() {
		
	}

	@Override
	public void run() {
		setup();
		startReceiving();
		startSending();
		// TODO Auto-generated method stub
	}
}