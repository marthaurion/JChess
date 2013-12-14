package pieces;

import java.util.ArrayList;

import board.Board;


public class Bishop extends StraightLine {
	public Bishop(int x, int y, PieceColor c, Board b) {
		super(x, y, c, b);
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
		
		list = addDiagonal(list);
		return list;
	}
    
}
