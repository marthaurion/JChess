public class Board {
    private Piece[][] board;
    
    public Board() {
        board = new Piece[8][8];
        //construct each piece
    }
    
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
    
    //initializes the board with the normal starting pieces
    public void newGame() {
    	//the middle are empty squares
    	for(int i = 2; i < 6; i++) {
    		for(int j = 0; j < 8; j++) {
    			//need to make empty pieces
    		}
    	}
    	
    	//pawns
    	for(int i = 0; i < 8; i++) {
    		//make these pawns
    	}
    	
    	
    	
    }
}
