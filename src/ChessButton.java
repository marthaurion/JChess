import javax.swing.JButton;


public class ChessButton extends JButton {
	private static final long serialVersionUID = 1L;
	private int myX;
	private int myY;
	
	public ChessButton(int x, int y) {
		myX = x;
		myY = y;
	}
	
	public ChessButton(String s) {
		myX = -1;
		myY = -1;
		setText(s);
	}
	
	public int getMyX() {
		return myX;
	}
	
	public int getMyY() {
		return myY;
	}
}