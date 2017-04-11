import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

import java.io.OutputStream;
import java.io.PrintStream;
public final class ChatClient{
	public static void main(String[] args)throws Exception{
		//Connect to server at "codebank.xyz using port 38001"
		try (Socket socket = new Socket("codebank.xyz", 38001)) {
			//Runnable object for getting message from server
			Runnable getMessage = () -> {
				//While its running
				while(true){
					try{
						//get message from server
						InputStreamReader fromServer = new InputStreamReader(socket.getInputStream(), "UTF-8");
						BufferedReader br = new BufferedReader(fromServer);
						String message = br.readLine();
						//if message is not empty, display the message
						if(message != null){
							System.out.println(message);
						}
						
					}
					catch(Exception e){

					}
				}	
			};
			//run get message thread
			Thread getMessageThread = new Thread(getMessage);
			getMessageThread.start();
			//while its running, read user input and send to server.
			while(true){
				try{
					BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
	                //set up output stream to send message to server
	            	PrintStream toServer = new PrintStream(socket.getOutputStream(), true, "UTF-8");
	            	String str = userInput.readLine();
	                //send the user message to server
	            	toServer.printf("%s%n",str);
				}
				catch(Exception e){

				}
			}
			
		}

	}
}
