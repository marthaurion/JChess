package online;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import pieces.PieceColor;

/**
 * Used by the server to identify each incoming connection.
 * @author marthaurion
 *
 */
public class ServerPlayer extends Thread {
	private ServerGame game;
	private ServerPlayer opponent;
	private ServerPlayer turnPlayer;
	private Socket socket;
	private BufferedReader input;
	private PieceColor color;
	private PrintWriter output;

	/**
	 * Constructor for the server player. Assigns all variables and sends a PieceColor to the player connection.
	 * @param game - Game object used to send messages to the other player.
	 * @param socket - Connection used for messages.
	 * @param color - PieceColor linked to this player.
	 */
	public ServerPlayer(ServerGame game, Socket socket, PieceColor color) {
		this.game = game;
		this.socket = socket;
		this.color = color;
		try {
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new PrintWriter(socket.getOutputStream(), true);
			System.out.println(color.toString());
			output.println(color.toString());
		} catch (IOException e) {
			System.out.println("Player died: " + e);
		}
		if(this.color == PieceColor.White) turnPlayer = this;
		else turnPlayer = null;
	}
	
	/**
	 * Accepts notification of who the opponent is.
	 * @param opponent - the other player
	 */
	public void setOpponent(ServerPlayer opponent) {
		this.opponent = opponent;
		if(turnPlayer == null) turnPlayer = opponent;
	}
	
	/**
	 * Get function to return the opponent for this player.
	 * @return - ServerPlayer that is the opponent for this player.
	 */
	public ServerPlayer getOpponent() {
		return opponent;
	}
	
	/**
	 * Get function to return the PieceColor of this player.
	 * @return - PieceColor that this player is playing.
	 */
	public PieceColor getColor() {
		return color;
	}
	
	/**
	 * Send the player's move to this player's connection.
	 * @param location - Message to be sent to the player.
	 */
	public void otherPlayerMoved(String location) {
		output.println(location);
		System.out.println("Move sent.");
	}
	
	/**
	 * Repeatedly get commands from the client and process them.
	 */
	public void run() {
		try {
			while (true) {
				String command = input.readLine();
				System.out.println(command);
				if (!command.contains("Resign") && command.contains(color.toString())) {
					game.sendMove(command, this);
				} else {
					game.sendMove(command, this);
					return;
				}
			}
		} catch (IOException e) {
			System.out.println("Player died: " + e);
		} finally {
			try {socket.close();} catch (IOException e) {}
		}
	}
}
