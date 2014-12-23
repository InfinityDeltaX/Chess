import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;


public class Game {
	
	static int lastDepth = Values.STARTING_DEPTH; //keep track of how far we searched, and how it took us, last time.
	static long lastSearch = Values.ACCEPTABLE_TIME_MIN; //how long did that depth take us?
	

	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		System.out.println(Values.ACCEPTABLE_TIME_MIN);
		String input = in.nextLine();
		Board b = new Board();
		b.setToFenString(input);
		System.out.println(b.evaluate());
		System.out.println(b);
		System.out.println("Evaluation Score: " + b.testEvalSpeed());
		System.out.println("New eval: " + b.evaluate());
		System.out.println("Old eval: " + b.fastEvaluateMaterial());
		//System.out.println(b.getAllPossibleMoves(Values.SIDE_WHITE).size());
		
		//System.out.println(b.getPossibleMoves(b.getPieceAtPosition(new Position("e2"))));
		
		System.out.println(b.getAllPossibleMoves(Values.SIDE_BLACK).size());
		
		for(Move m : b.getAllPossibleMoves(Values.SIDE_BLACK)){
			System.out.println(m.getToMoveTo());
		}
		
		setSides(Values.SIDE_BLACK);
		
		if(input.contains("play"))
			b.setToDefaultBoard();
		else {
			b.setToFenString(input);
			b.makeMove(getComputerMove(b));
		}
		
		
		
		while(1==1){
			eachSideMoves(b);
		}
	}
	
	private static void eachSideMoves(Board b){ //get a move from the user, then from the computer. 
		//get user's move
		b.makeMove(getUserMove(b));
		b.makeMove(getComputerMove(b));
		
	}
	
	
	
	public static Move getComputerMove(Board b){
		int depth = getDepth();
		System.out.println("Calculating to " + lastDepth + "...");
		Move bestMove = minimax(Values.SIDE_COMPUTER, depth, b);
		System.out.println("Made move: " + bestMove.getNotation());
		return bestMove;
	}
	
	private static Move getUserMove(Board input){
		Scanner in = new Scanner(System.in);
		System.out.print("Current Board State: ");
		System.out.println(input.FENString(Values.SIDE_USER));
		System.out.print("Please input your move: ");
		String userMove = in.nextLine();
		if(userMove.contains("exit")){
			in.close();
			System.exit(0);
		}
		
		return (new Move(userMove, input));
	}
	
	
	
	private static int getDepth(){
		if(lastSearch < Values.ACCEPTABLE_TIME_MIN){
			lastDepth++;
			System.out.println("Depth increased by one to " + lastDepth);
		}
		else if(lastSearch > Values.ACCEPTABLE_TIME_MAX){
			lastDepth--;
			System.out.println("Depth decreased by one to " + lastDepth);
		}
		return lastDepth;
	}

	private static void setSides(int computerside){
		if(computerside == Values.SIDE_WHITE){//computer is white
			Values.SIDE_COMPUTER = Values.SIDE_WHITE;
			Values.SIDE_USER = Values.SIDE_BLACK;
			System.out.println("User is side BLACK!");
		}	
		else{
			Values.SIDE_COMPUTER = Values.SIDE_BLACK;
			Values.SIDE_USER = Values.SIDE_WHITE;
			System.out.println("User is side WHITE!");
		}
	}
	
	private static int calculateNPS(int depthToTest){ //uses perft...
		Board b = new Board();
		b.setToDefaultBoard();
		long startTime = System.currentTimeMillis();
		int nodesSearched = perft(b, Values.SIDE_COMPUTER, depthToTest); //change to minimax soon.
		long endTime = System.currentTimeMillis();
		System.out.println("Time taken (ms): " + (endTime - startTime));
		System.out.println("Nodes searched: " + nodesSearched);
		return (int) (nodesSearched/(endTime-startTime)*1000);
	}
	
	private static void printTabs(int i){
		for(int j = 0; j < i; j++){
			System.out.print("  ");
		}
	}
	
	public static Move minimax(int side, int depthToSearch, Board inputBoard){ //given a board state, determine a best move. Basically a min/max node, except that it keeps trach of the corresponding moves > scores hashmap.
		Move currentBestMove = null;
		Move bestMove;
		int bestMoveScore = 0;
		long startTime = System.currentTimeMillis();
		int counter = 0;
		
		if(side == Values.SIDE_BLACK){ //minimizer
			int currentLowest = Integer.MAX_VALUE;

			ArrayList<Move> possibleNextMoves = inputBoard.getAllPossibleMoves(Values.SIDE_BLACK); //else, get a list of possible next moves. Black is always trying to minimize. The maximizer uses Values.SIDE_WHITE here.
			int topLevelBranches = possibleNextMoves.size();
			
			for(Move currentMove : possibleNextMoves){ //loop through all moves
				Board moveApplied = new Board(inputBoard);//generate a board with the move applied
				moveApplied.makeMove(currentMove);
				int currentScore = maxNode(moveApplied, depthToSearch-1); //run max() on each board
				counter++;
				System.out.printf("%d percent done. \n", (int) ((double) counter/topLevelBranches*100));
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
			int topLevelBranches = possibleNextMoves.size();
			
			for(Move currentMove : possibleNextMoves){ //loop through all moves
				Board moveApplied = new Board(inputBoard);//generate a board with the move applied
				moveApplied.makeMove(currentMove);
				int currentScore = maxNode(moveApplied, depthToSearch-1); //run max() on each board
				counter++;
				System.out.printf("%d percent done. \n", (int) ((double) counter/topLevelBranches*100));
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
		System.out.println("Minimax result: " + bestMove + " with score: " + bestMoveScore);
		lastSearch = ((System.currentTimeMillis()-startTime));
		return bestMove;
	}

	private static int minNode(Board inputBoard, int remainingDepth){ //given a board state, minimal value.

		//System.out.println("Running min...");
		
		Move currentBestMove = null;
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
				currentBestMove = currentMove;
			}
		}
		//printTabs(remainingDepth);
		//System.out.println("Best move found in this branch [Minimizer]: " + currentBestMove.getNotation() + "; Score: " + currentLowest);
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
		
		Move currentBestMove = null;
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
				currentBestMove = currentMove;
			}
		}
		//printTabs(remainingDepth);
		//System.out.println("Best move found in this branch [Maximizer]: " + currentBestMove.getNotation()  + "; Score: " + currentHighest);
		return currentHighest;
	}

}
