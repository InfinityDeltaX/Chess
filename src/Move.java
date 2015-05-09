import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Move {

	private final Piece piece;
	private final Position destination;
	private final Position origin;

	public Move(Piece p, Position destination) {
		piece = new Piece(p);
		this.destination = destination;
		this.origin = p.getPosition();
	}

	public Move(String notation, Board b) {
		//this constructor is only for reading in sequences of abstract moves.
		origin = new Position(notation.substring(0, 2));
		destination = new Position(notation.substring(2));
		piece = b.getPieceAtPosition(origin);

	}

	public Move(Move input) {
		this(input.getPiece(), input.getDestination());
	}

	public String getNotation() {
		return ("" + getOrigin() + getDestination());
	}

	public static boolean isCapture(Move m, Board b) {
		return !b.isPositionEmpty(m.getDestination()) && (m.getPiece().getSide() != Side.getOpposingSide(b.getPieceAtPosition(m.getDestination()).getSide()));
	}

	@Override
	public String toString() {
		if(piece != null) return ("Move " + piece.side + " " + piece.getClass().getName() + " at position " + origin + " to " + destination);
		else return "Piece is null....";
	}

	public Piece getPiece() {
		return getPiece();
	}

	public Position getDestination() {
		return destination;
	}

	public Position getOrigin() {
		return origin;
	}

	/*
	public Move getInverseMove() {
		Piece p = new Piece(piece);
		p.setPosition(getDestination());
		return new Move(p, getOrigin());
	}
	*/

	public static ArrayList<Move> orderMoves(ArrayList<Move> input, final Board board) {
		Collections.sort(input, new Comparator<Move>() {

			@Override
			public int compare(Move arg0, Move arg1) {
				return Values.booleanCompare(Move.isCapture(arg0, board), Move.isCapture(arg1, board));
			}
		});
		return input;
	}
}
