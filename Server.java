import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 
 * @author Andrew Leppo and Rachel Pavlakovic
 *
 */
public class Server extends Thread{
	public static final int portNum = 9898;
	
	protected Socket socket;
	private String userName = null;
	
	private Server(Socket socket) {
		this.socket = socket;
		System.out.println("New client connected from " + socket.getInetAddress().getHostAddress());
		start();
	}
	
	public void run() {
		InputStream in = null;
		OutputStream out = null;
		try {
			in = socket.getInputStream();
			out = socket.getOutputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String request;
			while((request = reader.readLine()) != null) {
				System.out.println("Message received:" + request);
				if(userName == null)
					userName = request;
				request += '\n';
				out.write(request.getBytes());
			}
		}
		catch(IOException e) {
			System.out.println("Unable to get streams from client");
		}
		finally {
			try{
				in.close();
				out.close();
				socket.close();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		System.out.println("Server");
		ServerSocket server = null;
		try {
			server = new ServerSocket(portNum);
			while (true) {
				new Server(server.accept());
			}
		} catch (IOException ex) {
			System.out.println("Unable to start server.");
		} finally {
			try {
				if (server != null)
					server.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}
