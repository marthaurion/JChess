
public class King extends Piece {
	private PieceColor color;
	private Square location;
	
	public King(int x, int y, PieceColor c) {
		color = c;
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
    	return "King";
    }
    
    //pawns have no character code in algebraic notation
    public char getID() {
    	return 'K';
    }
    
    public PieceColor getColor() {
    	return color;
    }
    
    public boolean move(Move m, Board b) {
    	return false;
    }
    
}
