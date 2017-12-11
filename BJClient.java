import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.net.InetAddress;

/**
 * 
 * @author Andrew Leppo and Rachel Pavlakovic
 *
 */
public class BJClient{
  
	public static void main(String args[]) {
        String host = "127.0.0.1";
        int port = 9898;
        new BJClient(host, port);
    }
 
	public BJClient(String host, int port) {
		try {
			String serverHost = new String("127.0.0.1");
			System.out.println("Connecting to host " + serverHost + " on port " + port + ".");
			 
            Socket socket = null;
            PrintWriter out = null;
            BufferedReader in = null;
 
            try {
                socket = new Socket(serverHost, 8081);
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (UnknownHostException e) {
                System.err.println("Unknown host: " + serverHost);
                System.exit(1);
            } catch (IOException e) {
                System.err.println("Unable to get streams from server");
                System.exit(1);
            }
 
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            
            while(true){
            	String message = null;
            	
            	while(message == null) {
            		// get server message
            		message = in.readLine();
            	}
                
                // print server message
              	System.out.println(message);
              	message = null;
                
                // get response from user
              	String userInput = stdIn.readLine();
                
              	if(userInput.equals("stop")) {
              		break;
              	}
                // else send response to server
              	else {
              		out.println(userInput);
              	}
            }
            out.close();
            in.close();
            stdIn.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}