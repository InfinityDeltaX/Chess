import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;


public class Game {
	public static void main(String[] args){
		
		
		//Testing
		Position p = new Position(3, 4);
		Position moveTo = new Position(5, 3);
		Piece piece = new Piece(p, Values.SIDE_BLACK, Values.BISHOP);
		//Move m = new Move(piece, moveTo);
		Board b = new Board();
		b.setToDefaultBoard();
		
		
		
		System.out.println(p.convertCoordsToNotation());
		
		b.setPieceAtPosition(p, Values.BISHOP_BLACK);
		
		//System.out.println("Real Pieces: " + b.getArrayListofMyRealPieces(2));
		
		System.out.println(b.getAllPossibleMoves(Values.SIDE_BLACK));
		
		//System.out.println(m.getNotation());
		
		System.out.println(Values.isFairPieceSquareTable(Values.PIECE_SQUARE_KING_END));
		System.out.println(Values.pieceSquareTableTotal(Values.PIECE_SQUARE_PAWN_START));
		
		System.out.println(b.getPossibleMoves(piece));
		System.out.println(b.evaluateMaterial());
		System.out.println("...");
		System.out.println("...");
	}
	
}
