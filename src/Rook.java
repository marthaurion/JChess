
public class Rook implements Piece {
	private PieceColor color;
	private Square location;
	
	public Rook(int x, int y, PieceColor c) {
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
    	return 5;
    }
    
    public String getName() {
    	return "Rook";
    }
    
    //pawns have no character code in algebraic notation
    public char getID() {
    	return 'R';
    }
    
    public PieceColor getColor() {
    	return color;
    }
    
    public boolean acceptVisitor(PieceVisitor p, Move m) {
    	return p.visitRook(m, this);
    }
}
