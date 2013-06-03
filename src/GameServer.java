import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer {

    /**
     * Runs the application. Pairs up clients that connect.
     */
    public static void main(String[] args) throws Exception {
        ServerSocket listener = new ServerSocket(8484);
        System.out.println("Server is running");
        try {
            while (true) {
                Game game = new Game();
                Game.Player playerX = game.new Player(listener.accept(), PieceColor.White);
                Game.Player playerO = game.new Player(listener.accept(), PieceColor.Black);
                playerX.setOpponent(playerO);
                playerO.setOpponent(playerX);
                game.currentPlayer = playerX;
                playerX.start();
                playerO.start();
            }
        } finally {
            listener.close();
        }
    }
}

//A two-player game.
class Game {

    //The current player.
    Player currentPlayer;

    public synchronized void sendMove(String s, Player player) {
        if (player == currentPlayer) {
            currentPlayer = currentPlayer.opponent;
            System.out.println(currentPlayer.color.toString());
            currentPlayer.otherPlayerMoved(s);
        }
    }

    class Player extends Thread {
        Player opponent;
        Socket socket;
        BufferedReader input;
        PieceColor color;
        PrintWriter output;

        /**
         * Constructs a handler thread for a given socket and mark
         * initializes the stream fields, displays the first two
         * welcoming messages.
         */
        public Player(Socket socket, PieceColor color) {
            this.socket = socket;
            this.color = color;
            try {
                input = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
                output = new PrintWriter(socket.getOutputStream(), true);
                output.println(color.toString());
            } catch (IOException e) {
                System.out.println("Player died: " + e);
            }
        }

        //Accepts notification of who the opponent is.
        public void setOpponent(Player opponent) {
            this.opponent = opponent;
        }

        public void otherPlayerMoved(String location) {
            output.println(location);
        }

        /**
         * The run method of this thread.
         */
        public void run() {
            try {
                // Repeatedly get commands from the client and process them.
                while (true) {
                    String command = input.readLine();
                    System.out.println(command);
                    if (!command.contains("Resign")) {
                        sendMove(command, this);
                    } else {
                    	sendMove(command, this);
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
}