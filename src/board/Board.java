package board;
import java.util.ArrayList;
import java.util.Random;

import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.NoPiece;
import pieces.Pawn;
import pieces.Piece;
import pieces.PieceColor;
import pieces.PieceFactory;
import pieces.Queen;
import pieces.Rook;
import pieces.Square;

/**
 * Abstraction for the chess board.
 * @author marthaurion
 */
public class Board {
	
    private Piece[][] board;
    private PieceColor turn;
    
    private Square wKing;
    private Square bKing;
    
    private boolean[] castle; //use an array to factor in king/queen side
    
    private ArrayList<String> moveList;
    
    /** 
     * Initializes the board.
     */
    public Board() {
        board = new Piece[8][8];
        turn = PieceColor.White;
        
        wKing = null;
        bKing = null;
        
        boolean[] temp = {true, true, true, true};
        castle = temp;
        
        moveList = new ArrayList<String>();
    }
    
    // getter functions
    
    /**
     * Get a piece on the board based on rank-file notation (such as g8).
     * @param alg A two-character string with the algebraic notation for a square.
     * @return Piece object for the input string
     */
    public Piece getPiece(String alg) {
    	if(alg.length() != 2) return null;
    	char file = alg.charAt(0);
    	int rank = Integer.parseInt(alg.substring(1));
    	return getPiece(file-'a', rank-1);
    }
    
    
    /**
     * Get a piece on the board based on x-y coordinates.
     * @param x The x-coordinate (column) of the piece.
     * @param y The y-coordinate (row) of the piece.
     * @return The piece at location (x, y) on the board.
     */
    public Piece getPiece(int x, int y) {
    	return board[y][x];
    }
    
    /**
     * Get a piece on the board based on a Square object.
     * @param s Square object signifying the location for the piece we want.
     * @return Piece object on that square.
     */
    public Piece getPiece(Square s) {
    	return this.getPiece(s.getX(), s.getY());
    }
    
    /**
     * Returns a list of all pieces on the board with the input color.
     * @param color PieceColor of the pieces that will be grabbed.
     * @return List of Piece objects of the given color.
     */
    private ArrayList<Piece> getPieces(PieceColor color) {
    	ArrayList<Piece> pieces = new ArrayList<Piece>();
    	Piece temp;
    	
    	for(int i = 0; i < 8; i++) {
    		for(int j = 0; j < 8; j++) {
    			temp = getPiece(j, i);
    			
    			//add piece to list if it's the same color
    			//NoPieces have their own color, so they won't pass this test
    			if(temp.getColor() == color) pieces.add(temp);
    		}
    	}
    	
    	return pieces;
    }
    
    /**
     * Returns the location of a king based on input color.
     * @param color PieceColor value of the king that is being grabbed.
     * @return Piece object for the appropriate king or null if PieceColor None is input.
     */
    public Square getKing(PieceColor color) {
    	if(color == PieceColor.White) return wKing;
    	else if(color == PieceColor.Black) return bKing;
    	return null;
    }
    
    /**
     * Return current turn.
     * @return PieceColor of the current turn.
     */
    public PieceColor getTurn() {
    	return turn;
    }
    
    /**
     * Return a list of all moves that have been made.
     * @return List of strings that represent each move.
     */
    public ArrayList<String> getMoves() {
    	return moveList;
    }
    
    /**
     * Calculates the total material score of an input player's pieces.
     * @param color PieceColor of the player whose pieces are being scored.
     * @return Total material score of all pieces on the board of input color.
     */
    public int getMaterialScore(PieceColor color) {
    	int score = 0;
    	ArrayList<Piece> pieces = getPieces(color);
    	for(int i = 0; i < pieces.size(); ++i) {
    		score += pieces.get(i).getValue();
    	}
    	
    	return score;
    }
    
    /* set functions */
    
    /**
     * Puts input Piece on the board.
     * @param x The x-coordinate (column) for the piece.
     * @param y The y-coordinate (row) for the piece.
     * @param piece Piece object to put at location (x, y).
     */
    public void setPiece(int x, int y, Piece piece) {
    	board[y][x] = piece;
    }
    
    /**
     * Set the turn PieceColor to a new input color.
     * @param color New PieceColor to set for the turn.
     */
    public void setTurn(PieceColor color) {
    	turn = color;
    }
    
    /**
     * Change the square of the input color's king.
     * @param color Indicates which king to change.
     * @param sq New location for the king.
     */
    public void setKing(PieceColor color, Square sq) {
    	if(color == PieceColor.White) wKing = sq;
    	else if(color == PieceColor.Black) bKing = sq;
    }
    
    /**
     * Initializes the board with the normal starting pieces for a chess game.
     */
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
    
