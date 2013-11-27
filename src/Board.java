import java.util.ArrayList;

public class Board {
	
    private Piece[][] board;
    private PieceColor turn;
    
    private Square wKing;
    private Square bKing;
    
    private boolean[] castle; //use an array to factor in kingside/queenside
    
    private ArrayList<String> moveList;
    
    public Board() {
        board = new Piece[8][8];
        turn = PieceColor.White;
        
        wKing = null;
        bKing = null;
        
        boolean[] temp = {true, true, true, true};
        castle = temp;
        
        moveList = new ArrayList<String>();
    }
    
    /* getter functions */
    
    //get the piece based on rank-file notation (such as g8)
    public Piece getPiece(String alg) {
    	if(alg.length() != 2) return null;
    	char file = alg.charAt(0);
    	int rank = Integer.parseInt(alg.substring(1));
    	return getPiece(file-'a', rank-1);
    }
    
    //get a piece based on coordinates
    public Piece getPiece(int x, int y) {
    	return board[y][x];
    }
    
    //returns a list of all pieces on the board with the input color
    private ArrayList<Piece> getPieces(PieceColor col) {
    	ArrayList<Piece> pieces = new ArrayList<Piece>();
    	Piece temp;
    	
    	for(int i = 0; i < 8; i++) {
    		for(int j = 0; j < 8; j++) {
    			temp = getPiece(j, i);
    			
    			//add piece to list if it's the same color
    			//NoPieces have their own color, so they won't pass this test
    			if(temp.getColor() == col) pieces.add(temp);
    		}
    	}
    	
    	return pieces;
    }
    
    //returns the location of a king based on input color
    public Square getKing(PieceColor c) {
    	if(c == PieceColor.White) return wKing;
    	else if(c == PieceColor.Black) return bKing;
    	return null;
    }
    
    //return current turn
    public PieceColor getTurn() {
    	return turn;
    }
    
    public ArrayList<String> getMoves() {
    	return moveList;
    }
    
    /* set functions */
    public void setPiece(int x, int y, Piece piece) {
    	board[y][x] = piece;
    }
    
    public void setTurn(PieceColor c) {
    	turn = c;
    }
    
    public void setKing(PieceColor c, Square s) {
    	if(c == PieceColor.White) wKing = s;
    	else if(c == PieceColor.Black) bKing = s;
    }
    
    //if kingSide is true, check castle rights on king side
    //otherwise check queen side
    public boolean canCastle(PieceColor c, boolean kingSide) {
    	if(c == PieceColor.White) {
    		if(kingSide) return castle[0];
    		else return castle[1];
    	}
    	else if(c == PieceColor.Black) {
    		if(kingSide) return castle[2];
    		else return castle[3];
    	}
    	return false;
    }
    
    //removes castling rights
    private void removeCastle(PieceColor c, boolean kingSide) {
    	if(c == PieceColor.White) {
    		if(kingSide) castle[0] = false;
    		else castle[1] = false;
    	}
    	else if(c == PieceColor.Black) {
    		if(kingSide) castle[2] = false;
    		else castle[3] = false;
    	}
    }
    
    //copies this board to another board object
    //currently, this might fail because I don't create new object instances
    //I need to find a way to create pieces
    private void copyBoard(Board b) {
    	//first copy the pieces on the board
    	for(int i = 0; i < 8; i++) {
    		for(int j = 0; j < 8; j++) {
    			b.setPiece(j, i, copyPiece(getPiece(j, i), b));
    		}
    	}
    	
    	//now copy the two kings
    	b.setKing(PieceColor.White, new Square(wKing.getX(), wKing.getY()));
    	b.setKing(PieceColor.Black, new Square(bKing.getX(), bKing.getY()));
    	
    	//copy the turn
    	b.setTurn(turn);
    	
    	//no need to copy castle rights because you can't castle while in check anyway
    }
    
