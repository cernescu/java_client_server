package serverPackage;

import java.net.*;
import java.util.ArrayList;
import java.io.*;

import serverPackage.ServerDataStreamsManager;

public class ServerManager implements Runnable {

	private ArrayList<Socket> sockets;
	private ServerSocket server = null;
	private ServerDataStreamsManager serverDataStreams = null;
	private ServerAuthManager serverAuthentication = null;

	public ServerManager(int port){
		
		sockets = new ArrayList<Socket>();
		
		System.out.println("Binding to port " + port + ", please wait...");
		try {
			server = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println("Usage: can't bind server to port...");
			System.out.println("StackTrace:");
			e.printStackTrace();
		}
		
		(new Thread(this)).start();				
	}

	@Override
	public void run() {
			System.out.println("Waiting for a client ...");
		while(true){
			try {
				sockets.add(server.accept());
				
				serverAuthentication = new ServerAuthManager(sockets.get(sockets.size()-1));
				
				if(sockets.size() >= 2)
				{serverDataStreams = new ServerDataStreamsManager(sockets.get(sockets.size()-2),
						sockets.get(sockets.size()-1));
				
				(new Thread(serverDataStreams)).start();

				System.out.println("Client accepted: " + sockets.get(sockets.size()-1));
				System.out.println("All clients connected: " + sockets.toString());}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.err.println("Backend: Acceptance Error... " + e);
				System.err.println("StackTrace:");
				e.printStackTrace();
			}
		}
	}
}