    /**
     * If kingSide is true, check castle rights on king side.
     * Otherwise, check queen side.
     * @param color PieceColor for which player's castling rights to check.
     * @param kingSide Indicates which side to check for castle rights.
     * @return Boolean indicating whether it is possible for the input castle move.
     */
    public boolean canCastle(PieceColor color, boolean kingSide) {
    	if(color == PieceColor.White) {
    		if(kingSide) return castle[0];
    		else return castle[1];
    	}
    	else if(color == PieceColor.Black) {
    		if(kingSide) return castle[2];
    		else return castle[3];
    	}
    	return false;
    }
    
    /**
     * Removes castling rights from a player.
     * @param color PieceColor for which player's castling rights to remove.
     * @param kingSide Indicates which side to remove castle rights.
     */
    private void removeCastle(PieceColor color, boolean kingSide) {
    	if(color == PieceColor.White) {
    		if(kingSide) castle[0] = false;
    		else castle[1] = false;
    	}
    	else if(color == PieceColor.Black) {
    		if(kingSide) castle[2] = false;
    		else castle[3] = false;
    	}
    }
    
    /**
     * Copies this board to another board object.
     * @param b The other board that will become a copy of this board.
     */
    public void copyBoard(Board b) {
    	//first copy the pieces on the board
    	for(int i = 0; i < 8; i++) {
    		for(int j = 0; j < 8; j++) {
    			b.setPiece(j, i, PieceFactory.createPiece(getPiece(j, i), b));
    		}
    	}
    	
    	//now copy the two kings
    	b.setKing(PieceColor.White, new Square(wKing.getX(), wKing.getY()));
    	b.setKing(PieceColor.Black, new Square(bKing.getX(), bKing.getY()));
    	
    	//copy the turn
    	b.setTurn(turn);
    	
    	//no need to copy castle rights because you can't castle while in check anyway
    }
    
    /**
     * Returns the current win/loss state of the game.
     * Impossible to tie so far. Will add functionality later.
     * @return 0 if tie, -1 if black win, 1 if white wins, and 2 if no win
     */
    public int gameState() {
    	boolean check = isCheck(turn);
    	
    	//change this code to be some sort of checkmate detection
    	if(check) {
    		//we need to check whether there is a legal move that puts the king out of check
    		//we need to loop through all pieces of the same color as the turn and make all legal moves
    		//then after each move, check whether the king is still in check
    		
    		//first generate a list of all pieces for the turn color
    		ArrayList<Piece> pieces = getPieces(turn);
    		
    		boolean freedom;
    		
    		//now for each piece, check whether moving that piece can get the king out of check
    		for(int i = 0; i < pieces.size(); i++) {
    			freedom = simulate(pieces.get(i));
    			if(freedom) {
    	    		updateCheckMove(false);
    	    		System.out.println("Check on " + turn + " King!");
    				return 2; //if the king can move, then the game isn't over yet
    			}
    		}
    		
    		//the game is over if we reach this far, so we can assume checkmate
    		//resignation logic will probably be specific to Display class
    		
    		updateCheckMove(true);
    		
    		if(turn == PieceColor.White) return -1; //black win if white king in check and can't move
    		if(turn == PieceColor.Black) return 1;
    	}
    	
    	//if all of these fail, the game isn't over yet
    	return 2;
    }
    
