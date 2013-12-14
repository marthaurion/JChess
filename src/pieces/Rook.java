package pieces;

import java.util.ArrayList;

import board.Board;


public class Rook extends StraightLine {
	
	public Rook(int x, int y, PieceColor c, Board b) {
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
    	return 5;
    }
    
    public String getName() {
    	return "Rook";
    }
    
    public char getID() {
    	return 'R';
    }
    
    public PieceColor getColor() {
    	return color;
    }

	public ArrayList<Square> getLegalMoves() {
		ArrayList<Square> list = new ArrayList<Square>();
		
		list = addStraight(list);
		return list;
	}
	
}
