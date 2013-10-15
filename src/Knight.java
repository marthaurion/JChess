import java.util.ArrayList;


public class Knight extends Piece {
	private PieceColor color;
	private Square location;
	
	public Knight(int x, int y, PieceColor c, Board b) {
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
    	return 1;
    }
    
    public String getName() {
    	return "Knight";
    }
    
    public char getID() {
    	return 'N';
    }
    
    public PieceColor getColor() {
    	return color;
    }

	public ArrayList<Square> getLegalMoves() {
		//get the list of legal moves
		ArrayList<Square> list = new ArrayList<Square>();
		int x = location.getX();
		int y = location.getY();
		
		//first add all possible moves
		//there should only be 8 for a knight
		list.add(new Square(x+1, y+2));
		list.add(new Square(x+1, y-2));
		list.add(new Square(x-1, y+2));
		list.add(new Square(x-1, y-2));
		
		list.add(new Square(x+2, y+1));
		list.add(new Square(x+2, y-1));
		list.add(new Square(x-2, y+1));
		list.add(new Square(x-2, y-1));
		
		//remove the moves that aren't legal
		for(int i = 0; i < list.size(); i++) {
			x = list.get(i).getX();
			y = list.get(i).getY();
			
			//covers the case where the move is off the board
			if(!onBoard(x, y)) {
				list.remove(i);
				i--;
			}
			//covers the case where the target is occupied by an ally
			else if(!isEmpty(x, y) && !isEnemy(x, y, this)) {
				list.remove(i);
				i--;
			}
		}
		
		return list;
	}
    
    
}
