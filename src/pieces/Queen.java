package pieces;

import java.util.ArrayList;

import board.Board;


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
    
    public char getID() {
    	return 'Q';
    }
    
    public PieceColor getColor() {
    	return color;
    }
    
	public ArrayList<Square> getLegalMoves() {
		ArrayList<Square> list = new ArrayList<Square>();
		int x = location.getX();
		int y = location.getY();
		
		list = addStraight(list, x, y, this);
		list = addDiagonal(list, x, y, this);
		return list;
	}
    
}
