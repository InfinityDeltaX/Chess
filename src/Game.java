import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;


public class Game {
	public static void main(String[] args){
		
		
		//Testing
		Position p = new Position(3, 1);
		Position moveTo = new Position(5, 3);
		Piece piece = new Piece(p, Values.SIDE_BLACK, Values.BISHOP);
		Move m = new Move(piece, moveTo);
		Board b = new Board();
		b.setToDefaultBoard();
		
		
		
		System.out.println(p.convertCoordsToNotation());
		
		b.setPieceAtPosition(p, Values.KING_WHITE);
		
		//System.out.println("Real Pieces: " + b.getArrayListofMyRealPieces(2));
		
		//System.out.println(b.getAllPossibleMoves(Values.SIDE_BLACK));
		
		System.out.println(m.getNotation());
		
		System.out.println(b.getPossibleKingMoves(p));
		System.out.println("...");
		System.out.println("...");
	}
}
