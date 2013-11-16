import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Test {
	public static void main(String[] args) {
		Board board = new Board();
		board.newGame();
		
		Display disp = new Display(board);
		disp.initialize();
		
		//meh();
	}
	
	public static void meh() {
		ImageIcon icon = new ImageIcon("images/Black B.png", "Black Bishop");
		JFrame frame = new JFrame();
		frame.setPreferredSize(new Dimension(1000, 1000));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new FlowLayout());
		
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		panel.add(new JLabel(icon));
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
	}
}
