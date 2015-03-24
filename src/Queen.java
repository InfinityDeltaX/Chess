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
}
