import java.io.IOException;


public class Player implements Observer {
	private PieceColor color;
	private PlayerProxy proxy;
	private Model model;
	private boolean castle;
	
	public Player(Model m) throws IOException {
		proxy = new PlayerProxy(this);
		PlayerBroker broker = new PlayerBroker();
		proxy.setBroker(broker);
		color = proxy.startGame();
		castle = true;
		model = m;
	}
	
	public PieceColor getColor() {
		return color;
	}
	
	public boolean canCastle() {
		return castle;
	}
	
	public void endGame(PieceColor c) {
		if(c == color) {
			//end game locally
		}
		else {
			try {
				proxy.sendResign(color);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public Move waitMove() throws IOException {
		return proxy.getMove();
	}
	
	//theoretically the other player should have checked the move before sending it
	public void getMove(Move m) throws IOException {
		model.makeMove(m);
	}
	
	public void sendMove(Move m) throws IOException {
		proxy.sendMove(m);
	}

	@Override
	public void update(Model m) {
		model = m;
	}
}