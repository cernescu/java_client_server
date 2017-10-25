package serverPackage;

import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;

import serverPackage.ServerInputStream;

public class ServerManager implements Runnable {

	private ArrayList<Socket> sockets;
	private ServerSocket server = null;
	public ServerInputStream serverInputStream = null;
	public ServerOutputStream serverOutputStream = null;

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
				
				serverInputStream = new ServerInputStream(sockets.get(sockets.size()-1));
				serverOutputStream = new ServerOutputStream(sockets.get(sockets.size()-1));
				
				(new Thread(serverInputStream)).start();
				
				System.out.println("Client accepted: " + sockets.get(sockets.size()-1));
				System.out.println("All clients connected: " + sockets.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.err.println("Backend: Acceptance Error... " + e);
				System.err.println("StackTrace:");
				e.printStackTrace();
			}
		}
	}

	//TODO Make pair of client sockets and show message only between them
	public HashMap<Socket,Socket> getSockets(){
		
		HashMap<Socket,Socket> clients = null;
		clients.put(sockets.get(0), sockets.get(0));
		
		return clients;		
	}
}
