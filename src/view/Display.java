package view;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import pieces.King;
import pieces.NoPiece;
import pieces.Pawn;
import pieces.Piece;
import pieces.PieceColor;
import pieces.Square;
import board.Board;
import board.Move;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * GUI for the chess board.
 * @author marthaurion
 *
 */
public class Display implements ActionListener {
	private Board board;
	private JFrame frame;
	private JPanel panel;
	private JLabel movelist;
	private int sourceX;
	private int sourceY;
	private ChessButton sourceButton;
	
	/**
	 * Initialize the GUI with a new board.
	 */
	public Display() {
		board = new Board();
		sourceX = -1;
		sourceY = -1;
		sourceButton = null;
		movelist = new JLabel();
		board.newGame();
	}
	
	/**
	 * Get a ChessButton on the board.
	 * @param x The x-coordinate of the ChessButton.
	 * @param y The y-coordinate of the ChessButton.
	 * @return ChessButton at location (x, y).
	 */
	private ChessButton getButtonAt(int x, int y) {
		Component[] comps = panel.getComponents();
		ChessButton tempB;
		for(int i = 0; i < comps.length; i++) {
			tempB = (ChessButton)comps[i];
			if(tempB.getMyX() == x && tempB.getMyY() == y) return tempB;
		}
		return null;
	}
	
	/**
	 * Ends the game and disposes the GUI.
	 * @param c PieceColor indicating which player won.
	 */
	private void endGame(PieceColor c) {
		displayMoves();
		if(c == PieceColor.None) {
			JOptionPane.showMessageDialog(frame, "Game Over. It's a tie!");
		}
		else {
			JOptionPane.showMessageDialog(frame, "Game Over. "+c.toString()+" wins!");
		}
		frame.dispose();
	}
	
	/**
	 * Creates the GUI and displays it to the user.
	 */
	public void initialize(){
		//initialize the frame
		frame = new JFrame();
		frame.setPreferredSize(new Dimension(1000, 1000));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new FlowLayout());
		
