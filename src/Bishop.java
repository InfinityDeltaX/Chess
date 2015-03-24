import java.util.ArrayList;


public class Bishop extends SlidingPiece{
	private static ArrayList<RelativePosition> directions = new ArrayList<RelativePosition>(){{
		directions.add(new RelativePosition(1, 1));
		directions.add(new RelativePosition(-1, 1));
		directions.add(new RelativePosition(-1, 1));
		directions.add(new RelativePosition(1, -1));
	}};
	
	public Bishop(Position p, Side side){
		super(p, side, directions);
	}
}
