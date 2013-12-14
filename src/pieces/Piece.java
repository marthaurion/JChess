package pieces;

import java.util.ArrayList;

import board.Board;
import board.Move;

public abstract class Piece {
	protected Board board;
	
	public abstract Square getLocation();
	public abstract void setLocation(Square s);
	public abstract String getNotation();
    public abstract int getValue();
    public abstract String getName();
    public abstract char getID();
    public abstract PieceColor getColor();
    public abstract ArrayList<Square> getLegalMoves();
    
    
    //returns a list of threatened squares for each piece
    //this is only here so the pawn can override it
    public ArrayList<Square> getThreatList() {
    	return getLegalMoves();
    }
    
    //moved this function to piece because it has become generalized
    public boolean move(Move m) {
		//first do basic checks
		if(!checkPiece(m, this)) return false;
		
		ArrayList<Square> list = getLegalMoves();
		
		list = checkKing(list);
		
		return checkList(m, list);
    }
    
    //takes the input list of squares and test whether each one will put the king in check
    //if so, the move is removed from the list
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
	
	//checks a location and returns whether it is empty or not
	protected boolean isEmpty(int x, int y) {
		//should return true only if the no piece is there
		return board.getPiece(x, y).getName().equals("None");
	}
	
	//checks if a given x, y coordinate is on the board
	protected boolean onBoard(int x, int y) {
		return x < 8 && x >= 0 && y >= 0 && y < 8;
	}
	
	//returns true if colors are different
	protected boolean isEnemy(int x, int y, Piece p) {
		return p.getColor() != board.getPiece(x, y).getColor();
	}
	
	//checks to make sure the source piece is the same as the piece calling visit
	protected boolean checkPiece(Move m, Piece p) {
		Piece temp = m.getSource();
		//if the source is a null piece return false
		if(temp.getName().equals("None")) {
			return false;
		}
		//checks if pieces are the same type
		if(!temp.getName().equals(p.getName())) {
			return false;
		}
		//checks if the locations are the same
		if(!temp.getNotation().equals(p.getNotation())) {
			return false;
		}
		//checks if colors of the pieces are the same
		if(temp.getColor() != p.getColor()) {
			return false;
		}
		
		return true;
	}
    
	//made this so I don't have to deal with constructor inheritance
	public void setBoard(Board b) {
		board = b;
	}
}