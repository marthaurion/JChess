import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServerBroker {
	private String ipAdd;

	public GameServerBroker(String ip) {
		ipAdd = ip;
	}
	
	//probably change this to call the server proxy
	public String getIP() {
		return ipAdd;
	}
	
	public void listen() throws IOException {
		@SuppressWarnings("resource")
		ServerSocket socket = new ServerSocket(8484);
		
		while(true) {
			
			Socket p1 = socket.accept();
			Socket p2 = socket.accept();
			GameServerThread g1 = new GameServerThread(p1, PieceColor.White);
			GameServerThread g2 = new GameServerThread(p2, PieceColor.Black);
			new GameServerGame(g1, g2);
		}
		
	}
}
