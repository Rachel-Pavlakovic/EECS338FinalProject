import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
	
	private static Deck deck;
	private static Hand hand;
	private static AutoDealer dealer;
	
	public static void main(String[] args) throws IOException {
		ConcurrentHashMap<String, Integer> chips = new ConcurrentHashMap<String, Integer>();
		ServerSocket listener = new ServerSocket(9898);
		 
		try{
			 while (true) {
				 new Seat(listener.accept(), chips);
			 }
		 }
		 finally {
			 listener.close();
		 }
	}
	
	private static class Seat extends Thread {
		private Socket socket;
		private ConcurrentHashMap<String, Integer> chipCount;
		private ObjectOutputStream output; 
		private ObjectInputStream input; 
		
		public Seat(Socket socket, ConcurrentHashMap<String, Integer> chipCount) {
			this.socket = socket;
			this.chipCount = chipCount;
		}
		
		public void run() {
			try {
				try {
					getStreams();
					processConnection(); 
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
			finally {
				try {
					socket.close();
				}
				catch (IOException e) {
					log("Couldn't close socket");
				}
				log("Connect with client closed");
			}
		}
		
		private void getStreams() throws IOException {
			
			output = new ObjectOutputStream( socket.getOutputStream() );
			output.flush(); 

			input = new ObjectInputStream( socket.getInputStream() );

		} 
		
		private void processConnection() throws IOException {
			String message = "Connection successful";
			sendData(message); 
			
			dealer = new AutoDealer(deck);
			
			do {
			 
				try {
					if(message.contains("hit")) {				
						cardhit();
					}

					if(message.contains("stay")) {
						dealer.clearHand();
						dealer.startRound();
						dealer.play();
						
						boolean playerWins = dealer.didPlayerWin(hand.getScore());
						boolean playerTies = dealer.didPlayerTie(hand.getScore());
					}

					message = ( String ) input.readObject(); 

				} 
				catch ( ClassNotFoundException classNotFoundException ) {
				} 

			} 
			
			while ( !message.equals( "CLIENT>>> TERMINATE" ) );
		} 
		
		private void cardhit() {
			hand.draw(deck.draw());
			
			sendData("Your Total: " +  hand.getScore());
		}
		
		private void sendData( String message ) {
			try {
				output.writeObject( message );
				output.flush(); 
			} 
			catch ( IOException ioException ) {
			} 
		} 
	
		private void log(String message) {
			System.out.println(message);
		}
	}
	
}
