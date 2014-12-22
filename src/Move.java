
public class Move {

	Piece piece;
	Position toMoveTo;
	Position originalPosition;
	
	public Move(Piece p, Position toMoveTo){
		piece = new Piece(p);
		this.toMoveTo = new Position(toMoveTo);
		this.originalPosition = new Position(p.getPosition());
	}
	
	public Move(String notation, Board b){
		//this constructor is only for reading in sequences of abstract moves.
		originalPosition = new Position(notation.substring(0, 2));
		toMoveTo = new Position(notation.substring(2));
		piece = b.getPieceAtPosition(originalPosition);
	}
	
	public Move(Move input) {
		this(input.getPiece(), input.getToMoveTo());
	}

	public String getNotation(){
		return ("" + getToMoveTo() + getOriginalPosition());
	}

	@Override
	public String toString() {
		if(piece!=null)
			return ("Move " + piece.getSideNames() + " " + Piece.getTypeName(piece.type) + " at position " + originalPosition + " to " + toMoveTo);
		else return "Piece is null....";
	}

	public Piece getPiece() {
		return new Piece(piece);
	}

	public void setPiece(Piece piece) {
		this.piece = new Piece(piece);
	}

	public Position getToMoveTo() {
		return new Position(toMoveTo);
	}

	public void setToMoveTo(Position toMoveTo) {
		this.toMoveTo = toMoveTo;
	}

	public Position getOriginalPosition() {
		return new Position(originalPosition);
	}

	public void setOriginalPosition(Position originalPosition) {
		this.originalPosition = originalPosition;
	}
	
	
	
}
