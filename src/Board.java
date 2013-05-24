public class Board {
    private Square[][] board;
    
    public Board() {
        board = new Square[8][8];
        //construct each square
    }
    
    public Square[][] getBoard() {
    	return board;
    }
    
    //get the square based on rank-file notation (such as g8)
    public Square getSquare(String alg) {
    	if(alg.length() != 2) return null;
    	char file = alg.charAt(0);
    	int rank = Integer.parseInt(alg.substring(1));
    	return board[rank-1][file-'a'];
    }
    
    //initializes the board with the normal starting pieces
    public void newGame() {
    	//the middle empty squares
    	for(int i = 2; i < 6; i++) {
    		for(int j = 0; j < 8; j++) {
    			board[i][j] = new Square(null, i, j);
    		}
    	}
    	
    	//pawns
    	for(int i = 0; i < 8; i++) {
    		board[1][i] = new Square(new Piece(PieceType.Pawn, PieceColor.White), 1, i);
    		board[6][i] = new Square(new Piece(PieceType.Pawn, PieceColor.Black), 6, i);
    	}
    	
    	
    	
    }
}
