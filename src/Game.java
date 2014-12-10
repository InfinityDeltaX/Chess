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
		
		//System.out.println(b.getAllPossibleMoves(Values.SIDE_BLACK));
		
		//System.out.println(m.getNotation());
		
		System.out.println(isFairPieceSquareTable(Values.PIECE_SQUARE_KING_END));
		
		System.out.println(b.getPossibleMoves(piece));
		System.out.println(b.evaluateMaterial());
		System.out.println("...");
		System.out.println("...");
	}
	
	int[][] getPieceSquareTables(int side, int type){ //given a side and a type, return the corresponding piece-square table. Flip if black, etc.
		
	}
	
	static boolean isFairPieceSquareTable(int[][] input){
		int count = 0;
		for(int i = 0; i < input.length; i++){
			for (int j = 0; j < input[0].length; j++) {
				count += input[i][j];
			}
		}
		return (count == 0);
	}
	
	static boolean allPieceSquareTablesFair(){
		if(isFairPieceSquareTable(Values.PIECE_SQUARE_KING_END) && //test all piece-square tables for fairness. 
			isFairPieceSquareTable(Values.PIECE_SQUARE_KING_END) &&
			isFairPieceSquareTable(Values.PIECE_SQUARE_KING_END) &&
			isFairPieceSquareTable(Values.PIECE_SQUARE_KING_END) &&
			isFairPieceSquareTable(Values.PIECE_SQUARE_KING_END) &&
			isFairPieceSquareTable(Values.PIECE_SQUARE_KING_END)) return true;
		else return false;
			
	}
	
}
