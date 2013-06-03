import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class PlayerBroker {
	
	private GameServerBroker broker;
	private Socket socket;
	private PrintWriter output;
	private BufferedReader input;
	
	public PlayerBroker() {
		broker = new GameServerBroker("68.51.78.38");
		socket = null;
		output = null;
		input = null;
	}
	
	public void attachServer(GameServerBroker b) {
		broker = b;
	}
	
	public PieceColor startGame() throws IOException {
		if(broker == null) return PieceColor.None;
		
		socket = new Socket(broker.getIP(), 8484);
		input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		output = new PrintWriter(socket.getOutputStream(), true);
		System.out.println("Reading Color");
		String color = input.readLine();
		System.out.println(color);
		return PieceColor.fromString(color);
	}
	
	public PlayerAction getMove() throws IOException {
		String fromServer = input.readLine();
		return PlayerAction.fromString(fromServer);
	}
	
	public void sendMessage(PlayerAction p) throws IOException {
		output.println(p.toString());
	}
	
	public void endGame(PieceColor c) throws IOException {
		socket.close();
		input.close();
		output.close();
	}
}
