package serverPackage;

import serverPackage.ServerManager;

public class ServerRunner {
	
	public static ServerManager serverManager= null;

	public static void main(String[] args) {
		// TODO Auto-generated method stub		
		serverManager = new ServerManager(8031);
		
	}

}