    //returns a new piece with the same attributes as the input piece
    private Piece copyPiece(Piece p, Board b) {
    	int x = p.getLocation().getX();
    	int y = p.getLocation().getY();
    	PieceColor c = p.getColor();
    	
    	if(p.getName().equals("None")) return new NoPiece(x, y);
    	else if(p.getName().equals("Pawn")) return new Pawn(x, y, c, b);
    	else if(p.getName().equals("Knight")) return new Knight(x, y, c, b);
    	else if(p.getName().equals("Bishop")) return new Bishop(x, y, c, b);
    	else if(p.getName().equals("King")) return new King(x, y, c, b);
    	else if(p.getName().equals("Queen")) return new Queen(x, y, c, b);
    	else if(p.getName().equals("Rook")) return new Rook(x, y, c, b);
    	
    	return null;
    }
    
    //returns 0 if tie, -1 if black win, 1 if white wins, and 2 if no win
    //impossible to tie so far...will add functionality later
    public int gameState() {
    	boolean check = isCheck(turn);
    	
    	//change this code to be some sort of checkmate detection
    	if(check) {
    		//we need to check whether there is a legal move that puts the king out of check
    		//we need to loop through all pieces of the same color as the turn and make all legal moves
    		//then after each move, check whether the king is still in check
    		
    		System.out.println("Check on " + turn + " King!");
    		//first generate a list of all pieces for the turn color
    		ArrayList<Piece> pieces = getPieces(turn);
    		
    		boolean freedom;
    		
    		//now for each piece, check whether moving that piece can get the king out of check
    		for(int i = 0; i < pieces.size(); i++) {
    			freedom = simulate(pieces.get(i), turn);
    			if(freedom) return 2; //if the king can move, then the game isn't over yet
    		}
    		
    		if(turn == PieceColor.White) return -1; //black win if white king in check and can't move
    		if(turn == PieceColor.Black) return 1;
    	}
    	
    	//if all of these fail, the game isn't over yet
    	return 2;
    }
    
    //this should return true if moving the piece gets the king out of check
    //return false if there is no move that gets the king out of check
    private boolean simulate(Piece p, PieceColor col) {
    	ArrayList<Square> moves = p.getLegalMoves();
    	Square temp;
    	Move m;
    	Piece piece;
    	
    	//need to copy the board
    	Board copy = null;
    	
    	for(int i = 0; i < moves.size(); i++) {
    		temp = moves.get(i);
    		copy = new Board();
    		copyBoard(copy);
    		piece = copy.getPiece(p.getLocation().getX(), p.getLocation().getY());
    		m = new Move(piece, copy.getPiece(temp.getX(), temp.getY()));
    		if(copy.tryMove(m)) {
    			copy.makeMove(m);
    			if(!copy.isCheck(col)) return true;
    		}
    	}
    	
    	return false;
    }
    
    //returns whether the input color's king is in check
    public boolean isCheck(PieceColor c) {
    	int[][] map = generateAttackMaps(c);
    	int x = getKing(c).getX();
    	int y = getKing(c).getY();

    	//if the king is being threatened, return true
    	if(map[y][x] > 0) return true;
    	else return false;
    }
    
