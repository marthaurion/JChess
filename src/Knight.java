
public class Knight implements Piece {
	private PieceColor color;
	private Square location;
	
	public Knight(int x, int y, PieceColor c) {
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
    
    public boolean acceptVisitor(PieceVisitor p, Move m) {
    	return p.visitKnight(m, this);
    }
}
