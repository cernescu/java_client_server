package serverPackage;

import java.net.*;
import java.io.*;

public class ServerSideSocket implements Runnable {

	private Socket socket = null;
	private ServerSocket server = null;
	private Thread thread = null;
	private DataInputStream streamIn = null;
	
	public ServerSideSocket(int port){
		
		System.out.println("Binding to port :" + port + ", please wait...");
		try {
			server = new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Usage: can't bind server to port...");
			System.out.println("StackTrace:");
			e.printStackTrace();
		}
		
		System.out.println("Server started: " + server);
		start();		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (thread != null)
		{
			System.out.println("Waiting for a client ...");
		
			try {
				socket = server.accept();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Backend: Acceptance Error... " + e);
				System.out.println("StackTrace:");
				e.printStackTrace();
			}
		
			System.out.println("Client accepted: " + socket);
		
			try {
				open();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
			boolean done = false;
		
			while (!done)
			{
				try {
					String line = streamIn.readUTF();
					System.out.println(line);
					done = line.equals(".bye");
				}catch(IOException e){
					done = true;
					System.out.println("Backend: chatting stopped...");
					//e.printStackTrace();
				}
			}
		
			try {
				close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void start(){
		if(thread==null){
			thread = new Thread(this);
			thread.start();
		}
	}
	
	public void stop() throws InterruptedException{
		if(thread!=null){
			thread.join();
			thread = null;
		}
	}
	
	public void open() throws IOException{
		streamIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
	}
	
	public void close() throws IOException{
		if (socket!=null){
			socket.close();
		}
		if (streamIn!=null){
			streamIn.close();
		}
	}
}
