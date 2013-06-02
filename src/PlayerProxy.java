import java.io.IOException;


public class PlayerProxy {
	
	private PlayerBroker broker;
	private Player play;
	
	public PlayerProxy(Player p) {
		broker = null;
		play = p;
	}
	
	public void setBroker(PlayerBroker b) {
		broker = b;
	}
	
	public Move getMove() throws IOException {
		PlayerAction action = broker.getMove();
		return action.getMove();
	}
	
	public PieceColor startGame() throws IOException {
		return broker.startGame();
	}
	
	public void sendMove(Move m) throws IOException {
		PlayerAction action = new PlayerAction(m.getSource().getColor());
		action.setMove(m);
		broker.sendMessage(action);
	}
	
	public void sendResign(PieceColor c) throws IOException {
		PlayerAction action = new PlayerAction(c);
		action.setResign();
		broker.sendMessage(action);
	}
}
