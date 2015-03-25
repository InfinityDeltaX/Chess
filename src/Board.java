import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;


public class Board {

	//private int[][] boardPosition;
	private TreeSet<Piece> boardPosition;

	public Board(Board input){
		this.boardPosition = new TreeSet<Piece>(byPosition);
		this.boardPosition.addAll(input.getBoardPosition());
	}
	public Board(){
		boardPosition = new TreeSet<Piece>(byPosition); //x, y coordinates. a8 == 0, 0; h1 == 7, 7; a1 == 7, 0; h8 == 0, 7
	}
	public Board(String startingPosFEN, ArrayList<Move> movesMade){
		this();
		this.setToFenString(startingPosFEN);
		for(Move current : movesMade){
			this.makeMove(current);
		}
	}
	public Board(String FenString){
		this();
		this.setToFenString(FenString);
	}

	public Set<Piece> getBoardPosition(){
		return new TreeSet<Piece>(boardPosition);
	}

	public void setPieceAtPosition(Position positionToSet, Piece pieceToPlace){
		assert(pieceToPlace.getPosition().equals(positionToSet));
		boardPosition.add(pieceToPlace);
	}

	public void setPositionToEmpty(Position positionToSet){
		boardPosition.removeIf(p -> p.getPosition().equals(positionToSet));
	}

	public void setToClearBoard(){
		boardPosition = new TreeSet<Piece>();
	}

	public boolean isPositionEmpty(Position input){
		return getPieceAtPosition(input) == null;
	}

	public void setToFenString(String input){
		this.setToClearBoard();
		String[] split = input.split("/"); //divides each row.
		split = Arrays.copyOfRange(split, 0, 8);
		System.out.println(Arrays.toString(split));

		for(int i = split.length-1; i >= 0; i--){ //rows
			int currentXPositionOnBoard = 0; //for example, if the first character we encounter is 5, this will go to 4, while j will iterate to 1.
			for (int j = 0; j < split[7-i].length(); j++) { //loop through each character. Columns. 

				char currentChar = split[7-i].charAt(j);
				Position currentPosition = new Position(currentXPositionOnBoard, i);

				if(currentChar <= '9' && currentChar >= '0'){ //if currentChar is an int, jump that number of spots.
					currentXPositionOnBoard+= (currentChar-'0'); //if we see one, we want to move 1 square. 

				} else { //currentChar is not an int, and is therefore a character representing a piece.
					setPieceAtPosition(currentPosition, Piece.getPieceFromLetter(currentChar, currentPosition));
					currentXPositionOnBoard++;
				}
			}
		}
	}

	public void setToDefaultBoard(){
		setToFenString(Values.defaultBoardFenString);
	}

	public Piece getPieceAtPosition(Position input){
		
		//Collections.binarySearch(this.boardPosition, new Rook(input, Side.WHITE));
		ArrayList<Piece> temp = new ArrayList<Piece>(boardPosition);
		int i = Collections.binarySearch(temp, new Rook(input, Side.WHITE));
		if(i < 0){
			return null;
		} else {
		return temp.get(i);
		}
		/*
		Optional<Piece> op =  boardPosition.stream().filter(piece -> piece.getPosition().equals(input)).findAny();
		if(op.isPresent()){
			return op.get();
		} else {
			return null;
		}
		*/
	}

	public HashSet<Piece> getArrayListofMyRealPieces(Side side){ //does not return empty spaces. Returns an ArrayList of pieces from the player specified in 'side'.
		HashSet<Piece> output = new HashSet<Piece>();

		boardPosition.stream().filter(derp -> derp.getSide() == side).forEach(output::add);
		return output;
	}

	public long testEvalSpeed(){
		long start = System.currentTimeMillis();
		for(int i = 0; i < 100; i++){
			this.evaluate();
		}
		return System.currentTimeMillis() - start;
	}

	public ArrayList<Move> getAllPossibleMoves(Side side){
		ArrayList<Move> output = new ArrayList<Move>();
		HashSet<Piece> myPieces = getArrayListofMyRealPieces(side);
		myPieces.stream().forEach( //for each piece,
				piece -> piece.getPossibleMoves(this).stream().map(pos -> new Move(piece, pos)).forEach(m -> output.add(m))
				);

		/*
		for(Piece currentPiece : myPieces){ //iterate through each of our pieces.
			for(Position currentEndingPosition : getPossibleMoves(currentPiece)){ //iterate through each place it can go
				output.add(new Move(currentPiece, currentEndingPosition)); // TODO

			}
		}
		 */
		return output;
	}

	public ArrayList<Board> getAllPossibleNextBoards(Side side){
		//iterate through getAllPossibleMoves and make the move.
		ArrayList<Board> output = new ArrayList<Board>();

		for(Move m : getAllPossibleMoves(side)){
			Board newBoard = new Board(this);
			newBoard.makeMove(m);
			output.add(newBoard);
		}
		return output;
	}

