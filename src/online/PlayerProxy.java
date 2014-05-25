package online;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import controller.Player;
import board.Move;
import pieces.PieceColor;

public class PlayerProxy {
	
	private Socket socket;
	private PrintWriter output;
	private BufferedReader input;
	private Player player;
	
	public PlayerProxy(Player p) {
		socket = null;
		output = null;
		input = null;
		player = p;
	}
	
	//start game with the server and return the piece color assigned
	public PieceColor startGame() {
		String color = null;
		
		try {
			socket = new Socket("localhost", 8484);
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new PrintWriter(socket.getOutputStream(), true);
			System.out.println("Reading Color");
			color = input.readLine();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return PieceColor.fromString(color);
	}
	
	public Move getMove() throws IOException {
		String fromServer = input.readLine();
		System.out.println("Move received.");
		System.out.println(fromServer);
		return Move.fromString(fromServer, player.getBoard());
	}
	
	public void sendMessage(Move p) throws IOException {
		System.out.println("Sent move: " + p.toString());
		output.println(p.toString());
	}
	
	public void endGame() throws IOException {
		socket.close();
		input.close();
		output.close();
	}
}
