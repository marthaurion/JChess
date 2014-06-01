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
	
	/**
	 * Constructor links the controller to a view and a model.
	 * @param on - Boolean to indicate whether this player is an online player.
	 * @throws IOException 
	 */
	public Player(boolean on) throws IOException {
		board = new Board();
		sourceX = -1;
		sourceY = -1;
		online = on;
		
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
	}
	
	public void endGame(int state) throws IOException {
		proxy.endGame(); //close connections
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
						//send the move to the player first because making the move changes the move data
						if(online) proxy.sendMessage(m);
						
						//make the move
						board.makeMove(m);
						
						//after making the move, check if the king is checkmated
						int temp = board.gameState();
						
						//update display
						display.displayMoves();
						display.displayBoard();
						
						//then check for endgame conditions
						if(temp != 2) endGame(temp);
						
						//get the other player's move and make it if we're playing an online game
						if(online) {
							m = proxy.getMove();
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
					display.clearBoard();
				}
			}
			//endelse
		}
		//endelse
	}
}
