import javax.swing.JButton;


public class ChessButton extends JButton {
	private static final long serialVersionUID = 1L;
	private int myX;
	private int myY;
	
	public ChessButton(int x, int y) {
		myX = x;
		myY = y;
		setBorderPainted(true);
	}
	
	public void setPiece(Piece p) {
		String st = "";
		if(p.getName().equals("None")) st = " ";
		else if(p.getName().equals("Pawn")) st += p.getColor().toString().substring(0,1)+"P";
		else st += p.getColor().toString().substring(0,1)+p.getID();
		
		setText(st);
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