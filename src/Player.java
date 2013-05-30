
public class Player {
	private PieceColor color;
	private boolean castle;
	
	public Player(PieceColor c) {
		color = c;
		castle = true;
	}
	
	public PieceColor getColor() {
		return color;
	}
	
	public boolean canCastle() {
		return castle;
	}
	
	
}