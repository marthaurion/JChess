import java.util.ArrayList;


public class Pawn extends Piece {
	private PieceColor color;
	private Square location;
	
	public Pawn(int x, int y, PieceColor c) {
		color = c;
		location = new Square(x, y);
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
    
    public boolean move(Move m, Board b) {
		//first do basic checks
		if(!checkPiece(m, this)) return false;
		
		//generate legal moves
		//ignoring en passant for now
		ArrayList<Square> list = new ArrayList<Square>();
		int myX = location.getX();
		int myY = location.getY();
		
		int a = advance();
		//checks for the case where the pawn moves forward two
		if(myY == start() && isEmpty(myX, myY+a, b) && isEmpty(myX, myY+(2*a), b)) {
			list.add(new Square(myX, myY+(2*a)));
		}
		//checks the space in front of the pawn
		if(isEmpty(myX, myY+a, b)) {
			list.add(new Square(myX, myY+a));
		}
		//checks the spaces in the corners
		if(onBoard(myX+1, myY+a) && !isEmpty(myX+1, myY+a, b) && isEnemy(myX+1, myY+a, this, b)) {
			list.add(new Square(myX+1, myY+a));
		}
		if(onBoard(myX-1, myY+a) && !isEmpty(myX-1, myY+a, b) && isEnemy(myX-1, myY+a, this, b)) {
			list.add(new Square(myX-1, myY+a));
		}
		
		return checkList(m, list);
    }
    
    
    //these two methods are specific only to the pawn
    public int advance() {
    	if(color == PieceColor.White) {
    		return 1;
    	}
    	else return -1;
    }
    
    public int start() {
    	if(color == PieceColor.White){
    		return 1;
    	}
    	else return 6;
    }
}
