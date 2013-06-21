
public class NoPiece extends Piece {
	private PieceColor color;
	private Square location;
	
	public NoPiece(int x, int y) {
		color = PieceColor.None;
		location = new Square(x, y);
	}
	
	public Square getLocation() {
		return location;
	}
	
	public void setLocation(Square s) {
		location = s;
	}
	
	public String getNotation() {
		return location.getNotation();
	}
	
    public int getValue() {
    	return 0;
    }
    
    public String getName() {
    	return "None";
    }
    
    //pawns have no character code in algebraic notation
    public char getID() {
    	return ' ';
    }
    
    public PieceColor getColor() {
    	return color;
    }
    
    public boolean move(Move m, Board b) {
    	return false;
    }
}
