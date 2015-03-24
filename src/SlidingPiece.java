import java.util.ArrayList;


public class SlidingPiece extends Piece{

	ArrayList<RelativePosition> slidingDirections;
	
	public SlidingPiece(Position p, Side s, ArrayList<RelativePosition> slidingDirections) {
		super(p, s);
		this.slidingDirections = new ArrayList<RelativePosition>(slidingDirections);
	}

	@Override
	public ArrayList<Position> getPossibleMoves(Board b) {
		
			assert(getPosition().doesExistOnBoard());
			
			ArrayList<Position> output = new ArrayList<Position>();
			
			for(RelativePosition r : slidingDirections){
				output.addAll(b.getMovesAlongDirectionalAxisUntilInterrupted(this.getPosition(), r));
			}
			
			return output;
	}
	
	
}
