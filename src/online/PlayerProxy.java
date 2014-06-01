package online;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import controller.Player;
import board.Move;
import pieces.PieceColor;

/**
 * Arbitrates the communication between the controller and the server.
 * @author marthaurion
 *
 */
public class PlayerProxy {
	
	private Socket socket;
	private PrintWriter output;
	private BufferedReader input;
	private Player player;
	
	/**
	 * Constructor to initialize variables.
	 * @param p - Player (controller) linked to the proxy
	 */
	public PlayerProxy(Player p) {
		socket = null;
		output = null;
		input = null;
		player = p;
	}
	
	/**
	 * Creates the link with the server and starts the game.
	 * @return PieceColor assigned to the player making the request
	 */
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
	
	/**
	 * Reads a move from the server and translates into a move.
	 * @return Move object corresponding to the move from the server.
	 * @throws IOException
	 */
	public Move getMove() throws IOException {
		String fromServer = input.readLine();
		System.out.println("Move received.");
		System.out.println(fromServer);
		return Move.fromString(fromServer, player.getBoard());
	}
	
	/**
	 * Send a move to the server from the player
	 * @param move - String indicating the move the player just made.
	 * @throws IOException
	 */
	public void sendMessage(String move) throws IOException {
		System.out.println("Sent move: " + move);
		output.println(move);
	}
	
	/**
	 * Close all connections when the game is done.
	 * @throws IOException
	 */
	public void endGame() throws IOException {
		socket.close();
		input.close();
		output.close();
	}
}
