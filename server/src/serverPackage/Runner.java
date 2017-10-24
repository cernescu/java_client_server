package serverPackage;

import serverPackage.ServerSideSocket;

public class Runner {
	
	public static ServerSideSocket server = null;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Usage: Server is initialising...");
		if (args.length != 1)
			System.out.println("Usage: Server port not specified...");
			//server = new ServerSideSocket(Integer.parseInt("8031"));
		else
			server = new ServerSideSocket(Integer.parseInt(args[0]));
	}

}
