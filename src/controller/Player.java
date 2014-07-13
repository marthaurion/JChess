package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import online.PlayerProxy;
import pieces.Piece;
import pieces.PieceColor;
import view.BasicDisplay;
import view.ChessButton;
import board.Board;
import board.Move;

/**
 * Manages user input.
 * @author marthaurion
 *
 */
public class Player implements ActionListener {
	private BasicDisplay display;
	private PieceColor color;
	private Board board;
	private int sourceX;
	private int sourceY;
	private PlayerProxy proxy;
	private boolean online;
	private boolean computer;
	
	/**
	 * Constructor links the controller to a view and a model.
	 * @param on - Boolean to indicate whether this player is an online player.
	 * @param com - Boolean to indicate whether we want to play against a computer.
	 * @throws IOException 
	 */
	public Player(boolean on, boolean com) throws IOException {
		board = new Board();
		sourceX = -1;
		sourceY = -1;
		online = on;
		
		//if it's an online game, we won't use a computer for now
		if(online) computer = false;
		else computer = com;
		
		//only use the proxy if it's an online game
		if(online) {
			proxy = new PlayerProxy(this);
			color = proxy.startGame(); //initialize the proxy
		}
		
		display = new BasicDisplay(board, this);
		display.initialize(color == PieceColor.Black);
		if(online && color == PieceColor.Black) {
			Move m = proxy.getMove();
			
			board.makeMove(m);
			
			display.initialize(false);
		}
		
		if(computer) color = PieceColor.White; //for now, let the player be white
	}
	
	public void endGame(int state) throws IOException {
		if(online) proxy.endGame(); //close connections
		if(state == 1) display.endGame(PieceColor.White);
		else if(state == -1) display.endGame(PieceColor.Black);
		else if(state == 0) display.endGame(PieceColor.None);
	}
	
	//this method probably isn't needed
	public PieceColor getColor() {
		return color;
	}
	
	public Board getBoard() {
		return board;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		ChessButton source = (ChessButton)e.getSource();
		
		//if there is no source, set this to be the source
		if(sourceX == -1 && sourceY == -1) {
			sourceX = source.getMyX();
			sourceY = source.getMyY();
			display.getButtonAt(sourceX, sourceY).activate();
			
			display.highlightPossible(sourceX, sourceY);
		}
		//otherwise create the move and send to the model
		else {
			int x = source.getMyX();
			int y = source.getMyY();
			
			//if the same button is clicked, cancel the selection
			if(sourceX == x && sourceY == y) {
				sourceX = -1;
				sourceY = -1;
				display.clearBoard();
			}
			
			//otherwise try the move and make if it is legal
			else {
				Piece src = board.getPiece(sourceX, sourceY);
				Piece dest = board.getPiece(x, y);
				Move m = new Move(src, dest);
		    	
				boolean flag = board.tryMove(m);
				//reset now that the move has been registered
				sourceX = -1;
				sourceY = -1;
				
				//make the move if legal and the color of the piece matches the player
				if(flag) {
					try {
						//store the move as a string because making the move on the board alters it
						String move = m.toString();
						
						//make the move
						board.makeMove(m);
						
						//after making the move, check if the king is checkmated
						int temp = board.gameState();
						
						//update display
						display.displayMoves();
						display.displayBoard();
						
						//send the move after updating the board
						if(online) proxy.sendMessage(move);
						
						//then check for endgame conditions
						if(temp != 2) endGame(temp);
						
						//special cases where we don't make the next move locally
						if(online || computer) {
							if(online) m = proxy.getMove(); //get the other player's move and make it if we're playing an online game
							else m = board.getRandomMove(PieceColor.opposite(color)); //get random move for computer otherwise
							if(m == null) System.out.println("MOVE WAS NULL"); //debug line
							board.makeMove(m);
							temp = board.gameState();
							
							//update display
							display.displayMoves();
							display.displayBoard();
							if(temp != 2) endGame(temp);
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				//endif flag
				else {
					System.out.println("Invalid move"); //for debug
					display.invalid();
					display.clearBoard();
				}
			}
			//endelse
		}
		//endelse
	}
}