    //initializes the board with the normal starting pieces
    public void newGame() {
    	turn = PieceColor.White;
    	
    	//the middle are empty squares
    	for(int i = 2; i < 6; i++) {
    		for(int j = 0; j < 8; j++) {
    			setPiece(j, i, new NoPiece(j, i));
    		}
    	}
    	
    	//pawns
    	for(int i = 0; i < 8; i++) {
    		//make these pawns
    		setPiece(i, 1, new Pawn(i, 1, PieceColor.White, this));
    		setPiece(i, 6, new Pawn(i, 6, PieceColor.Black, this));
    	}
    	
    	//white back row
    	setPiece(0, 0, new Rook(0, 0, PieceColor.White, this));
    	setPiece(1, 0, new Knight(1, 0, PieceColor.White, this));
    	setPiece(2, 0, new Bishop(2, 0, PieceColor.White, this));
    	setPiece(3, 0, new Queen(3, 0, PieceColor.White, this));
    	setPiece(4, 0, new King(4, 0, PieceColor.White, this));
    	setPiece(5, 0, new Bishop(5, 0, PieceColor.White, this));
    	setPiece(6, 0, new Knight(6, 0, PieceColor.White, this));
    	setPiece(7, 0, new Rook(7, 0, PieceColor.White, this));
    	
    	//black back row
    	setPiece(0, 7, new Rook(0, 7, PieceColor.Black, this));
    	setPiece(1, 7, new Knight(1, 7, PieceColor.Black, this));
    	setPiece(2, 7, new Bishop(2, 7, PieceColor.Black, this));
    	setPiece(3, 7, new Queen(3, 7, PieceColor.Black, this));
    	setPiece(4, 7, new King(4, 7, PieceColor.Black, this));
    	setPiece(5, 7, new Bishop(5, 7, PieceColor.Black, this));
    	setPiece(6, 7, new Knight(6, 7, PieceColor.Black, this));
    	setPiece(7, 7, new Rook(7, 7, PieceColor.Black, this));
    	
    	//store locations of king so they can be checked
    	wKing = new Square(4, 0);
    	bKing = new Square(4, 7);
    }
    
    //each point on the map corresponds to the number of attacking pieces
    //generates a map for the input color
    //map will show number of pieces of color opposite to input that are threatening each square
    public int[][] generateAttackMaps(PieceColor col) {
    	ArrayList<Square> moves;
    	int[][] map = new int[8][8];
    	int x, y;
    	
    	if(col == PieceColor.None) {
    		System.out.println("Attack Map got the wrong color.");
    		System.exit(0);
    	}
    	
    	//generate a list of all opposing pieces
    	ArrayList<Piece> pieces = getPieces(PieceColor.opposite(col));
    	
    	for(int i = 0; i < pieces.size(); i++) {
			moves = pieces.get(i).getThreatList();
			
			if(moves != null) {
    			//loop through legal moves
    			//add 1 to the target square's map
    			for(int j = 0; j < moves.size(); j++) {
    				x = moves.get(j).getX();
    				y = moves.get(j).getY();
    				
    				//add each possible move to the threat map
    				map[y][x]++;
    			}
			}
    	}
    	return map;
    }
    
    //for debug
    public void printBoard() {
    	for(int i = 7; i >= 0; i--) {
    		for(int j = 0; j < 8; j++) {
    			System.out.print(""+getPiece(j, i).getColor().toString().charAt(0)+getPiece(j, i).getName().charAt(0)+"\t");
    		}
    		System.out.println();
    	}
    }
    
    public void printMap(int[][] map) {
    	for(int i = 7; i >= 0; i--) {
    		for(int j = 0; j < 8; j++) {
    			System.out.print(""+map[i][j]+"\t");
    		}
    		System.out.println();
    	}
    }
    
    public void printMoveList() {
    	for(int i = 0; i < moveList.size(); ++i) {
    		System.out.println(moveList.get(i));
    	}
    }
    
    //I should change this function to take in Pieces or something, rather than the move itself
    //calling m.something.something(m) seems dumb
    public boolean tryMove(Move m) {
    	if(m.getSource().getColor() != turn) return false;
    	return m.getSource().move(m);
    }
    
