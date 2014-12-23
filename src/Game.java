import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;


public class Game {

	static Board mostRecentBoard;

	public static void main(String[] args){
		Scanner in = new Scanner(System.in);

		String input = in.nextLine();
		Board b = new Board();
		if(input.contains("new")){

			System.out.println("Randomly determined sides.");
			Random r = new Random();
			if(r.nextDouble() > 0.5){//computer is white
				Values.SIDE_COMPUTER = Values.SIDE_WHITE;
				Values.SIDE_USER = Values.SIDE_BLACK;
				System.out.println("User is side BLACK!");
			}	
			else{
				Values.SIDE_COMPUTER = Values.SIDE_BLACK;
				Values.SIDE_USER = Values.SIDE_WHITE;
				System.out.println("User is side WHITE!");
			}

			b.setToDefaultBoard();
		} else {
			b.setToFenString(input);
			System.out.println("Please input user's side: ");
			input = in.nextLine();
			if(input.contains("white")){
				Values.SIDE_COMPUTER = Values.SIDE_BLACK;
				Values.SIDE_USER = Values.SIDE_WHITE;
			} else {
				Values.SIDE_COMPUTER = Values.SIDE_BLACK;
				Values.SIDE_USER = Values.SIDE_WHITE;
			}
		}

		//game loop
		while(1==1){
			System.out.println("Current Board State: ");
			System.out.println(b.FENString(Values.SIDE_USER));
			System.out.println("Please input your move: [ex: e2e4]");
			String userMove = in.nextLine();
			b.makeMove(new Move(userMove, b));
			System.out.println("Calculating on board position...");
			Move bestMove = minimax(Values.SIDE_COMPUTER, 4, b);
			System.out.println("Made move: " + bestMove);
			b.makeMove(bestMove);


		}
	}

	private static int calculateNPS(int depthToTest){
		Board b = new Board();
		b.setToDefaultBoard();
		long startTime = System.currentTimeMillis();
		int nodesSearched = perft(b, Values.SIDE_WHITE, depthToTest); //change to minimax soon.
		long endTime = System.currentTimeMillis();
		System.out.println("Time taken (ms): " + (endTime - startTime));
		System.out.println("Nodes searched: " + nodesSearched);
		return (int) (nodesSearched/(endTime-startTime)*1000);
	}

	public static Move minimax(int side, int depthToSearch, Board inputBoard){ //given a board state, determine a best move. Basically a min/max node, except that it keeps trach of the corresponding moves > scores hashmap.
		Move currentBestMove = null;
		Move bestMove;
		int bestMoveScore = 0;
		long startTime = System.currentTimeMillis();

		if(side == Values.SIDE_BLACK){ //minimizer
			int currentLowest = Integer.MAX_VALUE;

			ArrayList<Move> possibleNextMoves = inputBoard.getAllPossibleMoves(Values.SIDE_BLACK); //else, get a list of possible next moves. Black is always trying to minimize. The maximizer uses Values.SIDE_WHITE here.
			//HashMap<Move, Integer> scores= new HashMap<Move, Integer>(); //make a hashmap of all possible moves to their corresponding scores.

			for(Move currentMove : possibleNextMoves){ //loop through all moves
				Board moveApplied = new Board(inputBoard);//generate a board with the move applied
				moveApplied.makeMove(currentMove);
				int currentScore = maxNode(moveApplied, depthToSearch-1); //run max() on each board
				if(currentScore < currentLowest){
					currentLowest = currentScore; //return the minimum of the previous function calls.
					currentBestMove = currentMove;
				}
			}
			bestMove = currentBestMove;
			bestMoveScore = currentLowest;

		} else if(side == Values.SIDE_WHITE){ //maximizer
			int currentHighest = Integer.MIN_VALUE;

			ArrayList<Move> possibleNextMoves = inputBoard.getAllPossibleMoves(Values.SIDE_WHITE); //else, get a list of possible next moves. White is always trying to maximize. The minimizer uses Values.SIDE_BLACK here.
			HashMap<Move, Integer> scores= new HashMap<Move, Integer>(); //make a hashmap of all possible moves to their corresponding scores.

			for(Move currentMove : possibleNextMoves){ //loop through all moves
				Board moveApplied = new Board(inputBoard);//generate a board with the move applied
				moveApplied.makeMove(currentMove);
				int currentScore = maxNode(moveApplied, depthToSearch-1); //run max() on each board
				if(currentScore > currentHighest){
					currentHighest = currentScore; //return the minimum of the previous function calls.
					currentBestMove = currentMove;
				}
			}
			bestMove = currentBestMove;
			bestMoveScore = currentHighest;
		} else {
			assert(false); //neither white nor black?
			bestMove = null;
		}
		System.out.println("Done in " + (System.currentTimeMillis()-startTime) + " milliseconds!");
		System.out.println("Minimax result: " + bestMove + "with score: " + bestMoveScore);
		return bestMove;
	}

	private static int minNode(Board inputBoard, int remainingDepth){ //given a board state, minimal value.

		//System.out.println("Running min...");

		int currentLowest = Integer.MAX_VALUE;

		if(remainingDepth == 0){ //if we have no layers left to search, return the current board eval.
			return inputBoard.evaluate();
		}

		ArrayList<Move> possibleNextMoves = inputBoard.getAllPossibleMoves(Values.SIDE_BLACK); //else, get a list of possible next moves. Black is always trying to minimize. The maximizer uses Values.SIDE_WHITE here.

		for(Move currentMove : possibleNextMoves){ //loop through all moves
			Board moveApplied = new Board(inputBoard);//generate a board with the move applied
			moveApplied.makeMove(currentMove);
			int currentScore = maxNode(moveApplied, remainingDepth-1); //run max() on each board
			//System.out.println("current score is " + currentScore);
			if(currentScore < currentLowest){
				currentLowest = currentScore; //return the minimum of the previous function calls.
			}
		}
		return currentLowest;
	}

	private static int perft(Board _input, int side, int depth){
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

	private static int maxNode(Board inputBoard, int remainingDepth){

		//System.out.println("Running max...");

		int currentHighest = Integer.MIN_VALUE;

		if(remainingDepth == 0){ //if we have no layers left to search, return the current board eval.
			return inputBoard.evaluate();
		}

		ArrayList<Move> possibleNextMoves = inputBoard.getAllPossibleMoves(Values.SIDE_WHITE); //else, get a list of possible next moves. White is always trying to maximize. The minimizer uses Values.SIDE_BLACK here.

		for(Move currentMove : possibleNextMoves){ //loop through all moves
			Board moveApplied = new Board(inputBoard);//generate a board with the move applied
			moveApplied.makeMove(currentMove);
			int currentScore = minNode(moveApplied, remainingDepth-1); //run max() on each board
			//System.out.println("current score is " + currentScore);
			if(currentScore > currentHighest){
				currentHighest = currentScore; //return the minimum of the previous function calls.
			}
		}
		return currentHighest;
	}

}
