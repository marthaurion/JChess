package pieces;

import java.util.ArrayList;

import board.Board;


public class Queen extends StraightLine {
	public Queen(int x, int y, PieceColor c, Board b) {
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
    	return 9;
    }
    
    public String getName() {
    	return "Queen";
    }
    
    public char getID() {
    	return 'Q';
    }
    
    public PieceColor getColor() {
    	return color;
    }
    
	public ArrayList<Square> getLegalMoves() {
		ArrayList<Square> list = new ArrayList<Square>();
		
		list = addStraight(list);
		list = addDiagonal(list);
		return list;
	}
    
}
