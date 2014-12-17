import java.util.ArrayList;
import java.util.HashMap;


public class Game {
	public static void main(String[] args){


		//Testing
		Position p = new Position(1, 0);
		Position moveTo = new Position(0, 3);
		Piece piece = new Piece(p, Values.SIDE_WHITE, Values.KNIGHT);
		Move m = new Move(piece, moveTo);
		Board b = new Board();
		b.setToDefaultBoard();
		b.setPositionToEmpty(p);
		//b.setPieceAtPosition(new Position(4, 4), new Piece(new Position(4, 4), Values.SIDE_WHITE, Values.KING_WHITE));
		
		System.out.println(b.FENString(Values.SIDE_BLACK));
		System.out.println(perft(b, Values.SIDE_BLACK, 4));
		System.out.println("Nodes per second: " + calculateNPS(3));
		System.out.println(minimax(Values.SIDE_WHITE, 4, b));
		
	}

	static int calculateNPS(int depthToTest){
		Board b = new Board();
		b.setToDefaultBoard();
		long startTime = System.currentTimeMillis();
		int nodesSearched = perft(b, Values.SIDE_WHITE, depthToTest); //change to minimax soon.
		long endTime = System.currentTimeMillis();
		System.out.println("Time taken (ms): " + (endTime - startTime));
		System.out.println("Nodes searched: " + nodesSearched);
		return (int) (nodesSearched/(endTime-startTime)*1000);
	}
	
	static Move minimax(int side, int depthToSearch, Board inputBoard){ //given a board state, determine a best move. Basically a min/max node, except that it keeps trach of the corresponding moves > scores hashmap.
		Move currentBestMove = null;

		if(side == Values.SIDE_BLACK){ //minimizer
			int currentLowest = Integer.MAX_VALUE;

			ArrayList<Move> possibleNextMoves = inputBoard.getAllPossibleMoves(Values.SIDE_BLACK); //else, get a list of possible next moves. Black is always trying to minimize. The maximizer uses Values.SIDE_WHITE here.
			HashMap<Move, Integer> scores= new HashMap<Move, Integer>(); //make a hashmap of all possible moves to their corresponding scores.

			for(Move currentMove : possibleNextMoves){ //loop through all moves
				Board moveApplied = new Board(inputBoard);//generate a board with the move applied
				moveApplied.makeMove(currentMove);
				int currentScore = maxNode(moveApplied, depthToSearch-1); //run max() on each board
				if(currentScore < currentLowest){
					currentScore = currentLowest; //return the minimum of the previous function calls.
					currentBestMove = currentMove;
				}
			}
			return currentBestMove;

		} else if(side == Values.SIDE_WHITE){ //maximizer
			int currentHighest = Integer.MIN_VALUE;

			ArrayList<Move> possibleNextMoves = inputBoard.getAllPossibleMoves(Values.SIDE_WHITE); //else, get a list of possible next moves. White is always trying to maximize. The minimizer uses Values.SIDE_BLACK here.
			HashMap<Move, Integer> scores= new HashMap<Move, Integer>(); //make a hashmap of all possible moves to their corresponding scores.

			for(Move currentMove : possibleNextMoves){ //loop through all moves
				Board moveApplied = new Board(inputBoard);//generate a board with the move applied
				moveApplied.makeMove(currentMove);
				int currentScore = maxNode(moveApplied, depthToSearch-1); //run max() on each board
				if(currentScore > currentHighest){
					currentScore = currentHighest; //return the minimum of the previous function calls.
					currentBestMove = currentMove;
				}
			}
			return currentBestMove;
		} else {
			assert(false); //neither white nor black?
			return null;
		}
	}

	static int minNode(Board inputBoard, int remainingDepth){ //given a board state, minimal value.
		
		System.out.println("Running min...");
		
		int currentLowest = Integer.MAX_VALUE;

		if(remainingDepth == 0){ //if we have no layers left to search, return the current board eval.
			return inputBoard.evaluate();
		}

		ArrayList<Move> possibleNextMoves = inputBoard.getAllPossibleMoves(Values.SIDE_BLACK); //else, get a list of possible next moves. Black is always trying to minimize. The maximizer uses Values.SIDE_WHITE here.

		for(Move currentMove : possibleNextMoves){ //loop through all moves
			Board moveApplied = new Board(inputBoard);//generate a board with the move applied
			moveApplied.makeMove(currentMove);
			int currentScore = maxNode(moveApplied, remainingDepth-1); //run max() on each board
			System.out.println("current score is " + currentScore);
			if(currentScore < currentLowest){
				currentScore = currentLowest; //return the minimum of the previous function calls.
			}
		}
		return currentLowest;
	}
	
	static int perft(Board _input, int side, int depth){
		Board input = new Board(_input);
		int total = 0;
		if(depth == 0){
			return 1;
		}
		else {
			for(Board b : input.getAllPossibleNextBoards(side)){
				total+= perft(b, Values.getOpposingSide(side), depth-1);
			}
		}
		return total;
	}
	
	static int maxNode(Board inputBoard, int remainingDepth){
		
		System.out.println("Running max...");
		
		int currentHighest = Integer.MIN_VALUE;

		if(remainingDepth == 0){ //if we have no layers left to search, return the current board eval.
			return inputBoard.evaluate();
		}

		ArrayList<Move> possibleNextMoves = inputBoard.getAllPossibleMoves(Values.SIDE_WHITE); //else, get a list of possible next moves. White is always trying to maximize. The minimizer uses Values.SIDE_BLACK here.

		for(Move currentMove : possibleNextMoves){ //loop through all moves
			Board moveApplied = new Board(inputBoard);//generate a board with the move applied
			moveApplied.makeMove(currentMove);
			int currentScore = minNode(moveApplied, remainingDepth-1); //run max() on each board
			System.out.println("current score is " + currentScore);
			if(currentScore > currentHighest){
				currentScore = currentHighest; //return the minimum of the previous function calls.
			}
		}
		return currentHighest;
	}

}
