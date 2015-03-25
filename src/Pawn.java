import java.util.ArrayList;


public class Pawn extends Piece{

	public Pawn(Position p, Side side) {
		super(p, side);
	}

	@Override
	public ArrayList<Position> getPossibleMoves(Board b) {
		Position p = getPosition();
		assert(p.doesExistOnBoard());
		//the eight ways to attack [circle]. start at top and go clock-wise.
		ArrayList<Position> output = new ArrayList<Position>();

		boolean isFirstMove = false; //figure out if it is our first move; if it is, we can move forwards 2.
		if((side == Side.BLACK && p.getRow() == Values.PAWN_ROW_BLACK) || (side == Side.WHITE && p.getRow() == Values.PAWN_ROW_WHITE))isFirstMove = true; //we're on the original pawn file for our color. [Where all the pawns start]

		if(side == Side.WHITE){
			if(p.getPositionRelative(0, 2).doesExistOnBoard() && (b.isPositionEmpty(p.getPositionRelative(0, 1)) && b.isPositionEmpty(p.getPositionRelative(0, 2))) && isFirstMove){output.add(p.getPositionRelative(0, 2));} //there are two empty squares in front of us, and we're on the original file. We can move 2 forwards!
			if(p.getPositionRelative(0, 1).doesExistOnBoard() && b.isPositionEmpty(p.getPositionRelative(0, 1))){output.add(p.getPositionRelative(0, 1));} //See if we can move forwards; there cannot be a piece there for this to work!
			if(p.getPositionRelative(1, 1).doesExistOnBoard() && (b.getPieceAtPosition(p.getPositionRelative(1, 1)).getSide() == Values.getOpposingSide(side))){output.add(p.getPositionRelative(1, 1));} //see if we can move diagonally for a capture. They must have a piece there for this to work!
			if(p.getPositionRelative(-1, 1).doesExistOnBoard() && (b.getPieceAtPosition(p.getPositionRelative(-1, 1)).getSide() == Values.getOpposingSide(side))){output.add(p.getPositionRelative(-1, 1));} //check diagonally the other way
		}

		if(side == Side.BLACK){
			if(p.getPositionRelative(0, -2).doesExistOnBoard() && (b.isPositionEmpty(p.getPositionRelative(0, -1)) && b.isPositionEmpty(p.getPositionRelative(0, -2))) && isFirstMove){output.add(p.getPositionRelative(0, -2));} //there are two empty squares in front of us, and we're on the original file. We can move 2 forwards!
			if(p.getPositionRelative(0, -1).doesExistOnBoard() && b.isPositionEmpty(p.getPositionRelative(0, -1))){output.add(p.getPositionRelative(0, -1));} //See if we can move forwards; there cannot be a piece there for this to work!
			if(p.getPositionRelative(1, -1).doesExistOnBoard() && (b.getPieceAtPosition(p.getPositionRelative(1, -1)).getSide() == Values.getOpposingSide(side))){output.add(p.getPositionRelative(1, -1));} //see if we can move diagonally for a capture. They must have a piece there for this to work!
			if(p.getPositionRelative(-1, -1).doesExistOnBoard() && (b.getPieceAtPosition(p.getPositionRelative(-1, -1)).getSide() == Values.getOpposingSide(side))){output.add(p.getPositionRelative(-1, -1));} //check diagonally the other way
		}

		return output;
	}

	@Override
	public Piece copy() {
		return new Pawn(getPosition(), getSide());
	}
	
	@Override
	public Piece factory(Position p, Side s) {
		return new Pawn(p, s);
	}

}
