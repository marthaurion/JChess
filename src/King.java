import java.util.ArrayList;


public class King extends Piece {
	private PieceColor color;
	private Square location;
	
	public King(int x, int y, PieceColor c, Board b) {
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
    	return 0;
    }
    
    public String getName() {
    	return "King";
    }
    
    public char getID() {
    	return 'K';
    }
    
    public PieceColor getColor() {
    	return color;
    }

	public ArrayList<Square> getLegalMoves() {
		//similar to the knight because there are only 8 possible moves
		ArrayList<Square> list = new ArrayList<Square>();
		int x = location.getX();
		int y = location.getY();
		
		//add surrounding squares
		list.add(new Square(x+1, y+1));
		list.add(new Square(x+1, y));
		list.add(new Square(x+1, y-1));
		
		list.add(new Square(x, y+1));
		list.add(new Square(x, y-1));
		
		list.add(new Square(x-1, y+1));
		list.add(new Square(x-1, y));
		list.add(new Square(x-1, y-1));
		
		
		//remove the moves that aren't legal
		for(int i = 0; i < list.size(); i++) {
			x = list.get(i).getX();
			y = list.get(i).getY();
			
			if(!onBoard(x, y)) {
				list.remove(i);
				i--;
			}
			else if(!isEmpty(x, y) && !isEnemy(x, y, this)) {
				list.remove(i);
				i--;
			}
		}
		
		return list;
	}
    
}
