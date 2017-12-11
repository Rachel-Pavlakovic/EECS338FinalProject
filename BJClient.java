import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.net.InetAddress;

/**
 * 
 * @author Andrew Leppo and Rachel Pavlakovic
 *
 */
public class BJClient{
  
  private Socket socket;
  private BufferedReader in;
  private PrintWriter out;
  private String userName;
  private Scanner userInput;
  private boolean playing;
  private String message;
  
  /**
   * Constructor for the BJClient
   * @param userName
   * @throws IOException
   */
  public BJClient(String userName) throws IOException{
    this.playing = true;
    this.userName = userName;
    this.userInput = new Scanner(System.in);
    this.socket = new Socket(InetAddress.getByName("127.0.0.1" ), 9898);
    
    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    out = new PrintWriter(socket.getOutputStream(), true);
	BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
	
    while(playing){
      
      // get server message
    	message = in.readLine();
      
      // print server message
    	System.out.println(message);
      
      // get response from user
    	String userInput = stdIn.readLine();
      
      // if "stop" set playing to "false"
    	if(userInput.equals("stop")) {
    		playing = false;
    	}
      // else send response to server
    	else {
    		out.println(userInput);
    	}
      
    }
    
    out.close();
    in.close();
    stdIn.close();
    this.socket.close();
    
  }
  
}