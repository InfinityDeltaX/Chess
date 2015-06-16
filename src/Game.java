import java.util.ArrayList;
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
		while(true) {eachSideMoves(theBoard);}
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

	private void eachSideMoves(Board b){ //get a move from the user, then from the computer. 
		b.makeMove(getUserMove(b));
		b.makeMove(getComputerMove(b));
	}

	public Move getComputerMove(Board b){
		if(!Values.lockDepth) getDepth();
		System.out.println("Calculating to " + lastDepth + "...");
		Move bestMove = negaMax(theBoard, lastDepth, Values.SIDE_COMPUTER, true, Integer.MIN_VALUE, Integer.MAX_VALUE).move;
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
			else if(input.contains("moverordering")){
				Values.MOVE_ORDERING = Boolean.valueOf(input.split(" ")[1]);
			}
			else if(input.contains("nps")){
				System.out.println("NPS: " + new Game(Side.BLACK).calculateNPSMinimax(6));
			}
			else if(input.contains("help")){
				System.out.println("Valid commands: lockdepth, maxtime, mintime, startingdepth, moveordering, setDepth");
			} 
			else if(input.contains("setdepth")){
				lastDepth = Integer.parseInt(input.split(" ")[1]);
			}
			else{changeSucessful = false;}

		} catch(Exception e){System.out.println("Error while setting paramter!"); changeSucessful = false;}
		System.out.println(changeSucessful ? "Done. Returning to game..." : "Could not find the correct parameter!");
		in.close();
	}

	public Move getUserMove(Board input){
		Scanner in = new Scanner(System.in);
		System.out.print("Current Board State: \n" + input.getFenString() + "\n" + input + "\n Please input your move:");
		String userMove = in.nextLine();
		if(userMove.contains("exit")){
			System.exit(0);
			return null;
		} else if(userMove.contains("admin")){
			adminConsole();
			return getUserMove(input);
		} else return (new Move(userMove, input));
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
	
	public MoveChoice negaMax(Board inputBoard, int remainingDepth, Side toMove){
		return negaMax(inputBoard, remainingDepth, toMove, true, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}
	
	private MoveChoice negaMax(Board inputBoard, int remainingDepth, Side toMove, boolean first, int alpha, int beta){
		Values.nodeCounter++;
		long startTime = System.currentTimeMillis();
		if(inputBoard.kingStatus() != 0 || remainingDepth == 0) return new MoveChoice(new Move(new Piece(null, null, null), (Position)null), toMove.multiplier*inputBoard.evaluate());
		ArrayList<Move> possibleNextMoves = inputBoard.getAllPossibleMoves(toMove);
		
		if(Values.MOVE_ORDERING && remainingDepth >= Values.DEPTH_NOT_TO_ORDER) Move.orderMoves(possibleNextMoves, inputBoard); //do move ordering.
		
		int counter = 0;
		Move currentBestMove = null;
		int currentBestScore = Integer.MIN_VALUE;
		
		for(Move currentMove : possibleNextMoves){
			Board changed = new Board(inputBoard);
			changed.makeMove(currentMove);
			
			int currentScore = -1*negaMax(changed, remainingDepth-1, Side.getOpposingSide(toMove), false, -1*beta, -1*alpha).score;
			//System.out.println("[" + remainingDepth + "] " + currentMove + " : " + currentScore);
			
			alpha = Math.max(alpha, currentScore);
			
			if(currentScore > currentBestScore){
				currentBestScore = currentScore;
				currentBestMove = currentMove;
			}
			
			if(alpha >= beta) break;
			
			counter++;
			if(first) System.out.printf("%d percent done. \r", (int) ((double) counter/possibleNextMoves.size()*100));
		}
		if(first) System.out.println("Score: " + currentBestScore);
		
		if(first) System.out.println("Done in " + (System.currentTimeMillis()-startTime) + " milliseconds!");
		if(first) System.out.println("Minimax result: " + currentBestMove + " with score: " + currentBestScore);
		lastSearch = ((System.currentTimeMillis()-startTime));
		return new MoveChoice(currentBestMove, currentBestScore);
	}
}

class MoveChoice{
	final Move move;
	final int score;
	
	MoveChoice(Move m, int score){
		this.move = m;
		this.score = score;
	}
}
