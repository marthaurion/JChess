package view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.Player;
import pieces.King;
import pieces.Piece;
import pieces.PieceColor;
import pieces.Square;
import board.Board;

/**
 * GUI for the chess board. This one is different in that it does no fancy checking.
 * It just displays the board blindly.
 * @author marthaurion
 *
 */
public class BasicDisplay {
	private Board board;
	private JFrame frame;
	private JPanel panel;
	private JLabel movelist;
	private Player player;
	
	/**
	 * Constructor for BasicDisplay takes in model as input and initializes a game
	 * @param b Board object representing the model for the display.
	 */
	public BasicDisplay(Board b, Player p) {
		board = b;
		movelist = new JLabel();
		board.newGame();
		player = p;
	}
	
	/**
	 * Ends the game and disposes the GUI.
	 * @param c PieceColor indicating which player won.
	 */
	public void endGame(PieceColor c) {
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
	 * Get a ChessButton on the board.
	 * @param x The x-coordinate of the ChessButton.
	 * @param y The y-coordinate of the ChessButton.
	 * @return ChessButton at location (x, y).
	 */
	public ChessButton getButtonAt(int x, int y) {
		Component[] comps = panel.getComponents();
		ChessButton tempB;
		for(int i = 0; i < comps.length; i++) {
			tempB = (ChessButton)comps[i];
			if(tempB.getMyX() == x && tempB.getMyY() == y) return tempB;
		}
		return null;
	}
	
	/**
	 * Creates the GUI and displays it to the user.
	 */
	public void initialize(boolean black) {
		if(frame != null) frame.dispose();
		//initialize the frame
		frame = new JFrame();
		frame.setPreferredSize(new Dimension(1000, 1000));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new FlowLayout());
		
		if(black) displayBlackBoard();
		else displayBoard();
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
	 */
	public void displayBoard() {
		if(panel == null) panel = new JPanel();
		panel.setLayout(new GridLayout(8, 8));
		panel.setPreferredSize(new Dimension(600, 600));
		panel.removeAll();
		for(int i = 7; i >= 0; i--) {
			for(int j = 0; j < 8; j++) {
				ChessButton grid = new ChessButton(j, i);
				
				grid.setPiece(board.getPiece(j, i));
				grid.addActionListener(player);
				panel.add(grid);
			}
		}
		panel.repaint();
	}
	
	/**
	 * Creates a JPanel for the chess board that isn't actionable yet.
	 */
	public void displayBlackBoard() {
		if(panel == null) panel = new JPanel();
		panel.setLayout(new GridLayout(8, 8));
		panel.setPreferredSize(new Dimension(600, 600));
		panel.removeAll();
		for(int i = 7; i >= 0; i--) {
			for(int j = 0; j < 8; j++) {
				ChessButton grid = new ChessButton(j, i);
				
				grid.setPiece(board.getPiece(j, i));
				panel.add(grid);
			}
		}
		panel.repaint();
	}
	
	
	/**
	 * Display move list on the Display.
	 */
	public void displayMoves() {
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
	
	
	/**
	 * Highlights all possible move squares for the input piece.
	 * @param x The x-coordinate of the input piece.
	 * @param y The y-coordinate of the input piece.
	 */
	public void highlightPossible(int x, int y) {
		//highlight all possible moves
		Piece temp = board.getPiece(x, y);
		
		//only highlight if it's the turn color
		if(temp.getColor() == board.getTurn()) {
			ArrayList<Square> moves = temp.getLegalMoves();
			moves = temp.checkKing(moves);
			
			//add the castle squares if it's a king
			if(temp.getName().equals("King")) moves = ((King)temp).addCastle(moves);
			
			//loop through all possible moves and activate their buttons
			Square sq;
			Piece temp2;
			for(int i = 0; i < moves.size(); i++) {
				sq = moves.get(i);
				
				//use a different color if enemy piece
				temp2 = board.getPiece(sq.getX(), sq.getY());
				if(!temp2.getName().equals("None") && temp2.getColor() != temp.getColor()) {
					getButtonAt(sq.getX(), sq.getY()).activateEnemy();
				}
				
				//use blue if empty square
				else getButtonAt(sq.getX(), sq.getY()).activatePossible();
			}
		}
	}
	
	/**
	 * Displays an error message when an incorrect move is made.
	 */
	public void invalid() {
		JOptionPane.showMessageDialog(frame, "Invalid move! Try a legal one this time.");
	}
	
	/**
	 * Remove borders on every square on the board.
	 * This might be more efficient than getting legal moves and deactivating them.
	 */
	public void clearBoard() {
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				getButtonAt(i, j).deactivate();
			}
		}
	}
}