		panel = displayBoard();
		JPanel p2 = new JPanel();
		p2.setLayout(new FlowLayout());
		p2.setPreferredSize(new Dimension(200, 600));
		displayMoves();
		p2.add(movelist);
		frame.add(panel);
		frame.add(p2);
		frame.pack();
		frame.setVisible(true);
	}
	
	/**
	 * Creates a JPanel for the chess board.
	 * @return JPanel that displays the chess board.
	 */
	private JPanel displayBoard() {
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(8, 8));
		p.setPreferredSize(new Dimension(600, 600));
		for(int i = 7; i >= 0; i--) {
			for(int j = 0; j < 8; j++) {
				ChessButton grid = new ChessButton(j, i);
				
				grid.setPiece(board.getPiece(j, i));
				grid.addActionListener(this);
				p.add(grid);
			}
		}
		
		return p;
	}
	
	/**
	 * Display move list on the Display.
	 */
	private void displayMoves() {
		ArrayList<String> moves = board.getMoves();
		if(moves.size() == 0) movelist.setText("Moves");
		else {
			StringBuilder str = new StringBuilder();
			
			//apparently JLabels use html
			str.append("<html><body>Moves<br><ol>");
			
			for(int i = 0; i < moves.size(); i++) {
				if(i%2 == 0) str.append("<li>");
				
				str.append(moves.get(i)+"&nbsp;");
				
				// line break for every other move
				if(i%2 == 1) str.append("</li>");
			}
			
			str.append("</ol></body></html>");
			movelist.setText(str.toString());
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		ChessButton source = (ChessButton)e.getSource();
		
		//if there is no source, set this to be the source
		if(sourceButton == null) {
			sourceX = source.getMyX();
			sourceY = source.getMyY();
			sourceButton = source;
			sourceButton.activate();
			
			highlightPossible();
		}
		//otherwise create the move and send to the model
		else {
			int x = source.getMyX();
			int y = source.getMyY();
			
			//if the same button is clicked, cancel the selection
			if(sourceX == x && sourceY == y) {
				sourceX = -1;
				sourceY = -1;
				clearBoard();
				sourceButton = null;
			}
			
			//otherwise try the move and make if it is legal
			else {
				Piece src = board.getPiece(sourceX, sourceY);
				Piece dest = board.getPiece(x, y);
				Move m = new Move(src, dest);
				
				//these variables are the castling check later
				//I know this is bad. I'll fix it later. This is just to get something working.
		    	int sx = m.getSource().getLocation().getX();
		    	int dx = m.getDest().getLocation().getX();
		    	int dy = m.getDest().getLocation().getY();
		    	PieceColor moved = m.getSource().getColor();
		    	String name_moved = m.getSource().getName();
		    	
		    	
				boolean flag = board.tryMove(m);
				//reset now that the move has been registered
				sourceX = -1;
				sourceY = -1;
				
				//make the move if legal
				if(flag) {
					boolean enPass = false;
					
					
					//win condition
					if(dest.getName().equals("King")) {
						//end game
						endGame(src.getColor());
						return;
					}
					
					//set the flag for en passant if a pawn is moved diagonally
					//and to an empty square
					if(src.getName().equals("Pawn")) {
						enPass = (sx != dx) && board.getPiece(dx, dy).getName().equals("None");
					}
					
					board.makeMove(m);
					
			    	//check if the move was a castle
			    	if(name_moved.equals("King")) processCastle(sx, dx, moved);
					
			    	//update the panel with the move
					sourceButton.setPiece(new NoPiece(x,y)); //empty old square
					source.setPiece(board.getPiece(dx, dy)); //set destination square to be source piece
					
					//if en passant was made, empty the captured piece's square
					if(enPass) {
						Pawn temp = (Pawn)(board.getPiece(dx, dy));
						ChessButton tempButton = getButtonAt(dx, dy-temp.advance());
						tempButton.setPiece(new NoPiece(dx, dy-temp.advance()));
						tempButton.repaint();
					}
					
					clearBoard();
					
					source.repaint();
					sourceButton = null; //clear the source button
					
					//after making the move, check if the king is checkmated
					int temp = board.gameState();
					
					//first update movelist
					displayMoves();
					
					//then check for endgame conditions
					if(temp == 1) endGame(PieceColor.White);
					else if(temp == -1) endGame(PieceColor.Black);
					else if(temp == 0) endGame(PieceColor.None);
				}
				//endif flag
				
				else {
					System.out.println("Invalid move"); //for debug
					clearBoard();
					sourceButton = null;
				}
			}
			//endelse
		}
		//endelse
	}
	
	
	/**
	 * Highlights all possible move squares for the clicked piece.
	 */
	private void highlightPossible() {
		if(sourceButton == null) return;
		
		//highlight all possible moves
		Piece temp = board.getPiece(sourceButton.getMyX(), sourceButton.getMyY());
		
		//only highlight if it's the turn color
		if(temp.getColor() == board.getTurn()) {
			ArrayList<Square> moves = temp.getLegalMoves();
			moves = temp.checkKing(moves);
			
			//add the castle squares if it's a king
			if(temp.getName().equals("King")) moves = ((King)temp).addCastle(moves);
			
			//loop through all possible moves and activate their buttons
			Square sq;
			for(int i = 0; i < moves.size(); i++) {
				sq = moves.get(i);
				getButtonAt(sq.getX(), sq.getY()).activatePossible();
			}
		}
	}
	
	/**
	 * Checks for a castling move.
	 * @param sx The x-coordinate of the source square of a move.
	 * @param dx The x-coordinate of the destination square of a move.
	 * @param moved PieceColor of the piece being moved.
	 */
	private void processCastle(int sx, int dx, PieceColor moved) {
		ChessButton tempSource = null;
		ChessButton tempDest = null;
		if(moved == PieceColor.White) { 
    		//if king moved positive x, then moved king side
    		if(dx - sx == 2) {
    			tempSource = getButtonAt(7, 0);
    			tempDest = getButtonAt(5, 0);
    		}
    		
    		else if(sx - dx == 2) {
    			tempSource = getButtonAt(0, 0);
    			tempDest = getButtonAt(3, 0);
    		}
		}
		
		if(moved == PieceColor.Black) { 
    		//if king moved positive x, then moved king side
    		if(dx - sx == 2) {
    			tempSource = getButtonAt(7, 7);
    			tempDest = getButtonAt(5, 7);
    		}
    		
    		else if(sx - dx == 2) {
    			tempSource = getButtonAt(0, 7);
    			tempDest = getButtonAt(3, 7);
    		}
		}
		
		//if we've populated tempSource and tempDest, then there is a castle
		if(tempSource != null && tempDest != null) {
			tempSource.setPiece(board.getPiece(tempSource.getMyX(), tempSource.getMyY()));
			tempDest.setPiece(board.getPiece(tempDest.getMyX(), tempDest.getMyY()));
			tempSource.repaint();
			tempDest.repaint();
		}
	}
	
	/**
	 * Remove borders on every square on the board.
	 * This might be more efficient than getting legal moves and deactivating them.
	 */
	private void clearBoard() {
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				getButtonAt(i, j).deactivate();
			}
		}
	}
}
