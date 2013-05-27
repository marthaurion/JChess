import java.util.ArrayList;


public class PieceVisitor implements Observer {
	private Model model;
	
	public PieceVisitor(Model m) {
		model = m;
	}
	
	public void update(Model m) {
		model = m;
	}
	
	public void endGame(PieceColor c) {
		
	}
	
	/*
	********
	* Pawn *
	********
	*/
	
	//returns whether a move is legal
	public boolean visitPawn(Move m, Pawn p) {
		//first do basic checks
		if(!checkPiece(m, p)) return false;
		
		//generate legal moves
		//ignoring en passant for now
		ArrayList<Square> list = new ArrayList<Square>();
		int myX = p.getLocation().getX();
		int myY = p.getLocation().getY();
		
		int a = p.advance();
		//checks for the case where the pawn moves forward two
		if(myY == p.start() && isEmpty(myX, myY+a) && isEmpty(myX, myY+(2*a))) {
			list.add(new Square(myX, myY+(2*a)));
		}
		//checks the space in front of the pawn
		if(isEmpty(myX, myY+a)) {
			list.add(new Square(myX, myY+a));
		}
		//checks the spaces in the corners
		if(onBoard(myX+1, myY+a) && !isEmpty(myX+1, myY+a) && isEnemy(myX+1, myY+a, p)) {
			list.add(new Square(myX+1, myY+a));
		}
		if(onBoard(myX-1, myY+a) && !isEmpty(myX-1, myY+a) && isEnemy(myX-1, myY+a, p)) {
			list.add(new Square(myX-1, myY+a));
		}
		
		return checkList(m, list);
	}
	
	/*
	**********
	* Knight *
	**********
	*/
	
	
	//this is easy because the knight can jump pieces
	public boolean visitKnight(Move m, Knight k) {
		if(!checkPiece(m, k)) return false;
		
		//get the list of legal moves
		ArrayList<Square> list = new ArrayList<Square>();
		int x = k.getLocation().getX();
		int y = k.getLocation().getY();
		
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
			else if(!isEmpty(x, y) && !isEnemy(x, y, k)) {
				list.remove(i);
				i--;
			}
		}
		
