package online;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import pieces.PieceColor;

//how the server identifies every connection
public class ServerPlayer extends Thread {
	private ChessServer server;
	private ServerPlayer opponent;
	private Socket socket;
	private BufferedReader input;
	private PieceColor color;
	private PrintWriter output;

	public ServerPlayer(ChessServer server, Socket socket, PieceColor color) {
		this.server = server;
		this.socket = socket;
		this.color = color;
		try {
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new PrintWriter(socket.getOutputStream(), true);
			output.println(color.toString());
		} catch (IOException e) {
			System.out.println("Player died: " + e);
		}
	}
	
	//Accepts notification of who the opponent is.
	public void setOpponent(ServerPlayer opponent) {
		this.opponent = opponent;
	}
	
	//get functions
	public ServerPlayer getOpponent() {
		return opponent;
	}
	
	public PieceColor getColor() {
		return color;
	}
	
	public void otherPlayerMoved(String location) {
		output.println(location);
		System.out.println("Move sent.");
	}
	
	public void run() {
		try {
			// Repeatedly get commands from the client and process them.
			while (true) {
				String command = input.readLine();
				System.out.println(command);
				if (!command.contains("Resign") && command.contains(color.toString())) {
					server.sendMove(command, this);
				} else {
					server.sendMove(command, this);
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
