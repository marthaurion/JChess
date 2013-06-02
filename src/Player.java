import java.io.IOException;


public class Player {
	private PieceColor color;
	private PlayerProxy proxy;
	private Board board;
	private boolean castle;
	
	public Player(Board b) throws IOException {
		proxy = new PlayerProxy(this);
		PlayerBroker broker = new PlayerBroker();
		proxy.setBroker(broker);
		color = proxy.startGame();
		castle = true;
		board = b;
	}
	
	public PieceColor getColor() {
		return color;
	}
	
	public boolean canCastle() {
		return castle;
	}
	
	public void endGame(PieceColor c) throws IOException {
		if(c == color) {
			//end game locally
		}
		else {
			proxy.sendResign(color);
		}
	}
	
	//theoretically the other player should have checked the move before sending it
	public void getMove(Move m) throws IOException {
		board.makeMove(m);
	}
	
	public void sendMove(Move m) throws IOException {
		proxy.sendMove(m);
	}
}