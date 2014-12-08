
public class Move {

	Piece piece;
	Position toMoveTo;
	Position originalPosition;
	
	public Move(Piece p, Position originalPosition, Position toMoveTo){
		piece = p;
		this.toMoveTo = toMoveTo;
		this.originalPosition = originalPosition;
	}
	
	

	@Override
	public String toString() {
		return ("Move " + Piece.getTypeName(piece.type) + " at position " + originalPosition + " to " + toMoveTo);
	}



	public Piece getPiece() {
		return piece;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}

	public Position getToMoveTo() {
		return toMoveTo;
	}

	public void setToMoveTo(Position toMoveTo) {
		this.toMoveTo = toMoveTo;
	}
	
	
	
}
