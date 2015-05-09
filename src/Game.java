import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;


public class Game {


	int lastDepth = Values.STARTING_DEPTH; //keep track of how far we searched, and how it took us, last time.
	long lastSearch = Values.ACCEPTABLE_TIME_MIN; //how long did that depth take us?
	Board theBoard;

	public void main(String[] args){
		Scanner in = new Scanner(System.in);		
		System.out.println(Values.ACCEPTABLE_TIME_MIN);
		String input = in.nextLine();
		Board b = new Board();
		//b.setToFenString(input);

		setSides(Side.BLACK);

		if(input.contains("play"))
			b.setToDefaultBoard();
		else {
			b = FenString.getBoard(input);
			b.makeMove(getComputerMove(b));
		}

		while(true){
			eachSideMoves(b);
		}
	}

	public void play(){
		while(true){
			eachSideMoves(theBoard);
		}
	}

	public void setBoard(Board b){
		theBoard = new Board(b);
	}

	public Board getBoard(){
		return new Board(theBoard);
	}

	public Game(Side computerSide){
		this(computerSide, null);
	}

	public Game(Side computerSide, Board board){
		setSides(computerSide);
		this.theBoard = board;

	}

	public void setup(){

	}

	private void eachSideMoves(Board b){ //get a move from the user, then from the computer. 
		//get user's move
		b.makeMove(getUserMove(b));
		b.makeMove(getComputerMove(b));

	}



	public Move getComputerMove(Board b){
		if(!Values.lockDepth)
			getDepth();
		System.out.println("Calculating to " + lastDepth + "...");
		Move bestMove = minimax(Values.SIDE_COMPUTER, lastDepth, b, true);
		System.out.println("Made move: " + bestMove.getNotation());
		return bestMove;
	}

	public void adminConsole(){
		boolean changeSucessful = true;
		Scanner in = new Scanner(System.in);
		System.out.println("Welcome to the admin console. Please enter a parameter to change, and a new value to set: ");
		String input = in.nextLine().toLowerCase();
		try{

			if(input.contains("lockdepth")){
				Values.lockDepth = Boolean.valueOf(input.split(" ")[1]);
			}
			else if(input.contains("maxtime")){
				Values.ACCEPTABLE_TIME_MAX = Integer.parseInt(input.split(" ")[1]);
			}
			else if(input.contains("mintime")){
				Values.ACCEPTABLE_TIME_MIN = Integer.parseInt(input.split(" ")[1]);
			}
			else if(input.contains("startingdepth")){
				Values.STARTING_DEPTH = Integer.parseInt(input.split(" ")[1]);
			}
			else if(input.contains("moverrdering")){
				Values.MOVE_ORDERING = Boolean.valueOf(input.split(" ")[1]);
			} else if(input.contains("nps")){
				System.out.println("NPS: " + new Game(Side.BLACK).calculateNPSMinimax(6));
			}
			else if(input.contains("help")){
				System.out.println("Valid commands: lockdepth, maxtime, mintime, startingdepth, moveordering, setDepth");
			} else if(input.contains("setdepth")){
				lastDepth = Integer.parseInt(input.split(" ")[1]);
			}
			
			else{changeSucessful = false;}

		} catch(Exception e){System.out.println("Error while setting paramter!"); changeSucessful = false;}
		System.out.println(changeSucessful ? "Done. Returning to game..." : "Could not find the correct parameter!");
		in.close();
	}

	public Move getUserMove(Board input){
		Scanner in = new Scanner(System.in);
		System.out.print("Current Board State: ");
		System.out.println(input.getFenString());
		System.out.println(input);
		System.out.print("Please input your move: ");
		String userMove = in.nextLine();
		if(userMove.contains("exit")){
			
			System.exit(0);
			return null;
		}
		else if(userMove.contains("admin")){
			adminConsole();
			return getUserMove(input);
		}

		else return (new Move(userMove, input));
	}



