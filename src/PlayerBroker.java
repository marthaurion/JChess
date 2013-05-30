import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class PlayerBroker {
	
	private GameServerBroker broker;
	
	public PlayerBroker() {
		broker = null;
	}
	
	public void attachServer(GameServerBroker b) {
		broker = b;
	}
	
	public void sendMessage(String st) throws IOException {
		Socket sock = new Socket("localhost", 8484);
		PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		
		String fromServer = "";
		while(!fromServer.equals("Done")) {
			out.write(st);
			fromServer = in.readLine();
		}
		
		sock.close();
	}
}
