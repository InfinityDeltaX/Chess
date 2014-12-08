import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;


public class Game {
	public static void main(String[] args){
		
		
		//Testing
		Position p = new Position(3, 1);
		Board b = new Board();
		b.setToDefaultBoard();
		
		//System.out.println(p.getOtherPositionsOnDiagonalRtoL());
		ArrayList<Position> test = new ArrayList<Position>();
		test.add(p);
		ArrayList<Position> test2 = new ArrayList<Position>(test);
		test2.remove(p);
		System.out.println(test);
		System.out.println(test2);
		
		b.setPieceAtPosition(p, Values.KING_WHITE);
		
		System.out.println("Real Pieces: " + b.getArrayListofMyRealPieces(2));
		
		System.out.println(b.getAllPossibleMoves(Values.SIDE_BLACK));
		
		System.out.println(b.getPossibleKingMoves(p));
		System.out.println("...");
		System.out.println("...");
	}
}
