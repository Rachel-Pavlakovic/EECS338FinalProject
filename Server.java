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
	private boolean gameInSession;
	private ConcurrentHashMap<String, Integer> chipCount;
	
	private Server(Socket socket, ConcurrentHashMap<String, Integer> chips) {
		this.socket = socket;
		chipCount = chips;
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
			String message = null;
			int bet = 0;
			Hand hand = new Hand();
			Deck deck = new Deck(1);
			AutoDealer dealer = new AutoDealer(deck);
			
			while((request = reader.readLine()) != null) {
				System.out.println("Message received:" + request);
				if(userName == null) {
					userName = request;
					message = "Your username is " + userName + " Press enter to continue \n";
					if(!chipCount.containsKey(userName)) {
						chipCount.put(userName, 500);
					}
				}
				else if(!gameInSession) {
					message = "Do you want to start a new game? Enter yes or no \n";
					gameInSession = true;
				}
				else if(request.equals("no")) {
					message = "Okay. Press enter to continue \n";
					gameInSession = false;
				}
				else if(request.equals("yes")){
					message = "What is your bet? Your current chip count is " + chipCount.get(userName) +"\n";
				}
				else if(request.equals("hit")){
					hand.addCard(deck.draw());
					message = "Your hand: " + hand.toString() + "Do you want to hit or stay? \n";
				}
				else if(request.equals("stay")){
					dealer.play();
					boolean tie = dealer.didPlayerTie(hand.getScore());
					boolean win = dealer.didPlayerWin(hand.getScore());
					int originalCount;
					
					if(tie) {
						originalCount = chipCount.get(userName);
						chipCount.replace(userName, originalCount + bet);
						message = "You tied! Current chip count: " + chipCount.get(userName) + " Press enter to continue \n";
					}
					else if(win) {
						originalCount = chipCount.get(userName);
						chipCount.replace(userName, originalCount + bet + bet);
						message = "You win! Current chip count: " + chipCount.get(userName) + " Press enter to continue \n";
					}
					else {
						message = "You loose. Current chip count: " + chipCount.get(userName) + " Press enter to continue \n";
					}
					hand.clear();
					dealer.clearHand();
					gameInSession = false;
				}
				else if((bet = Integer.parseInt(request)) <= chipCount.get(userName)){
					int originalCount = chipCount.get(userName);
					chipCount.replace(userName, originalCount - bet);
					hand.addCard(deck.draw());
					hand.addCard(deck.draw());
					dealer.startRound();
					message = "Dealer's card: " + dealer.getHand().toString() + " Your hand: " + hand.toString() + "Do you want to hit or stay? \n";
				}
				
				out.write(message.getBytes());
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
		ConcurrentHashMap<String, Integer> chipCount = new ConcurrentHashMap<String, Integer>();
		System.out.println("Server");
		ServerSocket server = null;
		try {
			server = new ServerSocket(portNum);
			while (true) {
				new Server(server.accept(), chipCount);
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
