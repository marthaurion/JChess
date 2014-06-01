package online;

/**
 * Stores the game on the server. Basically just a link between two players.
 * @author marthaurion
 *
 */
public class ServerGame {
	private ServerPlayer current;
	
	/**
	 * Used to initialize the current player. This should only be called at the start.
	 * @param p - player to set as current player
	 */
	public void setCurrent(ServerPlayer p) {
		this.current = p;
	}
	
	/**
	 * Send the move from one player to the other and switches the current player's turn
	 * @param s - message to send
	 * @param player - player that is sending the message
	 */
    public synchronized void sendMove(String s, ServerPlayer player) {
        if (player == current) {
        	current = current.getOpponent();
            System.out.println(current.getColor().toString());
            current.otherPlayerMoved(s);
        }
    }
}
