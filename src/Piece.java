import java.util.ArrayList;

public abstract class Piece {
	public abstract boolean move(Move m, Board b);
	public abstract Square getLocation();
	public abstract void setLocation(Square s);
	public abstract String getNotation();
    public abstract int getValue();
    public abstract String getName();
    public abstract char getID();
    public abstract PieceColor getColor();
    
    
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
	protected boolean isEmpty(int x, int y, Board b) {
		//should return true only if the no piece is there
		return b.getPiece(x, y).getName().equals("None");
	}
	
	//checks if a given x, y coordinate is on the board
	protected boolean onBoard(int x, int y) {
		return x < 8 && x >= 0 && y >= 0 && y < 8;
	}
	
	//returns true if colors are different
	protected boolean isEnemy(int x, int y, Piece p, Board b) {
		return p.getColor() != b.getPiece(x, y).getColor();
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
    
}