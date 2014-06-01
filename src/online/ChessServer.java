package online;

import java.io.IOException;
import java.net.ServerSocket;
import pieces.PieceColor;

/**
 * Server class to handle creation of online games.
 * @author marthaurion
 *
 */
public class ChessServer {
	
   /**
    * Constructor for chess server. Holds an infinite loop that just pairs connections in the input socket.
    * @throws IOException
    */
    public ChessServer() throws IOException {
        ServerSocket listener = new ServerSocket(8484);
        System.out.println("Server is running");
        try {
            while (true) {
            	//the server constantly accepts connections and pairs up two connections into a game
            	ServerGame game = new ServerGame();
                ServerPlayer playerX = new ServerPlayer(game, listener.accept(), PieceColor.White);
                ServerPlayer playerO = new ServerPlayer(game, listener.accept(), PieceColor.Black);
                playerX.setOpponent(playerO);
                playerO.setOpponent(playerX);
                //set first player to be white and start the game
                game.setCurrent(playerX);
                playerX.start();
                playerO.start();
            }
        } finally {
            listener.close();
        }
    }
}
