
public class Move {

	Piece piece;
	Position toMoveTo;
	
	public Move(Piece p, Position toMoveTo){
		piece = p;
		this.toMoveTo = toMoveTo;
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
