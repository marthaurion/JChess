package pieces;

import java.util.ArrayList;

import board.Board;


public class Pawn extends Piece {
	
	public Pawn(int x, int y, PieceColor c, Board b) {
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
    	return 1;
    }
    
    public String getName() {
    	return "Pawn";
    }
    
    //pawns have no character code in algebraic notation
    public char getID() {
    	return ' ';
    }
    
    public PieceColor getColor() {
    	return color;
    }
    
    @Override
    //the pawns threat squares aren't actually legal moves
    public ArrayList<Square> getThreatList() {
    	ArrayList<Square> list = new ArrayList<Square>();
    	
		int myX = location.getX();
		int myY = location.getY();
		
    	int a = advance();
    	
    	if(onBoard(myX+1, myY+a)) list.add(new Square(myX+1, myY+a));
    	if(onBoard(myX-1, myY+a)) list.add(new Square(myX-1, myY+a));
    	return list;
    }
    
    public ArrayList<Square> getLegalMoves() {
		//generate legal moves
		//ignoring en passant for now
		ArrayList<Square> list = new ArrayList<Square>();
		int myX = location.getX();
		int myY = location.getY();
		
		int a = advance();
		//checks for the case where the pawn moves forward two
		if(myY == start() && isEmpty(myX, myY+a) && isEmpty(myX, myY+(2*a))) {
			list.add(new Square(myX, myY+(2*a)));
		}
		//checks the space in front of the pawn
		if(isEmpty(myX, myY+a)) {
			list.add(new Square(myX, myY+a));
		}
		//checks the spaces in the corners
		if(onBoard(myX+1, myY+a) && !isEmpty(myX+1, myY+a) && isEnemy(myX+1, myY+a)) {
			list.add(new Square(myX+1, myY+a));
		}
		if(onBoard(myX-1, myY+a) && !isEmpty(myX-1, myY+a) && isEnemy(myX-1, myY+a)) {
			list.add(new Square(myX-1, myY+a));
		}
		return list;
    }
    
    
    //these methods are specifically for the pawn
    
    
    /**
     * Indicates which direction the pawn moves.
     * @return Value of movement.
     */
    private int advance() {
    	if(color == PieceColor.White) {
    		return 1;
    	}
    	else return -1;
    }
    
    /**
     * The Pawn's starting square.
     * @return The y-coordinate on the board which is the starting square for this Piece. 
     */
    private int start() {
    	if(color == PieceColor.White){
    		return 1;
    	}
    	else return 6;
    }
    
    /**
     * Finds the promotion square of a pawn.
     * @return The y-coordinate on the board which is the promotion square for this Piece.
     */
	public int getPromo() {
		if(color == PieceColor.White) return 7;
		else if(color == PieceColor.Black) return 0;
		else return -1;
	}
}
