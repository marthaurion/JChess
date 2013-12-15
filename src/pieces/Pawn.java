package pieces;

import java.util.ArrayList;

import board.Board;


public class Pawn extends Piece {
	private int moved;
	
	public Pawn(int x, int y, PieceColor c, Board b) {
		super(x, y, c, b);
		moved = 0;
	}
	
	public Square getLocation() {
		return location;
	}
	
	public void setLocation(Square s) {
		location = s;
		moved++;
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
    /**
     * The pawn's threat squares aren't the same as legal moves.
     * The threat list needs to remove the forward move squares.
     */
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
		
		//en passant detection
		if(myY == getEnPassant()) {
			if(onBoard(myX+1, myY+a) && isEmpty(myX+1, myY+a) && enPassantTarget(myX+1, myY)) {
				list.add(new Square(myX+1, myY+a));
			}
			if(onBoard(myX-1, myY+a) && isEmpty(myX-1, myY+a) && enPassantTarget(myX-1, myY)) {
				list.add(new Square(myX-1, myY+a));
			}
		}
		
		return list;
    }
    
    
    //these methods are specifically for the pawn
    
    
    /**
     * Indicates which direction the pawn moves.
     * @return Value of movement.
     */
    public int advance() {
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
	
	/**
	 * Returns the en passant threat square for this pawn.
	 * @return The y-coordinate on the board which is the threat square for en passant.
	 */
	private int getEnPassant() {
		if(color == PieceColor.White) return 4;
		else if(color == PieceColor.Black) return 3;
		else return -1;
	}
	
	private boolean enPassantTarget(int x, int y) {
		boolean enemy = isEnemy(x, y);
		if(board.getPiece(x, y).getName().equals("Pawn") && enemy) {
			return getMoved() < ((Pawn)board.getPiece(x, y)).getMoved();
		}
		else return false;
	}
	
	
	
	public void setMoved(int x) {
		moved = x;
	}
	
	public int getMoved() {
		return moved;
	}
}