	public int[][] testSpeed(int maxDepth, int minPieces, int maxPieces, int certainty){
		//certainty is the number of times we repeat to get a good estimate.
		//test each number of pieces at each depth to get an idea of how fast the program runs.
		int[][] output = new int[maxDepth][maxPieces]; //[depth][pieces]
		Game game;
		for(int i = 1; i < maxDepth; i++){
			for(int j = minPieces; j < maxPieces; j++){
				long total = 0;
				for(int repeat = 0; repeat < certainty; repeat++){ //TODO make this average
					game = new Game(Side.WHITE);
					long start = System.currentTimeMillis();
					game.minimax(Side.WHITE, i, Board.generateRandomBoard(j), false);
					long end = System.currentTimeMillis();
					total += (end-start);
				}
				output[i][j] = (int) ((double) total/certainty);
				System.out.printf("Depth: %d; Pieces: %d; Average Time%d; \n", i, j, (total/certainty));
			}
		}
		return output;
	}

	private static Board generateRandomBoard(int numberOfPieces){
		Board output = new Board();
		Random r = new Random();
		while(output.getArrayListofMyRealPieces(Side.WHITE).size() + output.getArrayListofMyRealPieces(Side.BLACK).size() < numberOfPieces){
			int xCoord = r.nextInt(7);
			int yCoord = r.nextInt(7);
			Side side = r.nextInt(2) > 0 ? Side.WHITE : Side.BLACK; //1 or 2
			//System.out.printf("Generated new piece; x=%d, y=%d, side=%d, type=%d \n", xCoord, yCoord, side, type);
			
			Piece p = Piece.getRandomPiece(new Position(xCoord, yCoord), side);
			output.setPieceAtPosition(p.getPosition(), p);
		}
		return output;
	}

	public int evaluate(){
		return boardPosition.stream().mapToInt(p -> p.evaluateValue(this)).sum();
	}

	public int kingStatus(){ //returns zero if there are two kings, or -1 if white king gone, 1 if black king gone.
		boolean whiteKing = false; //do they exist
		boolean blackKing = false;
		for(Piece p : boardPosition){
			if (p.getClass().equals(King.class)){
				if(p.getSide() == Side.WHITE){
					whiteKing = true;
				}
				if(p.getSide() == Side.BLACK){
					blackKing = true;
				}
			}
		}
		return Values.booleanCompare(blackKing, whiteKing);

		/*
		 * 		boardPosition.stream().forEach(p -> {
			if(p.getClass().equals(King.class)){
				if(p.getSide() == Side.WHITE)
					whiteKing = true;
				if(p.getSide() == Side.BLACK)
					blackKing = true;

			}
		});
		 */
	}

	public int getGameState(){
		return (boardPosition.size() > Values.END_GAME_THRESHOLD ? Values.GAME_STATE_START : Values.GAME_STATE_END);
	}

	public void makeMove(Move m){

		//check if the move is an en passant
		if(m.getPiece().getClass().equals(Pawn.class) && this.isPositionEmpty(m.getToMoveTo()) && (Values.getAbsolutePoint(Piece.getDifference(m.getToMoveTo(), m.getOriginalPosition())).equals(new Point(1, 1)))){
			//System.out.println("En Passant detected!");
			if(Piece.getDifference(m.getToMoveTo(), m.getOriginalPosition()).equals(new Point(1, 1))){
				setPositionToEmpty(m.getToMoveTo().getPositionRelative(0, -1));
			}
			if(Piece.getDifference(m.getToMoveTo(), m.getOriginalPosition()).equals(new Point(-1, 1))){
				setPositionToEmpty(m.getToMoveTo().getPositionRelative(0, -1));
			}
			if(Piece.getDifference(m.getToMoveTo(), m.getOriginalPosition()).equals(new Point(1, -1))){
				setPositionToEmpty(m.getToMoveTo().getPositionRelative(0, 1));
			}
			if(Piece.getDifference(m.getToMoveTo(), m.getOriginalPosition()).equals(new Point(1, -1))){
				setPositionToEmpty(m.getToMoveTo().getPositionRelative(0, 1));
			}
		}

		//check if the move is a promotion
		else if(m.getPiece().getClass().equals(Pawn.class) && ((m.getPiece().getSide() == Side.WHITE && m.getToMoveTo().getRow() == 7) || (m.getPiece().getSide() == Side.BLACK && m.getToMoveTo().getRow() == 0))){
			//System.out.println("Promotion detected.");
			m.setPiece(new Queen(m.getOriginalPosition(), m.getPiece().getSide()));
		}

		//check if the move is a castle.
		else if(m.getPiece().getClass().equals(Pawn.class)){
			//System.out.println("Castling detected!");
			//System.out.println(Piece.getDifference(m.getOriginalPosition(), m.getToMoveTo()));
			if(m.getPiece().getSide() == Side.WHITE && Piece.getDifference(m.getOriginalPosition(), m.getToMoveTo()).equals(new Point(-2, 0))){
				this.makeMove(new Move(this.getPieceAtPosition(new Position("h1")), new Position("f1")));
			} else if(m.getPiece().getSide() == Side.BLACK && Piece.getDifference(m.getOriginalPosition(), m.getToMoveTo()).equals(new Point(2, 0))){
				this.makeMove(new Move(this.getPieceAtPosition(new Position("a8")), new Position("c8")));
			}
		}

		//setPieceAtPosition(m.originalPosition, Values.EMPTY_SQUARE);
		setPositionToEmpty(m.getOriginalPosition());
		Piece toSet = m.getPiece();
		toSet.setPosition(m.getToMoveTo());
		m.setPiece(toSet);
		setPieceAtPosition(m.getToMoveTo(), m.getPiece());
	}

