package serverPackage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import serverPackage.ServerAuthManager.AuthState;

public class ServerAuthManager {
	
	public enum AuthState {
		
		//TODO encryption for passwords
		//TODO key or something related to check users
		
		USER_CONNECTED {
			@Override
			AuthState authProcess(String... args ){
				System.out.println("Usage: authentication process started...");
				
				return AuthState.PROMPT_FOR_USERNAME;
				}
		},
		
		PROMPT_FOR_USERNAME{
			@Override
			AuthState authProcess(String... args){
				System.out.println("Usage: user prompted for username...");
				
				try {
					toClient.writeUTF("Please enter your username:");
				} catch (IOException e) {
					System.err.println("[" + this.toString() + "]Usage: message sending/receiving error...");
					System.err.println("StackTrace:");
					e.printStackTrace();
				}
				
				try {
					//TODO regex to check username consistency
					//TODO username global var
					String username = fromClient.readUTF();
					System.out.println(username);
				} catch (IOException e) {
					System.err.println("[" + this.toString() + "]Usage: message sending/receiving error...");
					System.err.println("StackTrace:");
					e.printStackTrace();
				}
				
				return AuthState.CHECK_USERNAME;
				}
		},
		
		CHECK_USERNAME{
			@Override
			AuthState authProcess(String... args){
				System.out.println("Usage: username is being checked...");
				System.out.println("Usage: please wait...");
				
				//TODO check if username exists in database
				
				return AuthState.PROMPT_FOR_PASSWORD;
				}
			},
			
		PROMPT_FOR_PASSWORD{
			@Override
			AuthState authProcess(String... args){
				System.out.println("Usage: user prompted for password...");	
							
				try {
					toClient.writeUTF("Please enter your password:");
				} catch (IOException e) {
					System.err.println("[" + this.toString() + "]Usage: message sending/receiving error...");
					System.err.println("StackTrace:");
					e.printStackTrace();
				}
				
				try {
					//TODO password saved in global var to be checked in the next state
					String password = fromClient.readUTF();
					System.out.println(password);
				} catch (IOException e) {
					System.err.println("[" + this.toString() + "]Usage: message sending/receiving error...");
					System.err.println("StackTrace:");
					e.printStackTrace();
				}
				
				return AuthState.CHECK_PASSWORD;
				}
			},
			
		CHECK_PASSWORD{
			@Override
			AuthState authProcess(String... args){
				
				/*TODO check password stored with the one from
				 * database that corresponds to stored username
				 * earlier checked
				 */
				
				//TODO if password not good return AuthState.PROMPT_FOR_PASSWORD
				
				System.out.println("Usage: user validated...");
				System.out.println("Usage: login accepted...");
				
				return AuthState.AUTH_SUCCESSFUL;
				}
			},
			
		AUTH_SUCCESSFUL{
			@Override
			AuthState authProcess(String... args){
				
				return this;
				}
			};
			
			abstract AuthState authProcess(String... args);
		}
			
	private Socket socket;
	private static DataInputStream fromClient = null;
	private static DataOutputStream toClient = null;
	
	private AuthState state;
	
	public ServerAuthManager(Socket socket) throws IOException{
		
		this.socket=socket;
		
		fromClient = new DataInputStream(socket.getInputStream());
		toClient = new DataOutputStream(socket.getOutputStream());	
		
		state = AuthState.USER_CONNECTED;
		
		startAuthProcess();
				
		}		
	
	private void startAuthProcess(){
		
		while(!state.equals(state.AUTH_SUCCESSFUL)){
			state=state.authProcess();
		}
	}
}

