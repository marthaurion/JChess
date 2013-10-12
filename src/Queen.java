
public class Queen extends StraightLine {
	private PieceColor color;
	private Square location;
	
	public Queen(int x, int y, PieceColor c, Board b) {
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
    	return 9;
    }
    
    public String getName() {
    	return "Queen";
    }
    
    //pawns have no character code in algebraic notation
    public char getID() {
    	return 'Q';
    }
    
    public PieceColor getColor() {
    	return color;
    }
    
    public boolean move(Move m) {
    	return false;
    }
    
}
