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
		
		String color = input.readLine();
		return PieceColor.fromString(color);
	}
	
	public PlayerAction sendMessage(PlayerAction p) throws IOException {
		output.write(p.toString());
		String fromServer = input.readLine();
		while(fromServer == null) {
			fromServer = input.readLine();
		}
		
		return PlayerAction.fromString(fromServer);
	}
	
	public void endGame(PieceColor c) throws IOException {
		socket.close();
		input.close();
		output.close();
	}
}
