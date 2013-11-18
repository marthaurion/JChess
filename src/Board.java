import java.util.ArrayList;

public class Board {
    private Piece[][] board;
    private PieceColor turn;
    
    private Square wKing;
    private Square bKing;
    
    private boolean wCastle;
    private boolean bCastle;
    
    private ArrayList<String> moveList;
    
    public Board() {
        board = new Piece[8][8];
        turn = PieceColor.White;
        
        wKing = null;
        bKing = null;
        
        wCastle = true;
        bCastle = true;
        
        moveList = new ArrayList<String>();
    }
    
    /* getter functions */
    public Piece[][] getBoard() {
    	return board;
    }
    
    //get the piece based on rank-file notation (such as g8)
    public Piece getPiece(String alg) {
    	if(alg.length() != 2) return null;
    	char file = alg.charAt(0);
    	int rank = Integer.parseInt(alg.substring(1));
    	return board[rank-1][file-'a'];
    }
    
    //get a piece based on coordinates
    public Piece getPiece(int x, int y) {
    	return board[y][x];
    }
    
    //just for displaying current turn
    public String getTurn() {
    	if(turn == PieceColor.White) return "White";
    	else return "Black";
    }
    
    public ArrayList<String> getMoves() {
    	return moveList;
    }
    
    public boolean canCastle(PieceColor c) {
    	if(c == PieceColor.Black) return bCastle;
    	else if(c == PieceColor.White) return wCastle;
    	return false;
    }
    
    
    //returns 0 if tie, -1 if black win, 1 if white wins, and 2 if no win
    //impossible to tie so far...will add functionality later
    public int gameState() {
    	boolean surrounded = true; //stores whether king has safe moves
    	boolean check;
    	
    	//check white king first
    	ArrayList<Square> moves = board[wKing.getY()][wKing.getX()].getLegalMoves();
    	int[][] map = generateAttackMaps(PieceColor.White);
    	
    	for(int i = 0; i < moves.size(); i++) {
    		if(map[moves.get(i).getY()][moves.get(i).getX()] == 0) {
    			surrounded = false;
    		}
    	}
    	
    	check = isCheck(PieceColor.White);
    	if(check && surrounded) return -1; //black win if white king in check and can't move
    	if(check) System.out.println("Check on White King!");
    	//check black king now
    	surrounded = true;
    	
    	moves = board[bKing.getY()][bKing.getX()].getLegalMoves();
    	map = generateAttackMaps(PieceColor.Black);
    	
    	for(int i = 0; i < moves.size(); i++) {
    		if(map[moves.get(i).getY()][moves.get(i).getX()] == 0) {
    			surrounded = false;
    		}
    	}
    	
    	check = isCheck(PieceColor.Black);
    	if(check && surrounded) return 1; //white win if black king in check and can't move
    	if(check) System.out.println("Check on Black King!");
    	//if all of these fail, the game isn't over yet
    	return 2;
    }
    
    //returns whether the input color's king is in check
    private boolean isCheck(PieceColor c) {
    	int[][] map = generateAttackMaps(c);
    	int x, y;
    	if(c == PieceColor.White) {
    		x = wKing.getX();
    		y = wKing.getY();
    	}
    	else {
    		x = bKing.getX();
    		y = bKing.getY();
    	}
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
    			board[i][j] = new NoPiece(j, i);
    		}
    	}
    	
    	//pawns
    	for(int i = 0; i < 8; i++) {
    		//make these pawns
    		board[1][i] = new Pawn(i, 1, PieceColor.White, this);
    		board[6][i] = new Pawn(i, 6, PieceColor.Black, this);
    	}
    	
    	//white back row
    	board[0][0] = new Rook(0, 0, PieceColor.White, this);
    	board[0][1] = new Knight(1, 0, PieceColor.White, this);
    	board[0][2] = new Bishop(2, 0, PieceColor.White, this);
    	board[0][3] = new Queen(3, 0, PieceColor.White, this);
    	board[0][4] = new King(4, 0, PieceColor.White, this);
    	board[0][5] = new Bishop(5, 0, PieceColor.White, this);
    	board[0][6] = new Knight(6, 0, PieceColor.White, this);
    	board[0][7] = new Rook(7, 0, PieceColor.White, this);
    	
    	//black back row
    	board[7][0] = new Rook(0, 7, PieceColor.Black, this);
    	board[7][1] = new Knight(1, 7, PieceColor.Black, this);
    	board[7][2] = new Bishop(2, 7, PieceColor.Black, this);
    	board[7][3] = new Queen(3, 7, PieceColor.Black, this);
    	board[7][4] = new King(4, 7, PieceColor.Black, this);
    	board[7][5] = new Bishop(5, 7, PieceColor.Black, this);
    	board[7][6] = new Knight(6, 7, PieceColor.Black, this);
    	board[7][7] = new Rook(7, 7, PieceColor.Black, this);
    	
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
    	
    	for(int i = 0; i < 8; i++) {
    		for(int j = 0; j < 8; j++) {
    			moves = board[i][j].getThreatList();
    			
    			if(moves != null) {
	    			//loop through legal moves
	    			//add 1 to the target square's map
	    			for(int k = 0; k < moves.size(); k++) {
	    				x = moves.get(k).getX();
	    				y = moves.get(k).getY();
	    				
	    				//add pieces of the opposite color
	    				if(board[i][j].getColor() != col) map[y][x]++;
	    			}
    			}
    		}
    	}
    	return map;
    }
    
    //for debug
    public void printBoard() {
    	for(int i = 7; i >= 0; i--) {
    		for(int j = 0; j < 8; j++) {
    			System.out.print(""+board[i][j].getColor().toString().charAt(0)+board[i][j].getName().charAt(0)+"\t");
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
    	
    	//store new king location if it moved
    	if(m.getSource().getName().equals("King")) {
    		if(moved == PieceColor.White) {
    			wKing = m.getDest().getLocation();
    			wCastle = false;
    		}
    		else if(moved == PieceColor.Black) {
    			bKing = m.getDest().getLocation();
    			bCastle = false;
    		}
    		else System.exit(0);
    	}
    	
    	Piece temp = board[sy][sx];
    	
    	board[sy][sx] = new NoPiece(sx, sy);
    	
    	temp.setLocation(new Square(dx, dy));
    	board[dy][dx] = temp;
    	
    	if(turn == PieceColor.White) turn = PieceColor.Black;
    	else turn = PieceColor.White;
    }

    //simple for now, but want to add support for castling and such later
    public void updateMoveList(Move m) {
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
