
public class Game {
	public static void main(String[] args){
		
		
		//Testing
		Position p = new Position(1, 1);
		Board b = new Board();
		b.setToDefaultBoard();
		
		//System.out.println(p.getOtherPositionsOnDiagonalRtoL());
		System.out.println(b.moveUnitUntilInterrupted(p, 1, 0));
		System.out.println("...");
		System.out.println("...");
	}
}
