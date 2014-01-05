package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import pieces.Piece;
import pieces.PieceColor;
import board.Board;
import board.Move;

/**
 * Manages user input.
 * @author marthaurion
 *
 */
public class Controller implements ActionListener {
	private BasicDisplay display;
	private Board board;
	private int sourceX;
	private int sourceY;
	
	/**
	 * Constructor links the controller to a view and a model.
	 * @param d BasicDisplay object that will act as the view for the controller.
	 * @param b Board object that will act as a model for the controller.
	 */
	public Controller(BasicDisplay d, Board b) {
		display = d;
		board = b;
		sourceX = -1;
		sourceY = -1;
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
				
				//make the move if legal
				if(flag) {
					board.makeMove(m);
					
					//after making the move, check if the king is checkmated
					int temp = board.gameState();
					
					//first update movelist
					display.displayMoves();
					
					display.displayBoard();
					
					//then check for endgame conditions
					if(temp == 1) display.endGame(PieceColor.White);
					else if(temp == -1) display.endGame(PieceColor.Black);
					else if(temp == 0) display.endGame(PieceColor.None);
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
