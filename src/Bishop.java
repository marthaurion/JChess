import java.util.ArrayList;


public class Bishop extends StraightLine {
	private PieceColor color;
	private Square location;
	
	public Bishop(int x, int y, PieceColor c, Board b) {
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
    	return 3;
    }
    
    public String getName() {
    	return "Bishop";
    }
    
    public char getID() {
    	return 'B';
    }
    
    public PieceColor getColor() {
    	return color;
    }

	public ArrayList<Square> getLegalMoves() {
		ArrayList<Square> list = new ArrayList<Square>();
		int x = location.getX();
		int y = location.getY();
		
		list = addDiagonal(list, x, y, this);
		return list;
	}
    
}
