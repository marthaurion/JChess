import java.io.IOException;


public class Player implements Observer {
	private PieceColor color;
	private PlayerProxy proxy;
	private Model model;
	
	public Player(Model m) throws IOException {
		proxy = new PlayerProxy(this);
		PlayerBroker broker = new PlayerBroker();
		proxy.setBroker(broker);
		color = proxy.startGame();
		model = m;
	}
	
	public PieceColor getColor() {
		return color;
	}
	
	public void endGame(PieceColor c) {
		if(c == color) {
			try {
				proxy.sendResign(color);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else model.endGame(c);
	}
	
	public Move waitMove() throws IOException {
		return proxy.getMove();
	}
	
	public void sendMove(Move m) throws IOException {
		proxy.sendMove(m);
	}

	@Override
	public void update(Model m) {
		model = m;
	}
}