import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class PlayerBroker {
	
	private GameServerBroker broker;
	private Socket socket;
	
	public PlayerBroker() {
		broker = null;
		socket = null;
	}
	
	public void attachServer(GameServerBroker b) {
		broker = b;
	}
	
	public void startGame(PieceColor c) throws IOException {
		if(broker == null) return;
		
		socket = new Socket(broker.getIP(), 8484);
	}
	
	public PlayerAction sendMessage(PlayerAction p) throws IOException {
		
		
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		out.write(p.toString());
		String fromServer = in.readLine();
		
		return PlayerAction.fromString(fromServer);
	}
	
	public void endGame(PieceColor c) throws IOException {
		socket.close();
	}
}
