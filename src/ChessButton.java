import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.LineBorder;


public class ChessButton extends JButton {
	private static final long serialVersionUID = 1L;
	private int myX;
	private int myY;
	
	public ChessButton(int x, int y) {
		myX = x;
		myY = y;
		setBorder(new LineBorder(Color.RED, 5));
		setBorderPainted(false);
		if((x+y)%2 == 1) setBackground(Color.GRAY);
		else setBackground(Color.WHITE);
	}
	
	public void setPiece(Piece p) {
		//if setting to an empty square, just remove icon and return
		if(p.getColor() == PieceColor.None) {
			setIcon(null);
			return;
		}
		
		
		//otherwise create the path to the image and display it
		String st = "images/";
		
		if(p.getColor() == PieceColor.White) st += "w";
		else if(p.getColor() == PieceColor.Black) st += "b";
		
		if(p.getName().equals("Pawn")) st += "p.png";
		else if(p.getName().equals("King")) st += "k.png";
		else if(p.getName().equals("Knight")) st += "n.png";
		else if(p.getName().equals("Bishop")) st += "b.png";
		else if(p.getName().equals("Queen")) st += "q.png";
		else if(p.getName().equals("Rook")) st += "r.png";
		else {
			System.out.println("Something went wrong.");
			System.exit(0);
		}
		
		setIcon(new ImageIcon(st));
			
	}
	
	//paints red if we're indicating the clicked piece
	public void activate() {
		setBorder(new LineBorder(Color.RED, 5));
		setBorderPainted(true);
		repaint();
	}
	
	//paints blue if we're indicating possible squares
	public void activatePossible() {
		setBorder(new LineBorder(Color.BLUE, 5));
		setBorderPainted(true);
		repaint();
	}
	
	public void deactivate() {
		setBorderPainted(false);
		repaint();
	}
	
	public int getMyX() {
		return myX;
	}
	
	public int getMyY() {
		return myY;
	}
}