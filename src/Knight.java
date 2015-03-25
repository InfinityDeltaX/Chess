import java.util.ArrayList;


public class Knight extends JumpingPiece{
	private static ArrayList<RelativePosition> jumpable = new ArrayList<RelativePosition>(){{
		jumpable.add(new RelativePosition(1, 2));
		jumpable.add(new RelativePosition(-1, 2));
		jumpable.add(new RelativePosition(1, -2));
		jumpable.add(new RelativePosition(-1, -2));
		jumpable.add(new RelativePosition(2, 1));
		jumpable.add(new RelativePosition(2, -1));
		jumpable.add(new RelativePosition(-2, 1));
		jumpable.add(new RelativePosition(-2, -1));
	}};
	
	public Knight(Position p, Side side){
		super(p, side, jumpable);
	}
	
	@Override
	public Piece copy() {
		return new Knight(getPosition(), getSide());
	}
	
	@Override
	public Piece factory(Position p, Side s) {
		return new Knight(p, s);
	}
}
