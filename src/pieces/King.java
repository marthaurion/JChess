package pieces;

import java.util.ArrayList;

import board.Board;
import board.Move;


public class King extends Piece {
	
	public King(int x, int y, PieceColor c, Board b) {
		super(x, y, c, b);
	}
	
	public Square getLocation() {
		return location;
	}
	
	public void setLocation(Square s) {
		location = s;
	}
	
	public String getNotation() {
		return location.getNotation();
	}
	
    public int getValue() {
    	return 0;
    }
    
    public String getName() {
    	return "King";
    }
    
    public char getID() {
    	return 'K';
    }
    
    public PieceColor getColor() {
    	return color;
    }
    
    // king overrides move so it can add castling as possible moves
    // without adding to threat list
    public boolean move(Move m) {
		//first do basic checks
		if(!checkPiece(m)) return false;
		
		ArrayList<Square> list = getLegalMoves();
		
		list = addCastle(list);
		
		list = checkKing(list);
		
		return checkList(m, list);
    }

	public ArrayList<Square> getLegalMoves() {
		//similar to the knight because there are only 8 possible moves
		ArrayList<Square> list = new ArrayList<Square>();
		
		int x = location.getX();
		int y = location.getY();
		
		//add surrounding squares
		list.add(new Square(x+1, y+1));
		list.add(new Square(x+1, y));
		list.add(new Square(x+1, y-1));
		
		list.add(new Square(x, y+1));
		list.add(new Square(x, y-1));
		
		list.add(new Square(x-1, y+1));
		list.add(new Square(x-1, y));
		list.add(new Square(x-1, y-1));
		
		
		//remove the moves that aren't legal
		for(int i = 0; i < list.size(); i++) {
			x = list.get(i).getX();
			y = list.get(i).getY();
			
			//remove moves that aren't on the board
			if(!onBoard(x, y)) {
				list.remove(i);
				i--;
			}
			//remove moves that land on squares with an ally
			else if(!isEmpty(x, y) && !isEnemy(x, y)) {
				list.remove(i);
				i--;
			}
		}
		
		return list;
	}
	
	/**
	 * Add castling squares as valid move squares for the King.
	 * @param list Input list to add the moves.
	 * @return List with new moves added.
	 */
	public ArrayList<Square> addCastle(ArrayList<Square> list) {
		if(color == PieceColor.White) return addWhiteCastle(list);
		else if(color == PieceColor.Black) return addBlackCastle(list);
		else return list;
	}
    
	private ArrayList<Square> addWhiteCastle(ArrayList<Square> list) {
		//method stub
		boolean castleKing = board.canCastle(PieceColor.White, true);
		boolean castleQueen = board.canCastle(PieceColor.White, false);
		if(!castleKing && !castleQueen) return list;
		if(board.isCheck(PieceColor.White)) return list; //don't let the king castle if in check
		
		int[][] atkMap = board.generateAttackMaps(PieceColor.White);
		
		//first check king's side
		boolean flag = true;
		
		//check whether the squares between king and rook are empty
		if(castleKing) {
			if(!isEmpty(5,0) | !isEmpty(6,0)) flag = false;
			if(atkMap[0][5] > 0 || atkMap[0][6] > 0) flag = false;
			if(flag) list.add(new Square(6,0));
			flag = true;
		}
		
		//now check queen's side
		if(castleQueen) {
			if(!isEmpty(1,0) | !isEmpty(2,0) | !isEmpty(3,0)) flag = false;
			if(atkMap[0][1] > 0 || atkMap[0][2] > 0 || atkMap[0][3] > 0) flag = false;
			if(flag) list.add(new Square(2,0));
		}
		return list;
	}
	
	private ArrayList<Square> addBlackCastle(ArrayList<Square> list) {
		boolean castleKing = board.canCastle(PieceColor.Black, true);
		boolean castleQueen = board.canCastle(PieceColor.Black, false);
		if(!castleKing && !castleQueen) return list;
		if(board.isCheck(PieceColor.Black)) return list; //don't let the king castle if in check
		
		int[][] atkMap = board.generateAttackMaps(PieceColor.Black);
		
		//first check king's side
		boolean flag = true;
		
		if(castleKing) {
			//check whether the squares between king and rook are empty
			if(!isEmpty(5,7) | !isEmpty(6,7)) flag = false;
			if(atkMap[7][5] > 0 || atkMap[7][6] > 0) flag = false;
			if(flag) list.add(new Square(6,7));
			
			flag = true;
		}
		
		//now check queen's side
		
		if(castleQueen) {
			if(!isEmpty(1,7) | !isEmpty(2,7) | !isEmpty(3,7)) flag = false;
			if(atkMap[7][1] > 0 || atkMap[7][2] > 0 || atkMap[7][3] > 0) flag = false;
			if(flag) list.add(new Square(2,7));
		}
		return list;
	}
}
