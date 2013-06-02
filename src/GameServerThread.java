import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class GameServerThread extends Thread {
	private Socket socket;
	private GameServerGame game;
	private PieceColor color;
	private GameServerThread opp;
	private PrintWriter output;
	private BufferedReader input;
	
	public GameServerThread(Socket s, PieceColor c) {
		super("GameServerThread");
		socket = s;
		color = c;
		opp = null;
		game = null;
		try {
			output = new PrintWriter(socket.getOutputStream(), true);
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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
			String line = null;
			while(line == null) {
				output.write(color.toString());
				line = input.readLine();
			}
			
			while(true) {
				while(!game.canMove(line, this));
				System.out.println("Line Processed");
				line = input.readLine();
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


