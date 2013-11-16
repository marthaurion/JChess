import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class Display implements ActionListener {
	private Board board;
	private JFrame frame;
	private JPanel panel;
	private JLabel movelist;
	private int sourceX;
	private int sourceY;
	private ChessButton sourceButton;
	
	public Display(Board b) {
		board = b;
		sourceX = -1;
		sourceY = -1;
		sourceButton = null;
		movelist = new JLabel();
	}
	
	public void endGame(PieceColor c) {
		if(c == PieceColor.None) {
			JOptionPane.showMessageDialog(frame, "Game Over. It's a tie!");
		}
		else {
			JOptionPane.showMessageDialog(frame, "Game Over. "+c.toString()+" wins!");
		}
		
		frame.dispose();
	}


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
	
	public JPanel displayBoard() {
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
	
	//sets the text of the movelist label with the appropriate moves
	public void displayMoves() {
		ArrayList<String> moves = board.getMoves();
		if(moves.size() == 0) {
			movelist.setText("Moves");
		}
		else {
			StringBuilder str = new StringBuilder();
			//apparently JLabels use html
			str.append("<html><body>Moves<br><ol>");
			for(int i = 0; i < moves.size(); i++) {
				if(i%2 == 0) {
					str.append("<li>");
				}
				
				str.append(moves.get(i)+"&nbsp;");
				
				// line break for every other move
				if(i%2 == 1) {
					str.append("</li>");
				}
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
		}
		//otherwise create the move and send to the model
		else {
			int x = source.getMyX();
			int y = source.getMyY();
			
			//if the same button is clicked, cancel the selection
			if(sourceX == x && sourceY == y) {
				sourceX = -1;
				sourceY = -1;
				sourceButton.deactivate();
				sourceButton = null;
			}
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
					
					//win condition
					if(dest.getName().equals("King")) {
						//end game
						endGame(src.getColor());
						return;
					}
					else board.makeMove(m);
					
					//update movelist
					displayMoves();
					
					sourceButton.setPiece(new NoPiece(x,y)); //empty old square
					sourceButton.deactivate();
					source.setPiece(src); //set destination square to be source piece
					source.repaint();
					sourceButton = null; //clear the source button
					
					//after making the move, check if the king is checkmated
					int temp = board.gameState();
					if(temp == 1) endGame(PieceColor.White);
					else if(temp == -1) endGame(PieceColor.Black);
					else if(temp == 0) endGame(PieceColor.None);
				}
				//endif flag
				
				else {
					System.out.println("Invalid move"); //for debug
					sourceButton.deactivate();
					sourceButton = null;
				}
			}
			//endelse
		}
		//endelse
	}
	
}