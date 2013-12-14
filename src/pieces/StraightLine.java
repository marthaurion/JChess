package pieces;

import java.util.ArrayList;

import board.Board;

/**
 * Abstraction for all pieces that move in a straight line (rook, queen, bishop).
 * Their behaviors are fairly similar.
 * @author marthaurion
 *
 */
public abstract class StraightLine extends Piece {
	
	public StraightLine(int x, int y, PieceColor c, Board b) {
		super(x, y, c, b);
	}

	/**
	 * Adds all diagonals on the board for this piece to the input list.
	 * This is for bishop movements.
	 * @param list Input list to be edited with new moves.
	 * @return Updated list with new moves.
	 */
	protected ArrayList<Square> addDiagonal(ArrayList<Square> list) {
		boolean topright = true;
		boolean botright = true;
		boolean topleft = true;
		boolean botleft = true;
		
		int x = getLocation().getX();
		int y = getLocation().getY();
		
		
		for(int i = 1; i < 8; i++) {
			//check each corner
			if(topright) {
				//first check if gone off board
				if(!onBoard(x+i, y+i)) {
					topright = false;
				}
				//can add if square is empty
				else if(isEmpty(x+i, y+i)) {
					list.add(new Square(x+i, y+i));
				}
				//if the space is occupied, you can't advance any further no matter what
				else {	
					topright = false;
					//capture case 
					if(isEnemy(x+i, y+i)) {
						list.add(new Square(x+i, y+i));
					}
				}
			}
			//check bottom right corner
			if(botright) {
				if(!onBoard(x+i, y-i)) {
					botright = false;
				}
				else if(isEmpty(x+i, y-i)) {
					list.add(new Square(x+i, y-i));
				}
				else {
					botright = false;
					if(isEnemy(x+i, y-i)) {
						list.add(new Square(x+i, y-i));
					}
				}
			}
			//check top left
			if(topleft) {
				if(!onBoard(x-i, y+i)) {
					topleft = false;
				}
				else if(isEmpty(x-i, y+i)) {
					list.add(new Square(x-i, y+i));
				}
				else {
					topleft = false;
					if(isEnemy(x-i, y+i)) {
						list.add(new Square(x-i, y+i));
					}
				}
			}
			//check bottom left
			if(botleft) {
				if(!onBoard(x-i, y-i)) {
					botleft = false;
				}
				else if(isEmpty(x-i, y-i)) {
					list.add(new Square(x-i, y-i));
				}
				else {
					botleft = false;
					if(isEnemy(x-i, y-i)) {
						list.add(new Square(x-i, y-i));
					}
				}
			}
		}
		return list;
	}
	
	/**
	 * Adds all lines on the board for this piece to the input list.
	 * This is for rook movements.
	 * @param list Input list to be edited with new moves.
	 * @return Updated list with new moves.
	 */
	protected ArrayList<Square> addStraight(ArrayList<Square> list) {
		boolean top = true;
		boolean right = true;
		boolean left = true;
		boolean bot = true;
		
		int x = getLocation().getX();
		int y = getLocation().getY();
		
		for(int i = 1; i < 8; i++) {
			
			//check top
			if(top) {
				if(!onBoard(x, y+i)) {
					top = false;
				}
				else if(!isEmpty(x, y+i)) {
					top = false;
					if(isEnemy(x, y+i)) {
						list.add(new Square(x, y+i));
					}
				}
				else list.add(new Square(x, y+i));
			}
			//check bottom
			if(bot) {
				if(!onBoard(x, y-i)) {
					bot = false;
				}
				else if(!isEmpty(x, y-i)) {
					bot = false;
					if(isEnemy(x, y-i)) {
						list.add(new Square(x, y-i));
					}
				}
				else list.add(new Square(x, y-i));
			}
			//check left
			if(left) {
				if(!onBoard(x-i, y)) {
					left = false;
				}
				else if(!isEmpty(x-i, y)) {
					left = false;
					if(isEnemy(x-i, y)) {
						list.add(new Square(x-i, y));
					}
				}
				else list.add(new Square(x-i, y));
			}
			//check right
			if(right) {
				if(!onBoard(x+i, y)) {
					right = false;
				}
				else if(!isEmpty(x+i, y)) {
					right = false;
					if(isEnemy(x+i, y)) {
						list.add(new Square(x+i, y));
					}
				}
				else list.add(new Square(x+i, y));
			}
		}
		
		return list;
	}
}
