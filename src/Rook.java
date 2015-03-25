import java.util.ArrayList;


public class Rook extends SlidingPiece{
	private static ArrayList<RelativePosition> directions = new ArrayList<RelativePosition>(){{
		directions.add(new RelativePosition(1, 0));
		directions.add(new RelativePosition(-1, 0));
		directions.add(new RelativePosition(0, 1));
		directions.add(new RelativePosition(0, -1));
	}};
	
	public Rook(Position p, Side side){
		super(p, side, directions);
	}


	@Override
	public Piece copy() {
		return new Rook(getPosition(), getSide());
	}

	@Override
	public Piece factory(Position p, Side s) {
		return new Rook(p, s);
	}

}
