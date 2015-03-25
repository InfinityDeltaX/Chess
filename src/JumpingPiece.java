import java.util.ArrayList;


public abstract class JumpingPiece extends Piece{
	
	ArrayList<RelativePosition> jumpablePositions;
	
	public JumpingPiece(Position p, Side side, ArrayList<RelativePosition> jumpablePositions) {
		super(p, side);
		this.jumpablePositions = new ArrayList<RelativePosition>(jumpablePositions);
	}
	protected JumpingPiece(){};

	@Override
	public ArrayList<Position> getPossibleMoves(Board b) {
		assert(getPosition().doesExistOnBoard());
		
		ArrayList<Position> output = new ArrayList<Position>();
		Side side = getSide();
		for(RelativePosition r : jumpablePositions){
		if(b.isValidPlaceToMove(getPosition().getPositionRelative(r.getX(), r.getY()), side)){output.add(getPosition().getPositionRelative(r.getX(), r.getY()));} //the coordinates are the destination position. TLDR: If it's a valid place to move, add it to the list of places to move.
		}
		return output;
	}


}
