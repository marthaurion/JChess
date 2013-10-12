
public class Knight extends Piece {
	private PieceColor color;
	private Square location;
	
	public Knight(int x, int y, PieceColor c, Board b) {
		color = c;
		location = new Square(x, y);
		board = b;
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
    	return 1;
    }
    
    public String getName() {
    	return "Knight";
    }
    
    //pawns have no character code in algebraic notation
    public char getID() {
    	return 'N';
    }
    
    public PieceColor getColor() {
    	return color;
    }
    
    public boolean move(Move m) {
    	return false;
    }
    
}
