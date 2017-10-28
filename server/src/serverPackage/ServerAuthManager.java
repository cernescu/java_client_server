package serverPackage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerAuthManager {
	
	//TODO ServerClientRegistrationsManager
	
	private static String username;
	private static String password;
	private static int loginAttempts;
	
	public enum AuthState {
		
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
					username = fromClient.readUTF();
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
				
				if(usernameExists(username)){
					System.out.println("Usage: username accepted...");
					return AuthState.PROMPT_FOR_PASSWORD;
				}
				else
					System.out.println("Usage: username does not exist...");
				
				return this;
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
					password = fromClient.readUTF();
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
				if (isPasswordCorrect(password) && loginAttempts <= 2){
					loginAttempts = 0;
					return AuthState.AUTH_SUCCESSFUL;
				}
				else
					if(!isPasswordCorrect(password))
						loginAttempts++;
				
				if(loginAttempts > 2){
					loginAttempts = 0;
					return AuthState.AUTH_FAILED;
					}
				
				return AuthState.PROMPT_FOR_PASSWORD;
				}
			},
			
		AUTH_SUCCESSFUL{
			@Override
			AuthState authProcess(String... args){
				
				username = null;
				password = null;
				
				System.out.println("Usage: user validated...");
				System.out.println("Usage: login accepted...");
				
				return AuthState.AUTH_PROCESS_DONE;
				}
			},
		
		AUTH_FAILED{
			@Override
			AuthState authProcess(String... args){
				
				username = null;
				password = null;
				
				return AuthState.AUTH_PROCESS_DONE;
				}
			},
		
		AUTH_PROCESS_DONE{
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
		
		username = null;
		password = null;
		
		loginAttempts = 0;
		
		state = AuthState.USER_CONNECTED;
		
		startAuthProcess();
				
		}		
	
	private void startAuthProcess(){
		
		while(!state.equals(AuthState.AUTH_PROCESS_DONE)){
			
			state=state.authProcess();
			
			if(state.equals(AuthState.AUTH_FAILED)){
				try {
			
					socket.close();
					state=AuthState.AUTH_PROCESS_DONE;
				
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private static boolean usernameExists(String username){
		
		return true;
	}
	
	private static boolean isPasswordCorrect(String password){
		
		return false;
	}
	
}