		return checkList(m, list);
	}
	
	
	/*
	**********
	* Bishop *
	**********
	*/
	
	public boolean visitBishop(Move m, Bishop b) {
		ArrayList<Square> list = new ArrayList<Square>();
		int x = b.getLocation().getX();
		int y = b.getLocation().getY();
		
		list = addDiagonal(list, x, y, b);
		
		return checkList(m, list);
	}
	
	
	/*
	********
	* Rook *
	********
	*/
	
	public boolean visitRook(Move m, Rook r) {
		ArrayList<Square> list = new ArrayList<Square>();
		int x = r.getLocation().getX();
		int y = r.getLocation().getY();
		
		list = addStraight(list, x, y, r);
		
		return checkList(m, list);
	}
	
	/*
	*********
	* Queen *
	*********
	*/
	
	public boolean visitQueen(Move m, Queen q) {
		ArrayList<Square> list = new ArrayList<Square>();
		int x = q.getLocation().getX();
		int y = q.getLocation().getY();
		
		list = addStraight(list, x, y, q);
		list = addDiagonal(list, x, y, q);
		
		return checkList(m, list);
	}
	
	/*
	********
	* King *
	********
	*/
	
	public boolean visitKing(Move m, King k) {
		return false;
	}
	
	
	
	//helper functions
	
	//use these so that the queen is easier
	
	//adds all diagonals on the board
	private ArrayList<Square> addDiagonal(ArrayList<Square> list, int x, int y, Piece p) {
		boolean topright = true;
		boolean botright = true;
		boolean topleft = true;
		boolean botleft = true;
		
		
		for(int i = 1; i < 8; i++) {
			//check each corner
			if(topright) {
				//first check if gone off board
				if(!onBoard(x+i, y+i)) {
					topright = false;
				}
				//can add if square is empty
				else if(isEmpty(x+i, y+i)) {
					list.add(new Square(x+i, y+i));
				}
				//if the space is occupied, you can't advance any further no matter what
				else {	
					topright = false;
					//capture case 
					if(isEnemy(x+i, y+i, p)) {
						list.add(new Square(x+i, y+i));
					}
				}
			}
			//check bottom right corner
			if(botright) {
				if(!onBoard(x+i, y-i)) {
					botright = false;
				}
				else if(isEmpty(x+i, y-i)) {
					list.add(new Square(x+i, y-i));
				}
				else {
					botright = false;
					if(isEnemy(x+i, y-i, p)) {
						list.add(new Square(x+i, y-i));
					}
				}
			}
			//check top left
			if(topleft) {
				if(!onBoard(x-i, y+i)) {
					topleft = false;
				}
				else if(isEmpty(x-i, y+i)) {
					list.add(new Square(x-i, y+i));
				}
				else {
					topleft = false;
					if(isEnemy(x-i, y+i, p)) {
						list.add(new Square(x-i, y+i));
					}
				}
			}
			//check bottom left
			if(botleft) {
				if(!onBoard(x-i, y-i)) {
					botleft = false;
				}
				else if(isEmpty(x-i, y-i)) {
					list.add(new Square(x-i, y-i));
				}
				else {
					botleft = false;
					if(isEnemy(x-i, y-i, p)) {
						list.add(new Square(x-i, y-i));
					}
				}
			}
		}
		return list;
	}
	
	private ArrayList<Square> addStraight(ArrayList<Square> list, int x, int y, Piece p) {
		boolean top = true;
		boolean right = true;
		boolean left = true;
		boolean bot = true;
		
		
		
		for(int i = 1; i < 8; i++) {
			
			//check top
			if(top) {
				if(!onBoard(x, y+i)) {
					top = false;
				}
				else if(!isEmpty(x, y+i)) {
					top = false;
					if(isEnemy(x, y+i, p)) {
						list.add(new Square(x, y+i));
					}
				}
				else list.add(new Square(x, y+i));
			}
			//check bottom
			if(bot) {
				if(!onBoard(x, y-i)) {
					bot = false;
				}
				else if(!isEmpty(x, y-i)) {
					bot = false;
					if(isEnemy(x, y-i, p)) {
						list.add(new Square(x, y-i));
					}
				}
				else list.add(new Square(x, y-i));
			}
			//check left
			if(left) {
				if(!onBoard(x-i, y)) {
					left = false;
				}
				else if(!isEmpty(x-i, y)) {
					left = false;
					if(isEnemy(x-i, y, p)) {
						list.add(new Square(x-i, y));
					}
				}
				else list.add(new Square(x-i, y));
			}
			//check right
			if(right) {
				if(!onBoard(x+i, y)) {
					right = false;
				}
				else if(!isEmpty(x+i, y)) {
					right = false;
					if(isEnemy(x+i, y, p)) {
						list.add(new Square(x+i, y));
					}
				}
				else list.add(new Square(x+i, y));
			}
		}
		
		return list;
	}
	
	private boolean checkList(Move m, ArrayList<Square> l) {
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
	private boolean isEmpty(int x, int y) {
		//should return true only if the no piece is there
		return model.getData().getPiece(x, y).getName().equals("None");
	}
	
	//checks if a given x, y coordinate is on the board
	private boolean onBoard(int x, int y) {
		return x < 8 && x >= 0 && y >= 0 && y < 8;
	}
	
	//returns true if colors are different
	private boolean isEnemy(int x, int y, Piece p) {
		return p.getColor() != model.getData().getPiece(x, y).getColor();
	}
	
	//checks to make sure the source piece is the same as the piece calling visit
	private boolean checkPiece(Move m, Piece p) {
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
