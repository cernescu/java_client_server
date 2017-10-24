package mainStuff;

import java.io.*;
import java.net.*;

public class ClientSocket {
	private static String DEFAULT_HOST = "localhost";//"192.168.0.169";
	private static int DEFAULT_PORT = 8031;
	
	private String host_;
	private int port_;
	
	private DataOutputStream out_;
	private DataInputStream in_;
	
	private Socket socket_ = null;
	
	public ClientSocket(/*String host, int port*/) {
			//System.err.println("Running with no arguments. Assuming default hostName and Port!");
			host_ = DEFAULT_HOST;
			port_ = DEFAULT_PORT;
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
	
	public void run() {
		setup();
		SocketSender sender = new SocketSender(out_);
		SocketReceiver receiver = new SocketReceiver(in_);
		
		Thread senderThread = new Thread(sender);
		senderThread.start();
		
		Thread receiverThread = new Thread(receiver);
		receiverThread.start();
		// TODO Auto-generated method stub
	}
}
