import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;


public class ChessButton extends JButton {
	private static final long serialVersionUID = 1L;
	private int myX;
	private int myY;
	
	public ChessButton(int x, int y) {
		myX = x;
		myY = y;
		setBorderPainted(true);
		if((x+y)%2 == 1) setBackground(Color.BLACK);
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
		
		if(p.getColor() == PieceColor.White) st += "White ";
		else if(p.getColor() == PieceColor.Black) st += "Black ";
		
		if(p.getName().equals("Pawn")) st += "P.png";
		else if(p.getName().equals("King")) st += "K.png";
		else if(p.getName().equals("Knight")) st += "N.png";
		else if(p.getName().equals("Bishop")) st += "B.png";
		else if(p.getName().equals("Queen")) st += "Q.png";
		else if(p.getName().equals("Rook")) st += "R.png";
		else {
			System.out.println("Something went wrong.");
			System.exit(0);
		}
		
		setIcon(new ImageIcon(st));
			
	}
	
	public void activate() {
		setBorderPainted(false);
		repaint();
	}
	
	public void deactivate() {
		setBorderPainted(true);
		repaint();
	}
	
	public int getMyX() {
		return myX;
	}
	
	public int getMyY() {
		return myY;
	}
}