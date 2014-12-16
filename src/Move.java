
public class Move {

	Piece piece;
	Position toMoveTo;
	Position originalPosition;
	
	public Move(Piece p, Position toMoveTo){
		piece = new Piece(p);
		this.toMoveTo = new Position(toMoveTo);
		this.originalPosition = new Position(p.getPosition());
	}
	
	public Move(Move input) {
		this(input.getPiece(), input.getToMoveTo());
	}

	String getNotation(){
		return (getToMoveTo().convertCoordsToNotation() + getOriginalPosition().convertCoordsToNotation());
	}

	@Override
	public String toString() {
		return ("Move " + piece.getSideNames() + " " + Piece.getTypeName(piece.type) + " at position " + originalPosition + " to " + toMoveTo);
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
