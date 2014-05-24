package online;

import java.io.IOException;
import java.net.ServerSocket;
import pieces.PieceColor;

public class ChessServer {
	private ServerPlayer currentPlayer;
	
    //Runs the application. Pairs up clients that connect.
    public ChessServer() throws IOException {
        ServerSocket listener = new ServerSocket(8484);
        System.out.println("Server is running");
        try {
            while (true) {
            	//the server constantly accepts connections and pairs up two connections into a game
                ServerPlayer playerX = new ServerPlayer(this, listener.accept(), PieceColor.White);
                ServerPlayer playerO = new ServerPlayer(this, listener.accept(), PieceColor.Black);
                playerX.setOpponent(playerO);
                playerO.setOpponent(playerX);
                //set first player to be white and start the game
                currentPlayer = playerX;
                playerX.start();
                playerO.start();
            }
        } finally {
            listener.close();
        }
    }
    
    public synchronized void sendMove(String s, ServerPlayer player) {
        if (player == currentPlayer) {
            currentPlayer = currentPlayer.getOpponent();
            System.out.println(currentPlayer.getColor().toString());
            currentPlayer.otherPlayerMoved(s);
        }
    }
}
