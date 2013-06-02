import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class GameServerThread extends Thread {
	private Socket socket;
	private GameServerGame game;
	private GameServerThread opp;
	private PrintWriter output;
	private BufferedReader input;
	
	public GameServerThread(Socket s, PieceColor c) {
		super("GameServerThread");
		socket = s;
		opp = null;
		game = null;
		try {
			output = new PrintWriter(socket.getOutputStream(), true);
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output.write(c.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addGame(GameServerGame g) {
		game = g;
	}
	
	public void setOpponent(GameServerThread t) {
		opp = t;
	}
	
	public void oppMove(String s) {
		output.write(s);
	}
	
	public GameServerThread getOpponent() {
		return opp;
	}
	
	public void run() {
		try {
			while(true) {
				String line = input.readLine();
				while(!game.canMove(line, this));
				if(line.contains("Resign")) {
					break;
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