	private int getDepth(){
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

	public void setSides(Side computerside){
		if(computerside == Side.WHITE){//computer is white
			Values.SIDE_COMPUTER = Side.WHITE;
			Values.SIDE_USER = Side.BLACK;
			System.out.println("Computer is side WHITE!");
		}	
		else{
			Values.SIDE_COMPUTER = Side.BLACK;
			Values.SIDE_USER = Side.WHITE;
			System.out.println("Computer is side BLACK!");
		}
	}

	private int calculateNPS(int depthToTest){ //uses perft...
		Board b = new Board();
		b.setToDefaultBoard();
		long startTime = System.currentTimeMillis();
		int nodesSearched = perft(b, Values.SIDE_COMPUTER, depthToTest); //change to minimax soon.
		long endTime = System.currentTimeMillis();
		System.out.println("Time taken (ms): " + (endTime - startTime));
		System.out.println("Nodes searched: " + nodesSearched);
		return (int) (nodesSearched/(endTime-startTime)*1000);
	}
	
	private int calculateNPSMinimax(int depthToTest){
		Board b = new Board();
		b.setToDefaultBoard();
		Values.nodeCounter = 0;
		long startTime = System.currentTimeMillis();
		getComputerMove(b);//change to minimax soon.
		long endTime = System.currentTimeMillis();
		System.out.println("Time taken (ms): " + (endTime - startTime));
		System.out.println("Nodes searched: " + Values.nodeCounter);
		return (int) (Values.nodeCounter/(endTime-startTime)*1000);
	}

	private void printTabs(int i){
		for(int j = 0; j < i; j++){
			System.out.print("  ");
		}
	}

	public Move minimax(Side side, int depthToSearch, Board inputBoard, boolean shouldPrint){ //given a board state, determine a best move. Basically a min/max node, except that it keeps track of the corresponding moves > scores hashmap.
		Move currentBestMove = null;
		Move bestMove;
		int bestMoveScore = 0;
		long startTime = System.currentTimeMillis();
		int counter = 0;

		if(side == Side.BLACK){ //minimizer
			int currentLowest = Integer.MAX_VALUE;

			ArrayList<Move> possibleNextMoves = inputBoard.getAllPossibleMoves(Side.BLACK); //else, get a list of possible next moves. Black is always trying to minimize. The maximizer uses Side.WHITE here.
			int topLevelBranches = possibleNextMoves.size();

			for(Move currentMove : possibleNextMoves){ //loop through all moves
				Board moveApplied = new Board(inputBoard);//generate a board with the move applied
				moveApplied.makeMove(currentMove);
				int currentScore = maxNode(moveApplied, depthToSearch-1, Integer.MIN_VALUE, Integer.MAX_VALUE); //run max() on each board... Changed this line from max to min. Can't tell if that was a really dumb bug, or what.
				counter++;
				System.out.println(currentMove + " : " + currentScore);
				if(shouldPrint) System.out.printf("%d percent done. \r", (int) ((double) counter/topLevelBranches*100));
				if(currentScore < currentLowest){
					currentLowest = currentScore; //return the minimum of the previous function calls.
					currentBestMove = currentMove;
				}
			}
			bestMove = currentBestMove;
			bestMoveScore = currentLowest;

		} else if(side == Side.WHITE){ //maximizer
			int currentHighest = Integer.MIN_VALUE;

			ArrayList<Move> possibleNextMoves = inputBoard.getAllPossibleMoves(Side.WHITE); //else, get a list of possible next moves. White is always trying to maximize. The minimizer uses Side.BLACK here.
			int topLevelBranches = possibleNextMoves.size();

			for(Move currentMove : possibleNextMoves){ //loop through all moves
				Board moveApplied = new Board(inputBoard);//generate a board with the move applied
				moveApplied.makeMove(currentMove);
				int currentScore = minNode(moveApplied, depthToSearch-1, Integer.MIN_VALUE, Integer.MAX_VALUE); //run max() on each board
				counter++;
				System.out.println(currentMove + " : " + currentScore);
				if(shouldPrint) System.out.printf("%d percent done. \r", (int) ((double) counter/topLevelBranches*100));
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

		if(shouldPrint) System.out.println("Done in " + (System.currentTimeMillis()-startTime) + " milliseconds!");
		if(shouldPrint) System.out.println("Minimax result: " + bestMove + " with score: " + bestMoveScore);
		lastSearch = ((System.currentTimeMillis()-startTime));
		return bestMove;
	}

	private int minNode(Board inputBoard, int remainingDepth, int alpha, int beta){ //given a board state, minimal value.
		Values.nodeCounter++;
		//System.out.println("Running min...");

		if(inputBoard.kingStatus() != 0 || remainingDepth == 0){
			return inputBoard.evaluate();
		}

		ArrayList<Move> possibleNextMoves = inputBoard.getAllPossibleMoves(Side.BLACK); //else, get a list of possible next moves. Black is always trying to minimize. The maximizer uses Side.WHITE here.

		//move ordering.
		if(Values.MOVE_ORDERING && remainingDepth > Values.DEPTH_NOT_TO_ORDER){
			Move.orderMoves(possibleNextMoves, inputBoard);
		}

		for(Move currentMove : possibleNextMoves){ //loop through all moves

			if(alpha >= beta){
				break;
			}
			
			Piece capped = inputBoard.getPieceAtPosition(currentMove.getDestination());
			if(capped != null) capped = capped.copy();
			inputBoard.makeMove(currentMove);
			int currentScore = maxNode(inputBoard, remainingDepth-1, alpha, beta); //run max() on each board
			inputBoard.makeMove(currentMove.getInverseMove());
			if(capped != null) inputBoard.setPieceAtPosition(currentMove.getDestination(), capped.copy());
			
			System.out.println(currentMove + " : " + currentScore);
			if(currentScore < beta){
				beta = currentScore;
			}
		}
		//printTabs(remainingDepth);
		//System.out.println("Best move found in this branch [Minimizer]: " + currentBestMove.getNotation() + "; Score: " + currentLowest);
		return beta;
	}

	private int maxNode(Board inputBoard, int remainingDepth, int alpha, int beta){
		//System.out.println("Running max...");
		Values.nodeCounter++;

		if(inputBoard.kingStatus() != 0 || remainingDepth == 0){
			return inputBoard.evaluate();
		}

		ArrayList<Move> possibleNextMoves = inputBoard.getAllPossibleMoves(Side.WHITE); //else, get a list of possible next moves. White is always trying to maximize. The minimizer uses Side.BLACK here.

		//move ordering.
		if(Values.MOVE_ORDERING && remainingDepth >= Values.DEPTH_NOT_TO_ORDER){
			Move.orderMoves(possibleNextMoves, inputBoard);
		}

		for(Move currentMove : possibleNextMoves){ //loop through all moves

			if(alpha >= beta){
				break;
			}

			//Board moveApplied = new Board(inputBoard);//generate a board with the move applied
			Piece capped = inputBoard.getPieceAtPosition(currentMove.getDestination());
			if(capped != null) capped = capped.copy();
			inputBoard.makeMove(currentMove);
			int currentScore = minNode(inputBoard, remainingDepth-1, alpha, beta); //run max() on each board
			inputBoard.makeMove(currentMove.getInverseMove());
			if(capped != null) inputBoard.setPieceAtPosition(currentMove.getDestination(), capped.copy());
			
			System.out.println(currentMove + " : " + currentScore);
			if(currentScore > alpha){
				alpha = currentScore;
			}
		}
		//printTabs(remainingDepth);
		//System.out.println("Best move found in this branch [Maximizer]: " + currentBestMove.getNotation()  + "; Score: " + currentHighest);
		return alpha;
	}
	
	private int perft(Board _input, Side side, int depth){
		Board input = new Board(_input);
		int total = 0;
		if(depth == 0){
			return 1;
		}
		else {
			for(Board b : input.getAllPossibleNextBoards(side)){
				total+= perft(b, Side.getOpposingSide(side), depth-1);
			}
		}
		return total;
	}

}