    //should only be called after tryMove returns true
    public void makeMove(Move m) {
    	PieceColor moved = m.getSource().getColor();

    	//before the move is made, record the move in the movelist
    	updateMoveList(m);
    	
    	//store locations
    	int sx = m.getSource().getLocation().getX();
    	int sy = m.getSource().getLocation().getY();
    	int dx = m.getDest().getLocation().getX();
    	int dy = m.getDest().getLocation().getY();
    	String name_moved = m.getSource().getName();
    	
    	//store new king location if it moved
    	if(name_moved.equals("King")) {
    		if(moved == PieceColor.White) {
    			wKing = m.getDest().getLocation();
    			
    			//remove castle rights on both sides
    			castle[0] = false;
    			castle[1] = false;
    		}
    		else if(moved == PieceColor.Black) {
    			bKing = m.getDest().getLocation();
    			
    			//remove castle rights on both sides
    			castle[2] = false;
    			castle[3] = false;
    		}
    		else System.exit(0);
    	}
    	
    	//rook check for castling rights
    	if(name_moved.equals("Rook")) {
    		if(moved == PieceColor.White) {
    			if(sx == 0 && sy == 0) removeCastle(moved, false);
    			if(sx == 7 && sy == 0) removeCastle(moved, true);
    		}
    		
    		else if(moved == PieceColor.Black) {
    			if(sx == 0 && sy == 7) removeCastle(moved, false);
    			if(sx == 7 && sy == 7) removeCastle(moved, true);
    		}
    	}
    	
    	Piece temp = getPiece(sx, sy);
    	
    	setPiece(sx, sy, new NoPiece(sx, sy));
    	
    	temp.setLocation(new Square(dx, dy));
    	setPiece(dx, dy, temp);
    	
    	if(turn == PieceColor.White) turn = PieceColor.Black;
    	else turn = PieceColor.White;
    	
    	
    	
    	//check if the move was a castle
    	if(name_moved.equals("King")) {
    		
    		if(moved == PieceColor.White) { 
	    		//if king moved positive x, then moved king side
	    		if(dx - sx == 2) {
	    			temp = getPiece(7, 0);
	    			setPiece(7, 0, new NoPiece(7, 0));
	    			
	    			temp.setLocation(new Square(5,0));
	    			setPiece(5, 0, temp);
	    		}
	    		
	    		else if(sx - dx == 2) {
	    			temp = getPiece(0, 0);
	    			setPiece(0, 0, new NoPiece(0, 0));
	    			
	    			temp.setLocation(new Square(3, 0));
	    			setPiece(3, 0, temp);
	    		}
    		}
    		
    		if(moved == PieceColor.Black) { 
	    		//if king moved positive x, then moved king side
	    		if(dx - sx == 2) {
	    			temp = getPiece(7, 7);
	    			setPiece(7, 7, new NoPiece(7, 7));
	    			
	    			temp.setLocation(new Square(5, 7));
	    			setPiece(5, 7, temp);
	    		}
	    		
	    		else if(sx - dx == 2) {
	    			temp = getPiece(0, 7);
	    			setPiece(0, 7, new NoPiece(0, 7));
	    			
	    			temp.setLocation(new Square(3, 7));
	    			setPiece(3, 7, temp);
	    		}
    		}
    	}
    }

    //simple for now, but want to add support for castling and such later
    private void updateMoveList(Move m) {
    	//first check for castling move
    	if(m.getSource().getName().equals("King")) {
        	int sx = m.getSource().getLocation().getX();
        	int dx = m.getDest().getLocation().getX();
        	
        	//castle king side
        	if(dx - sx == 2) {
        		moveList.add("0-0");
        		return;
        	}
        	//castle queen side
        	else if(sx - dx == 2) {
        		moveList.add("0-0-0");
        		return;
        	}
    	}
    	
    	//now do normal checks for moves
    	//if no piece, normal notation
    	if(m.getDest().getName().equals("None")) {
    		moveList.add(m.getSource().getID() + m.getDest().getLocation().getNotation());
    	}
    	//add "x" for capturing
    	//for pawn, it's a bit different
    	else if(m.getSource().getName().equals("Pawn")) {
    		moveList.add(m.getSource().getLocation().getFile()+ "x" + m.getDest().getLocation().getNotation());
    	}
    	else {
    		moveList.add(m.getSource().getID() + "x" + m.getDest().getLocation().getNotation());
    	}
    }
}
