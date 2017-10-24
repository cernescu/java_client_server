package mainStuff;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class SocketSender implements Runnable{
	private DataOutputStream out_ = null;

	public SocketSender(DataOutputStream out) {
		out_ = out;
	}
	
	private void startSending() {
		BufferedReader readFromUser = new BufferedReader(new InputStreamReader(System.in));
		String fromUser;
		try {
			while(true) {
				System.out.print("Client: ");
				fromUser = readFromUser.readLine();
	            if (fromUser != null) {
	            	if (fromUser.equals("done")) {
		            	break;
		            }
	                out_.writeUTF(fromUser);
	            }
			}
		} catch (IOException e) {
			System.err.println("Exception in communication!");
	        System.exit(1);
		}
	}
	@Override
	public void run() {
		startSending();		
	}
}
