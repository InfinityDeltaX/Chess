import java.util.ArrayList;
import java.util.HashMap;


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
	
	Move minimax(){
		
	}
	
	int minNode(Board inputBoard, int remainingDepth){ //given a board state, determine the move 
		
		int currentLowest = 0;
		
		if(remainingDepth == 0){ //if we have no layers left to search, return the current board eval.
			return inputBoard.evaluate();
		}
		ArrayList<Move> possibleNextMoves = inputBoard.getAllPossibleMoves(Values.SIDE_BLACK); //else, get a list of possible next moves. Black is always trying to minimize. The maximizer uses Values.SIDE_WHITE here.
		HashMap<Move, Integer> scores= new HashMap<Move, Integer>(); //make a hashmap of all possible moves to their corresponding scores.
		
		for(Move currentMove : possibleNextMoves){ //loop through all moves
			Board moveApplied = new Board(inputBoard);//generate a board with the move applied
			moveApplied.makeMove(currentMove);
			int currentScore = maxNode(moveApplied, remainingDepth-1); //run max() on each board
			if(currentScore < currentLowest){
				currentScore = currentLowest; //return the minimum of the previous function calls.
			}
		}
		return currentLowest;
	}
	
	int maxNode(Board inputBoard, int remainingDepth){
		
	}
	
}