    /**
     * Simulates every move the input piece can make
     * to see whether it gets their king out of check.
     * @param p Piece to be simulated on the copy board.
     * @return True if moving the piece gets the king out of check and false otherwise.
     */
    //return false if there is no move that gets the king out of check
    private boolean simulate(Piece p) {
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
    			if(!copy.isCheck(p.getColor())) return true;
    		}
    	}
    	
    	return false;
    }
    
    /**
     * Method for evaluating whether a king is in check.
     * @param color PieceColor for the player's king to evaluate.
     * @return Boolean indicating whether the input king is in check.
     */
    public boolean isCheck(PieceColor color) {
    	int[][] map = generateAttackMaps(color);
    	int x = getKing(color).getX();
    	int y = getKing(color).getY();

    	//if the king is being threatened, return true
    	if(map[y][x] > 0) return true;
    	else return false;
    }
    
    
    /**
     * Generates a map for the input color.
     * Each point on the map corresponds to the number of pieces
     * of the opposite color that are threatening that point on the board.
     * This probably should be changed to use an "Attack Map object".
     * 
     * @param color PieceColor for the color the threats are against. Shows the threats for the opposite color.
     * @return Array of threats against the input color.
     */
    public int[][] generateAttackMaps(PieceColor color) {
    	ArrayList<Square> moves;
    	int[][] map = new int[8][8];
    	int x, y;
    	
    	if(color == PieceColor.None) {
    		System.out.println("Attack Map got the wrong color.");
    		System.exit(0);
    	}
    	
    	//generate a list of all opposing pieces
    	ArrayList<Piece> pieces = getPieces(PieceColor.opposite(color));
    	
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
    
    /**
     * Debug method. Will be removed.
     */
    public void printBoard() {
    	for(int i = 7; i >= 0; i--) {
    		for(int j = 0; j < 8; j++) {
    			System.out.print(""+getPiece(j, i).getColor().toString().charAt(0)+getPiece(j, i).getName().charAt(0)+"\t");
    		}
    		System.out.println();
    	}
    }
    
    /**
     * Debug method. Will be removed.
     */
    public void printMap(int[][] map) {
    	for(int i = 7; i >= 0; i--) {
    		for(int j = 0; j < 8; j++) {
    			System.out.print(""+map[i][j]+"\t");
    		}
    		System.out.println();
    	}
    }
    
    /**
     * Debug method. Will be removed.
     */
    public void printMoveList() {
    	int x = 1;
    	for(int i = 0; i < moveList.size(); ++i) {
    		if(i%2 == 0) {
    			System.out.print("" + x + ". " + moveList.get(i));
    			x++;
    		}
    		else {
    			System.out.print(" "+moveList.get(i));
    			System.out.println();
    		}
    	}
    }
    
    /**
     * Tests the input move for legality.
     * @param m Input Move object.
     * @return Boolean indicating whether the move is legal.
     */
    public boolean tryMove(Move m) {
    	if(m.getSource().getColor() != turn) return false;
    	return m.getSource().move(m);
    }
    
    /**
     * Makes the input move on the board.
     * Should only be called after tryMove returns true.
     * @param m Input Move object to be made.
     */
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
    	
    	if(temp.getName().equals("Pawn") && ((Pawn)temp).getPromo() == dy) {
    		temp = new Queen(dx, dy, temp.getColor(), this);
    	}
    	
    	
    	temp.setLocation(new Square(dx, dy));
    	
    	//check for en passant
    	if(name_moved.equals("Pawn")) {
    		int loc = ((Pawn)temp).advance();
    		((Pawn)temp).setMoved(moveList.size());
    		
    		//only a valid en passant move if the pawn moves diagonally
    		//and the destination square is empty
    		if(sx != dx && getPiece(dx, dy).getName().equals("None")) {
    			setPiece(dx, dy-loc, new NoPiece(dx, dy-loc));
    		}
    	}
    	
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

    /**
     * Updates the board's move list.
     * @param m Move object to be added to the list.
     */
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
    		
    		//check for en passant
    		if(m.getSource().getName().equals("Pawn")) {
    			
    			//it's only en passant if the pawn moves diagonally to an empty square 
    			if(m.getSource().getLocation().getX() != m.getDest().getLocation().getX()) {
    				moveList.add(m.getSource().getLocation().getFile()+ "x" + m.getDest().getLocation().getNotation());
    				return;
    			}
    		}
    		
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
    
    /**
     * Adds check/checkmate notation to the last move in the move list.
     * @param mate Boolean indicating whether this is a checkmate being updated.
     */
    private void updateCheckMove(boolean mate) {
    	if(moveList.size() > 0) {
    		int x = moveList.size()-1;
    		if(mate) moveList.set(x, moveList.get(x) + "#");
    		else moveList.set(x, moveList.get(x)+"+");
    	}
    }
    
    /**
     * Generates a random move for one of the players on the board out of all possible moves.
     * This is the first "AI".
     * @param color PieceColor for the side of the board to find moves.
     * @return Move object for a random legal move.
     */
    public Move getRandomMove(PieceColor color) {
    	ArrayList<Piece> pieces = getPieces(color);
    	ArrayList<Move> moves = new ArrayList<Move>();
    	Piece temp;
    	ArrayList<Square> squares;
    	Random rand = new Random();
    	
    	//loop through each of the pieces on the board for a color
    	//and add its list of legal moves to the total move list
    	for(int i = 0; i < pieces.size(); ++i) {
    		temp = pieces.get(i);
    		squares = temp.getLegalMoves();
    		if(squares == null || squares.size() < 1) continue; //skip this piece if there are no legal moves
    		
    		//after we grab all of the legal squares, we need to translate them into moves
    		for(int j = 0; j < squares.size(); ++j) {
    			//create a move for this piece to each of the target squares
    			moves.add(new Move(temp, this.getPiece(squares.get(j))));
    		}
    	}
    	
    	//if we don't find any legal moves, return null
    	//(this shouldn't happen because we should end the game before that happens)
    	if(moves.size() < 1) return null;
    	//otherwise return a random move in the list of legal moves
    	else return moves.get(rand.nextInt(moves.size()));
    }
}
