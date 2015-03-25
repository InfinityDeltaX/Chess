import java.util.ArrayList;


public class Queen extends SlidingPiece{
	private static ArrayList<RelativePosition> directions = new ArrayList<RelativePosition>(){{
		directions.add(new RelativePosition(1, 0));
		directions.add(new RelativePosition(-1, 0));
		directions.add(new RelativePosition(0, 1));
		directions.add(new RelativePosition(0, -1));
		directions.add(new RelativePosition(1, 1));
		directions.add(new RelativePosition(-1, 1));
		directions.add(new RelativePosition(-1, 1));
		directions.add(new RelativePosition(1, -1));
	}};
	
	public Queen(Position p, Side side){
		super(p, side, directions);
	}
	
	@Override
	public Piece copy() {
		return new Queen(getPosition(), getSide());
	}
	
	@Override
	public Piece factory(Position p, Side s) {
		return new Queen(p, s);
	}
}
