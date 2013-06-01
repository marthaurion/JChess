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
	
	public void sendMove(Move m) throws IOException {
		PlayerAction action = new PlayerAction(m.getSource().getColor());
		action.setMove(m);
		PlayerAction receive = broker.sendMessage(action);
		if(receive.getAction() != null && receive.getAction().equals("Resign")) {
			play.endGame(play.getColor());
		}
		else {
			play.getMove(receive.getMove());
		}
	}
	
	public void sendResign(PieceColor c) throws IOException {
		PlayerAction action = new PlayerAction(c);
		action.setResign();
		broker.sendMessage(action);
	}
}
