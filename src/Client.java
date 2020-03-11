import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements Runnable {
	
	public static BufferedReader outputFromServer;
	public static PrintStream outputForServer;
	public static BufferedReader inputFromKeyboard;

	public static void main(String[] args) {
		
		try {
			Socket socketForCommunication = new Socket("localhost", 6666);
			
			outputFromServer = new BufferedReader(new InputStreamReader(socketForCommunication.getInputStream()));
			outputForServer = new PrintStream(socketForCommunication.getOutputStream());
			inputFromKeyboard = new BufferedReader(new InputStreamReader(System.in));
			
			new Thread(new Client()).start();
			String messageFromServer;
			
			while(true) {
				messageFromServer = outputFromServer.readLine();
				System.out.println(messageFromServer);
				
				if(messageFromServer.startsWith(">>> Exit")) {
					break;
				}
			}
			
			socketForCommunication.close();
		} catch (UnknownHostException e) {
			 System.out.println("Unknown Host | Client Class.");

		} catch (IOException e) {
			System.out.println("Server Is Down | Client Class.");
		}
	}

	@Override
	public void run() {
		
		String messageForServer;
		
		try {
			while(true) {
				messageForServer = inputFromKeyboard.readLine();
				outputForServer.println(messageForServer);
				
				if(messageForServer.startsWith("--exit")) {
					break;
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

}