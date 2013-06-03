import java.io.IOException;

public class Board {
    private Piece[][] board;
    private PieceColor turn;
    private Player player;
    
    public Board(PieceColor c, Model m) throws IOException {
    	player = new Player(m);
        board = new Piece[8][8];
        turn = PieceColor.White;
    }
    
    public Piece[][] getBoard() {
    	return board;
    }
    
    public Player getPlayer() {
    	return player;
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
    		board[1][i] = new Pawn(i, 1, PieceColor.White);
    		board[6][i] = new Pawn(i, 6, PieceColor.Black);
    	}
    	
    	//white back row
    	board[0][0] = new Rook(0, 0, PieceColor.White);
    	board[0][1] = new Knight(1, 0, PieceColor.White);
    	board[0][2] = new Bishop(2, 0, PieceColor.White);
    	board[0][3] = new Queen(3, 0, PieceColor.White);
    	board[0][4] = new King(4, 0, PieceColor.White);
    	board[0][5] = new Bishop(5, 0, PieceColor.White);
    	board[0][6] = new Knight(6, 0, PieceColor.White);
    	board[0][7] = new Rook(7, 0, PieceColor.White);
    	
    	//black back row
    	board[7][0] = new Rook(0, 7, PieceColor.Black);
    	board[7][1] = new Knight(1, 7, PieceColor.Black);
    	board[7][2] = new Bishop(2, 7, PieceColor.Black);
    	board[7][3] = new Queen(3, 7, PieceColor.Black);
    	board[7][4] = new King(4, 7, PieceColor.Black);
    	board[7][5] = new Bishop(5, 7, PieceColor.Black);
    	board[7][6] = new Knight(6, 7, PieceColor.Black);
    	board[7][7] = new Rook(7, 7, PieceColor.Black);
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
    
    public boolean tryMove(Move m, PieceVisitor v) {
    	//checks if the right player is moving
    	if(player.getColor() != turn) return false;
    	
    	//now does the visitor check
    	return m.getSource().acceptVisitor(v, m);
    }
    
    //should only be called after tryMove returns true
    public void makeMove(Move m) throws IOException {
    	//send move first
    	if(player.getColor() == turn) {
    		player.sendMove(m);
    		System.out.println("Move sent.");
    	}
    	System.out.println("Current turn: "+turn.toString());
    	int x = m.getSource().getLocation().getX();
    	int y = m.getSource().getLocation().getY();
    	
    	Piece temp = board[y][x];
    	
    	board[y][x] = new NoPiece(x, y);
    	
    	x = m.getDest().getLocation().getX();
    	y = m.getDest().getLocation().getY();
    	
    	temp.setLocation(new Square(x, y));
    	board[y][x] = temp;
    	
    	if(turn == PieceColor.White) turn = PieceColor.Black;
    	else turn = PieceColor.White;
    }
    
    public void waitMove() throws IOException {
    	if(turn != player.getColor()) {
    		Move m = player.waitMove();
    		if(m == null) endGame(turn);
    		makeMove(m);
    	}
    }
    
    public void endGame(PieceColor c) {
    	player.endGame(c);
    }
}
