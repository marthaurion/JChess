import java.io.IOException;


public class Player {
	private PieceColor color;
	private PlayerProxy proxy;
	
	public Player(Model m) throws IOException {
		proxy = new PlayerProxy(this);
		PlayerBroker broker = new PlayerBroker();
		proxy.setBroker(broker);
		color = proxy.startGame();
	}
	
	public PieceColor getColor() {
		return color;
	}
	
	public void endGame(PieceColor c) {
		try {
			proxy.sendResign(color);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Move waitMove() throws IOException {
		return proxy.getMove();
	}
	
	public void sendMove(Move m) throws IOException {
		proxy.sendMove(m);
	}
}