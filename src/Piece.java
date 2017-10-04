import java.awt.Point;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Piece implements Comparable<Piece> {
	
	boolean hasBeenPromoted;
	Side side;
	Position myPosition;
	PieceType type;

	public Piece(PieceType t, Position p, Side side) {
		this.type = t;
		this.side = side;
		this.myPosition = p;
		hasBeenPromoted = false;
	}

	public Piece(Piece piece) {
		this(piece.getType(), piece.getPosition(), piece.getSide());
	}

	@Override
	public String toString() {
		return ((this.type == null ? "null" : this.type.name()) + " on position " + this.getPosition() + " [Side: " + this.getSide() + "]");
	}

	public char getTypeLetter() {
		return type.name().charAt(0);
	}

	public static Point getDifference(Position a, Position b) {
		return new Point(a.getFile() - b.getFile(), a.getRow() - b.getRow());
	}

	public int evaluateValue(Board b) { //take gamestate to avoid recalculating it every time.
		int pieceValue = type.value;
		int sideMultiplier = getSide().multiplier;
		int pieceSquareTableValue = Values.getPieceSquareValue(this, b.getGameState());

		int output = sideMultiplier * (pieceSquareTableValue + pieceValue);
		return output;
	}

	public static Piece getPieceFromLetter(char input, Position p) { //return a piece from an appropriately capitalized letter, such as those found in a fen string. White = capital, Black = lower case.
		Side s = Character.isUpperCase(input) ? Side.WHITE : Side.BLACK;
		PieceType type = null;
		for(PieceType t : PieceType.values()){
			if(t.toString().toLowerCase().startsWith((input + "").toLowerCase())){
				type = t;
				break;
			}
		}
		return new Piece(type, p, s);
	}

	public static Piece getRandomPiece(Position p, Side s) {
		return new Piece(PieceType.values()[new Random().nextInt(PieceType.values().length)], p, s);
	}

	public Set<Position> getPossibleMoves(Board b){
		if(this.type == PieceType.BISHOP || this.type == PieceType.QUEEN || this.type == PieceType.ROOK){
			return getPossibleSlidingMoves(b, this, this.type.getMoves());
		} else if(this.type == PieceType.KING || this.type == PieceType.NIGHT){
			return getPossibleJumpingMoves(b, this, this.type.getMoves());
		} else if(this.type == PieceType.PAWN){
			return getPossiblePawnMoves(b, this);
		} else {
			return null;
		}
	}


	public static Set<Position> getPossiblePawnMoves(Board b, Piece piece) {
		Position p = piece.getPosition();
		assert(p.doesExistOnBoard());
		//the eight ways to attack [circle]. start at top and go clock-wise.
		Set<Position> output = new HashSet<Position>();

		boolean isFirstMove = false; //figure out if it is our first move; if it is, we can move forwards 2.
		if((piece.getSide() == Side.BLACK && p.getRow() == Values.PAWN_ROW_BLACK) || (piece.getSide() == Side.WHITE && p.getRow() == Values.PAWN_ROW_WHITE)) isFirstMove = true; //we're on the original pawn file for our color. [Where all the pawns start]

		if(piece.getSide() == Side.WHITE){
			if(p.getPositionRelative(0, 2).doesExistOnBoard() && (b.isPositionEmpty(p.getPositionRelative(0, 1)) && b.isPositionEmpty(p.getPositionRelative(0, 2))) && isFirstMove){output.add(p.getPositionRelative(0, 2));} //there are two empty squares in front of us, and we're on the original file. We can move 2 forwards!
			if(p.getPositionRelative(0, 1).doesExistOnBoard() && b.isPositionEmpty(p.getPositionRelative(0, 1))){output.add(p.getPositionRelative(0, 1));} //See if we can move forwards; there cannot be a piece there for this to work!
			if(p.getPositionRelative(1, 1).doesExistOnBoard() && ((!b.isPositionEmpty(p.getPositionRelative(1, 1))) && b.getPieceAtPosition(p.getPositionRelative(1, 1)).getSide() == Side.getOpposingSide(piece.getSide()))){output.add(p.getPositionRelative(1, 1));} //see if we can move diagonally for a capture. They must have a piece there for this to work!
			if(p.getPositionRelative(-1, 1).doesExistOnBoard() && ((!b.isPositionEmpty(p.getPositionRelative(-1, 1))) && b.getPieceAtPosition(p.getPositionRelative(-1, 1)).getSide() == Side.getOpposingSide(piece.getSide()))){output.add(p.getPositionRelative(-1, 1));} //check diagonally the other way
		}

		if(piece.getSide() == Side.BLACK){
			if(p.getPositionRelative(0, -2).doesExistOnBoard() && (b.isPositionEmpty(p.getPositionRelative(0, -1)) && b.isPositionEmpty(p.getPositionRelative(0, -2))) && isFirstMove){output.add(p.getPositionRelative(0, -2));} //there are two empty squares in front of us, and we're on the original file. We can move 2 forwards!
			if(p.getPositionRelative(0, -1).doesExistOnBoard() && b.isPositionEmpty(p.getPositionRelative(0, -1))){output.add(p.getPositionRelative(0, -1));} //See if we can move forwards; there cannot be a piece there for this to work!
			if(p.getPositionRelative(1, -1).doesExistOnBoard() && ((!b.isPositionEmpty(p.getPositionRelative(1, -1))) && b.getPieceAtPosition(p.getPositionRelative(1, -1)).getSide() == Side.getOpposingSide(piece.getSide()))){output.add(p.getPositionRelative(1, -1));} //see if we can move diagonally for a capture. They must have a piece there for this to work!
			if(p.getPositionRelative(-1, -1).doesExistOnBoard() && ((!b.isPositionEmpty(p.getPositionRelative(-1, -1))) && b.getPieceAtPosition(p.getPositionRelative(-1, -1)).getSide() == Side.getOpposingSide(piece.getSide()))){output.add(p.getPositionRelative(-1, -1));} //check diagonally the other way
		}

		return output;
	}

	public static Set<Position> getPossibleSlidingMoves(Board b, Piece p, RelativePosition[] directions) {
		Set<Position> output = new HashSet<Position>();

		for (RelativePosition r : directions) {
			output.addAll(b.getMovesAlongDirectionalAxisUntilInterrupted(p.getPosition(), r));
		}

		return output;
	}

	public static Set<Position> getPossibleJumpingMoves(Board b, Piece piece, RelativePosition[] jumps) {
		Position p = piece.getPosition();
		Set<Position> output = new HashSet<Position>();
		Side side = piece.getSide();
		for (RelativePosition r : jumps) {
			if(b.isValidPlaceToMove(p.getPositionRelative(r.getX(), r.getY()), side)) {
				output.add(p.getPositionRelative(r.getX(), r.getY()));
			} //the coordinates are the destination position. TLDR: If it's a valid place to move, add it to the list of places to move.
		}
		return output;
	}

	@Override
	public int compareTo(Piece a) {
		return getPosition().compareTo(a.getPosition());
	}

	//setters and getters
	public int getFile() {
		return myPosition.getFile();
	}

	public void setFile(int file) {
		myPosition = new Position(file, myPosition.getRow());
	}

	public int getRow() {
		return myPosition.getRow();
	}

	public void setRow(int row) {
		myPosition = new Position(myPosition.getFile(), row);
	}

	public Side getSide() {
		return side;
	}

	public Position getPosition() {
		return myPosition;
	}

	public void setPosition(Position myPosition) {
		this.myPosition = myPosition;
	}
	
	public PieceType getType() {
		return type;
	}

	public void setType(PieceType type) {
		this.type = type;
	}
}