	boolean isValidPlaceToMove(Position p, Side side){ //designed for knights and kings, this tests if one of the spots where they "can" move is A: unoccupied or B: has an opposing piece, but does NOT have a friendly piece. //side should the be the side of the moving piece. p is the destination position.
		return (p.doesExistOnBoard() && !isPositionEmpty(p) && getPieceAtPosition(p).getSide() != side); //using getPositionRelative because it doesn't actually modify the object.
		//This tests: is the new position on the board? and is the new position either enemy or unoccupied (not my own)?
	}

	public ArrayList<Position> getMovesAlongDirectionalAxisUntilInterrupted(Position current, RelativePosition r){ //x = 0, y = 1 will move the unit up until it hits an opposing piece, it's own piece, or the edge of the board. All of these positions will be returned.

		ArrayList<Position> output = new ArrayList<Position>();

		Position start = new Position(current);

		Side side = getPieceAtPosition(start).side;

		Piece temp = getPieceAtPosition(start);
		setPositionToEmpty(start);

		/*
		//we don't add the starting position, because moving to your original location is not valid.
		current.changePositionRelative(x, y); //without this, we look at the spot where we started, see our own piece in the spot where we are, end the loop, and return. This moves us 1 forwards immediately to avoid this.
		output.add(new Position(current)); */

		while(current.doesExistOnBoard() && isPositionEmpty(current)){ //while still valid
			current.changePositionRelative(r.getX(), r.getY());
			output.add(new Position(current));
		}

		if(!current.doesExistOnBoard()){ //we're off the board
			//move back onto the board.
			output.remove(output.size()-1); //get rid of the last one
		} else if(getPieceAtPosition(current).side == side){ //the piece that we ran into is our own
			//go back so we aren't on top of our piece.
			output.remove(output.size()-1); //get rid of the last one
		} else { //we're on top of an opposing piece, and that is O.K.

		}
		setPieceAtPosition(start, temp); //replace ourselves. 
		//System.out.println(output);
		return output;
	}

	public void dumpBoard(){
		for(Piece p : boardPosition){
			System.out.println(p);
		}
	}
	
	public String FENString(Side activeSide){
		char castling = '-';
		char enPassant = '-';
		int halfMoveClock = 0;
		int fullMoveClock = 0;

		StringBuilder sb = new StringBuilder();
		for(int i = 7; i >= 0; i--){
			for(int j = 0; j < 8; j++){
				Position current = new Position(j, i);
				if(this.getPieceAtPosition(current) == null)sb.append(' ');
				else if(this.getPieceAtPosition(current).getSide() == Side.WHITE){
					sb.append(this.getPieceAtPosition(current).getTypeLetter());
				} else sb.append(Character.toLowerCase(this.getPieceAtPosition(current).getTypeLetter()));
			}
			sb.append("/");
		}
		StringBuilder sb2 = new StringBuilder(replaceIsWithNums(sb.toString()));
		sb2.append(castling + " " + enPassant + " " + halfMoveClock + " " + fullMoveClock);
		return sb2.toString();
	}

	private static String replaceIsWithNums(String input){ //fen helper. Replaces rows of unoccupied cells with a number.
		int count = 0;
		boolean lastWasI = false;

		StringBuilder sb = new StringBuilder(input);

		for(int i = 0; i < sb.length(); i++){ //iterate over input string
			char current = sb.charAt(i);
			//System.out.println("Looking at: " + current);
			if(current == ' '){
				count++;
				lastWasI = true;
			} else if(lastWasI){ //breaking a streak
				sb.insert(i, (char)((int)'0' + count)); //insert the number of consecutive Is into the String.
				count = 0;
				lastWasI = false;
			}
		}
		//sb.repl
		String output = sb.toString();
		output = output.replace(" ", ""); //delete all remaining is.
		return output;
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append("===========\n");
		for(int i = 7; i >= 0; i--){
			for(int j = 0; j < 8; j++){
				Position current = new Position(j, i);
				if(this.getPieceAtPosition(current) == null)sb.append(' ');
				else if(this.getPieceAtPosition(current).getSide() == Side.WHITE){
					sb.append(this.getPieceAtPosition(current).getTypeLetter());
				} else sb.append(Character.toLowerCase(this.getPieceAtPosition(current).getTypeLetter()));
			}
			sb.append("\n");
		}
		sb.append("===========\n");
		return sb.toString();
	}
	public void setBoardPosition(TreeSet<Piece> boardPosition) {
		this.boardPosition = boardPosition;
	}
	
	Comparator<Piece> byPosition = new Comparator<Piece>(){
		@Override
		public int compare(Piece a, Piece b){
			return a.getPosition().compareTo(b.getPosition());
		}
	};
}
