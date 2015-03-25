import java.util.ArrayList;


public class King extends JumpingPiece{
	private static ArrayList<RelativePosition> jumpable = new ArrayList<RelativePosition>(){{
		jumpable.add(new RelativePosition(1, 1));
		jumpable.add(new RelativePosition(1, 0));
		jumpable.add(new RelativePosition(1, -1));
		jumpable.add(new RelativePosition(0, 1));
		jumpable.add(new RelativePosition(0, -1));
		jumpable.add(new RelativePosition(-1, 1));
		jumpable.add(new RelativePosition(-1, 0));
		jumpable.add(new RelativePosition(1, -1));
	}};
	
	public King(Position p, Side side){
		super(p, side, jumpable);
	}

	@Override
	public Piece copy() {
		return new King(getPosition(), getSide());
	}
	
	@Override
	public Piece factory(Position p, Side s) {
		return new King(p, s);
	}
}
