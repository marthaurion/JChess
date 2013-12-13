package pieces;

import java.util.ArrayList;

import board.Board;


public class Rook extends StraightLine {
	private PieceColor color;
	private Square location;
	
	public Rook(int x, int y, PieceColor c, Board b) {
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
		int x = location.getX();
		int y = location.getY();
		
		list = addStraight(list, x, y, this);
		return list;
	}
	
}
