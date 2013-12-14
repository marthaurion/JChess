package pieces;

import java.util.ArrayList;

import board.Board;
import board.Move;

/**
 * Abstraction for the chess piece.
 * @author marthaurion
 *
 */
public abstract class Piece {
	protected Board board;
	protected PieceColor color;
	protected Square location;
	
	/**
	 * Initialize a new piece.
	 * @param x The x-coordinate of the new piece.
	 * @param y The y-coordinate of the new piece.
	 * @param c PieceColor of the new piece.
	 * @param b The Board that will hold the new piece.
	 */
	public Piece(int x, int y, PieceColor c, Board b) {
		color = c;
		location = new Square(x, y);
		board = b;
	}
	
	/**
	 * Get the location of this Piece.
	 * @return Square indicating location of this Piece.
	 */
	public abstract Square getLocation();
	
	/**
	 * Set the location of this Piece.
	 * @param s New Square indicating new location.
	 */
	public abstract void setLocation(Square s);
	
	/**
	 * Algebraic notation for this Piece.
	 * @return String with algebraic notation.
	 */
	public abstract String getNotation();
	
	/**
	 * Get the Piece's value.
	 * @return int value of the Piece.
	 */
    public abstract int getValue();
    
    /**
     * English name for this Piece.
     * @return String with the name of the Piece type.
     */
    public abstract String getName();
    
    /**
     * Identifying character for this Piece's type.
     * @return Character ID for this Piece type.
     */
    public abstract char getID();
    
    /**
     * Get the PieceColor for this Piece.
     * @return PieceColor of this Piece.
     */
    public abstract PieceColor getColor();
    
    /**
     * Get a list of legal moves. This may be an incomplete list.
     * @return List containing legal moves.
     */
    public abstract ArrayList<Square> getLegalMoves();
    
    
    /**
     * Get a list of threatened squares for this Piece.
     * @return List containing every square this Piece attacks.
     */
    public ArrayList<Square> getThreatList() {
    	return getLegalMoves();
    }
    
    /**
     * Checks whether the input move is valid for this piece.
     * @param m Input Move object to be checked.
     * @return True if the move is valid and false otherwise.
     */
    public boolean move(Move m) {
		//first do basic checks
		if(!checkPiece(m)) return false;
		
		ArrayList<Square> list = getLegalMoves();
		
		list = checkKing(list);
		
		return checkList(m, list);
    }
    
    /**
     * Takes the input list of squares and test whether each one will put the king in check.
     * If so, the move is removed from the list.
     * @param input Input list of squares indicating possible moves.
     * @return Updated list of squares with illegal moves removed.
     */
    public ArrayList<Square> checkKing(ArrayList<Square> input) {
    	Board copy = null;
    	Square temp;
    	Move m;
    	Piece src, dest;
    	
    	for(int i = 0; i < input.size(); i++) {
    		temp = input.get(i);
    		
    		//copy the board
    		copy = new Board();
    		board.copyBoard(copy);
    		
    		//make the candidate move on the copy board
    		src = copy.getPiece(getLocation().getX(), getLocation().getY());
    		dest = copy.getPiece(temp.getX(), temp.getY());
    		m = new Move(src, dest);
    		copy.makeMove(m);
    		
    		//if the move puts the king in check, remove it from legal moves
    		if(copy.isCheck(getColor())) {
    			input.remove(i);
    			i--;
    		}
    		
    	}
    	
    	return input;
    }
    
    /**
     * Try to find the input move in the input list.
     * @param m Move object to find in the list.
     * @param l List of legal moves.
     * @return Boolean indicating whether the input Move is valid.
     */
	protected boolean checkList(Move m, ArrayList<Square> l) {
		//false if there are no legal moves
		if(l.size() < 1) return false;
		
		//if the current move is in the legal move set, then the move is legal
		for(int i = 0; i < l.size(); i++) {
			int x = l.get(i).getX();
			int y = l.get(i).getY();
			Square temp = m.getDest().getLocation();
			if(x == temp.getX() && y == temp.getY()) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Checks whether a location on the board is empty.
	 * @param x The x-coordinate of the location being checked.
	 * @param y The y-coordinate of the location being checked.
	 * @return True if (x, y) is empty and false otherwise.
	 */
	protected boolean isEmpty(int x, int y) {
		//should return true only if the no piece is there
		return board.getPiece(x, y).getName().equals("None");
	}
	
	/**
	 * Checks if a location is on the board.
	 * @param x The x-coordinate of the location being checked.
	 * @param y The y-coordinate of the location being checked.
	 * @return True if (x, y) is on the board and false otherwise. 
	 */
	protected boolean onBoard(int x, int y) {
		return x < 8 && x >= 0 && y >= 0 && y < 8;
	}
	
	/**
	 * Checks whether this Piece is different color than the Piece at the input location.
	 * @param x The x-coordinate for the Piece being compared to this Piece.
	 * @param y The y-coordinate for the Piece being compared to this Piece.
	 * @return Boolean indicating whether the PieceColor is different.
	 */
	protected boolean isEnemy(int x, int y) {
		return getColor() != board.getPiece(x, y).getColor();
	}
	
	/**
	 * Checks to make sure the source piece is the same as the piece calling visit.
	 * @param m The Move to check for validity.
	 * @return Boolean indicating whether this Piece is the same as the source piece in the Move object.
	 */
	protected boolean checkPiece(Move m) {
		Piece temp = m.getSource();
		//if the source is a null piece return false
		if(temp.getName().equals("None")) {
			return false;
		}
		//checks if pieces are the same type
		if(!temp.getName().equals(getName())) {
			return false;
		}
		//checks if the locations are the same
		if(!temp.getNotation().equals(getNotation())) {
			return false;
		}
		//checks if colors of the pieces are the same
		if(temp.getColor() != getColor()) {
			return false;
		}
		
		return true;
	}
}