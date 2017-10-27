package serverPackage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import serverPackage.ServerAuthManager.AuthState;

public class ServerAuthManager {
	
	public enum AuthState {
		
		USER_CONNECTED {
			@Override
			AuthState authProcess(String... args ){
			
				return AuthState.PROMPT_FOR_USERNAME;
				}
		},
		
		PROMPT_FOR_USERNAME{
			@Override
			AuthState authProcess(String... args){
			
				return AuthState.CHECK_USERNAME;
				}
		},
		
		CHECK_USERNAME{
			@Override
			AuthState authProcess(String... args){
				
				return AuthState.CHECK_USERNAME;
				}
			},
			
		PROMPT_FOR_PASSWORD{
			@Override
			AuthState authProcess(String... args){
				
				return AuthState.CHECK_USERNAME;
				}
			},
			
		CHECK_PASSWORD{
			@Override
			AuthState authProcess(String... args){
				
				return AuthState.CHECK_USERNAME;
				}
			},
			
		AUTH_SUCCESSFULL{
			@Override
			AuthState authProcess(String... args){
				
				return AuthState.CHECK_USERNAME;
				}
			};
			
			abstract AuthState authProcess(String... args);
		}
		

	
	Socket socket;
	DataInputStream fromClient = null;
	DataOutputStream toClient = null;
	
	private AuthState state;
	
	public ServerAuthManager(Socket socket) throws IOException{
		
		this.socket=socket;
		
		fromClient = new DataInputStream(socket.getInputStream());
		toClient = new DataOutputStream(socket.getOutputStream());	
				
		}		
}

