import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class Board {

	private int[][] boardPosition;

	public Board(Board input){
		this.boardPosition = deepCopyArray(input.getBoardArrayInt());
	}
	public Board(){
		boardPosition = new int[8][8]; //x, y coordinates. a8 == 0, 0; h1 == 7, 7; a1 == 7, 0; h8 == 0, 7
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

	public void setPieceAtPosition(Position positionToSet, Piece pieceToPlace){
		int intOfPieceToPlace = pieceToPlace.getCorrespondingInt();
		//if(!pieceToPlace.getPosition().equals(positionToSet)) assert(false);
		pieceToPlace.setPosition(positionToSet);
		//should NOT be passed a general 5, for instance. Has to have side as well; 205 is acceptable.
		if(!((intOfPieceToPlace == Values.EMPTY_SQUARE) || (intOfPieceToPlace <= Values.KNIGHT_BLACK) && (intOfPieceToPlace >= Values.PAWN_WHITE))){
			assert(false); //needs to be a value between the biggest side-associated piece, KNIGHT_BLACK, and the smallest side-associated piece, PAWN_WHITE.
		}
		//System.out.println("Set piece " + pieceToPlace + " to position " + positionToSet);
		boardPosition[positionToSet.getFile()][positionToSet.getRow()] = intOfPieceToPlace;

		//we need to change the coordinates of the piece that we've been passed, as well.
		pieceToPlace.setPosition(positionToSet);
	}

	public void setPositionToEmpty(Position positionToSet){
		boardPosition[positionToSet.getFile()][positionToSet.getRow()] = 0;
	}

	public void setToClearBoard(){
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				setPositionToEmpty(new Position(i, j));
			}
		}
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
		boardPosition = new int[][]{
				{Values.ROOK_WHITE, Values.PAWN_WHITE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.PAWN_BLACK, Values.ROOK_BLACK},
				{Values.KNIGHT_WHITE, Values.PAWN_WHITE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.PAWN_BLACK, Values.KNIGHT_BLACK},
				{Values.BISHOP_WHITE, Values.PAWN_WHITE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.PAWN_BLACK, Values.BISHOP_BLACK},
				{Values.QUEEN_WHITE, Values.PAWN_WHITE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.PAWN_BLACK, Values.QUEEN_BLACK},
				{Values.KING_WHITE, Values.PAWN_WHITE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.PAWN_BLACK, Values.KING_BLACK},
				{Values.BISHOP_WHITE, Values.PAWN_WHITE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.PAWN_BLACK, Values.BISHOP_BLACK},
				{Values.KNIGHT_WHITE, Values.PAWN_WHITE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.PAWN_BLACK, Values.KNIGHT_BLACK},
				{Values.ROOK_WHITE, Values.PAWN_WHITE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.PAWN_BLACK, Values.ROOK_BLACK}};
	}

	public Piece getPieceAtPosition(Position p){
		if(p.doesExistOnBoard()){
			return Piece.getCorrespondingPiece(p, boardPosition[p.getFile()][p.getRow()]);
		} else return null;
	}

	Piece[][] getBoardArrayPiece(){

		Piece[][] output = new Piece[8][8];

		for(int i = 0; i < boardPosition.length; i++){
			for(int j = 0; j < boardPosition[0].length; j++){ //these two loops iterate over the whole int array
				//convert int to piece
				Piece current = Piece.getCorrespondingPiece(new Position(i, j), boardPosition[i][j]);
				output[i][j] = current;
			}
		}
		return output;
	}

	public ArrayList<Board> NextBoardTest(int notUsed){ //always returns 5 boards. used for testing the perft function.
		ArrayList<Board> output = new ArrayList<Board>();
		output.add(new Board());
		output.add(new Board());
		output.add(new Board());
		output.add(new Board());
		output.add(new Board());
		return output;
	}

	public ArrayList<Piece> getArrayListofMyRealPieces(int side){ //does not return empty spaces. Returns an ArrayList of pieces from the player specified in 'side'.
		ArrayList<Piece> output = new ArrayList<Piece>();
		for(int i = 0; i < boardPosition.length; i++){
			for(int j = 0; j < boardPosition[0].length; j++){ //these two loops iterate over the whole int array
				//convert int to piece
				Piece current = Piece.getCorrespondingPiece(new Position(i, j), boardPosition[i][j]);
				if(current.getSide() == side) output.add(current); //we only want to add current if it's our piece; not enemy's or blank.
			}
		}
		return output;
	}

	public int[][] getBoardArrayInt(){
		return deepCopyArray(boardPosition);
	}

	private int[][] deepCopyArray(int[][] input){
		int[][] output = new int[input.length][input[0].length];
		for (int i = 0; i < input.length; i++) {
			output[i] = Arrays.copyOf(input[i], input[i].length);
		}
		return output;
	}

	public long testEvalSpeed(){
		long start = System.currentTimeMillis();
		for(int i = 0; i < 100; i++){
			this.evaluate();
		}
		return System.currentTimeMillis() - start;
	}

	public ArrayList<Move> getAllPossibleMoves(int side){
		ArrayList<Move> output = new ArrayList<Move>();
		ArrayList<Piece> myPieces = getArrayListofMyRealPieces(side);
		for(Piece currentPiece : myPieces){ //iterate through each of our pieces.
			//System.out.println("Testing: " + currentPiece);
			ArrayList<Position> possibleLocationsAfterMove = getPossibleMoves(currentPiece); //get all the places it can go
			for(Position currentEndingPosition : possibleLocationsAfterMove){ //iterate through each place it can go
				Move m = new Move(currentPiece, currentEndingPosition);
				//System.out.println(m);
				//if(isLegalMove(m)){ //this is not totally done yet...
				output.add(m); // TODO
				//System.out.println("Added move: " + m);
				//}
			}
		}
		return output;
	}

	public ArrayList<Board> getAllPossibleNextBoards(int side){
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
		Board board;
		for(int i = 1; i < maxDepth; i++){
			for(int j = minPieces; j < maxPieces; j++){
				long total = 0;
				for(int repeat = 0; repeat < certainty; repeat++){ //TODO make this average
					game = new Game(Values.SIDE_WHITE);
					long start = System.currentTimeMillis();
					game.minimax(Values.SIDE_WHITE, i, Board.generateRandomBoard(j), false);
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
		while(output.getArrayListofMyRealPieces(Values.SIDE_WHITE).size() + output.getArrayListofMyRealPieces(Values.SIDE_BLACK).size() < numberOfPieces){
			int xCoord = r.nextInt(7);
			int yCoord = r.nextInt(7);
			int type = r.nextInt(6)+1;
			int side = r.nextInt(2)+1; //1 or 2
			//System.out.printf("Generated new piece; x=%d, y=%d, side=%d, type=%d \n", xCoord, yCoord, side, type);
			Piece p = new Piece(new Position(xCoord, yCoord), side, type);
			output.setPieceAtPosition(p.getPosition(), p);
		}
		return output;
	}

	private int evaluateMaterial(){ //returns the difference in material. Positive favors white.
		return(evaluateMaterialSide(Values.SIDE_WHITE) - evaluateMaterialSide(Values.SIDE_BLACK));
	}

	private int evaluateMaterialSide(int side){ //gets a positive int representing the total material for either side. 
		int count = 0;
		count += Values.POINT_VALUE_PAWN * countPiecesOfType(Values.PAWN, side);
		count += Values.POINT_VALUE_BISHOP * countPiecesOfType(Values.BISHOP, side);
		count += Values.POINT_VALUE_KNIGHT * countPiecesOfType(Values.KNIGHT, side);
		count += Values.POINT_VALUE_ROOK * countPiecesOfType(Values.ROOK, side);
		count += Values.POINT_VALUE_QUEEN * countPiecesOfType(Values.QUEEN, side);
		count += Values.POINT_VALUE_KING * countPiecesOfType(Values.KING, side);
		return count;
	}

	public int evaluate(){ //White side is trying to maximize, Black to minimize. 
		int count = 0;
		int gameState = getGameState();

		Piece[][] allPieces = getBoardArrayPiece();
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				int value = Values.POINT_VALUE_TABLE[allPieces[i][j].getType()]; //get 100, 900, etc.
				value += Values.getPieceSquareValue(allPieces[i][j], gameState); //get Piece-square tables. 
				if(allPieces[i][j].getSide() == Values.SIDE_BLACK) value = value*-1; //*-1 if it's black.

				count += value;
			}
		}

		return count;
	}

	public int kingStatus(){ //returns zero if there are two kings, or -1 if white king gone, 1 if black king gone.
		boolean whiteKing = false; //do they exist
		boolean blackKing = false;
		int bKingVal = Piece.getCorrespondingInt(new Piece(new Position(0,0), Values.SIDE_BLACK, Values.KING));
		int wKingVal = Piece.getCorrespondingInt(new Piece(new Position(0,0), Values.SIDE_WHITE, Values.KING));
		for (int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++){
				if(boardPosition[i][j] == wKingVal) whiteKing = true;
				if(boardPosition[i][j] == bKingVal) blackKing = true;
			}
		}
		if(blackKing && whiteKing) return 0;
		else if(blackKing && !whiteKing) return -1;
		else if(!blackKing && whiteKing) return 1;
		else return 0;
	}
	
	public int getGameState(){
		int totalPieces = 0;
		int gameState = 0;
		Piece[][] allPieces = getBoardArrayPiece();

		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				totalPieces += (allPieces[i][j].getType() == Values.SIDE_BLACK || allPieces[i][j].getType() == Values.SIDE_WHITE) ? 1 : 0;
			}
		}
		return (totalPieces > Values.END_GAME_THRESHOLD ? Values.GAME_STATE_START : Values.GAME_STATE_END);
	}

	public void makeMove(Move m){
		//setPieceAtPosition(m.originalPosition, Values.EMPTY_SQUARE);
		setPositionToEmpty(m.getOriginalPosition());
		setPieceAtPosition(m.getToMoveTo(), m.getPiece());
	}
	private boolean isLegalMove(Move input){
		//return true;

		//for some reason, uncommenting this code creates a mega-derp with getAllPossibleMoves. Is this somehow modifying the input parameter??


		Move moveToCheck = new Move(input);
		Board afterMove = new Board(this);
		afterMove.makeMove(moveToCheck);

		if(afterMove.isKingInCheck()){
			return false; //if the king is in check after the move, we can't make it.
		}

		//is this all that is required?

		return true;

	}
	private boolean isKingInCheck(){
		//is the king in check on this board?
		return false;
	}

	public ArrayList<Position> getPossibleMoves(Piece p) { //wrapper function for getPossible___Moves. Given a piece, will return it's possible moves.

		if(p.getType() == Values.PAWN){
			return getPossiblePawnMoves(p.getPosition());
		} 
		else if(p.getType() == Values.BISHOP){
			return getPossibleBishopMoves(p.getPosition());
		}
		else if(p.getType() == Values.ROOK){
			return getPossibleRookMoves(p.getPosition());
		}
		else if(p.getType() == Values.KING){
			return getPossibleKingMoves(p.getPosition());
		}
		else if(p.getType() == Values.QUEEN){
			return getPossibleQueenMoves(p.getPosition());
		}
		else if(p.getType() == Values.KNIGHT){
			return getPossibleKnightMoves(p.getPosition());
		} else {
			assert(false); //we should never get here....
			ArrayList<Position> output = new ArrayList<Position>();
			return output;

		}
	}

	private ArrayList<Position> getPossibleQueenMoves(Position p){

		assert(getPieceAtPosition(p).type == Values.QUEEN); //make sure that it's a queen!
		assert(p.doesExistOnBoard());
		if(!p.doesExistOnBoard()){
			return null;
		}
		//the eight ways to attack [4 diagonals, 4 straights]. start at top and go clock-wise.
		ArrayList<Position> output = new ArrayList<Position>();

		output.addAll(getMovesAlongDirectionalAxisUntilInterrupted(new Position(p), 0, 1));
		output.addAll(getMovesAlongDirectionalAxisUntilInterrupted(new Position(p), 1, 1));
		output.addAll(getMovesAlongDirectionalAxisUntilInterrupted(new Position(p), 1, 0));
		output.addAll(getMovesAlongDirectionalAxisUntilInterrupted(new Position(p), 1, -1));
		output.addAll(getMovesAlongDirectionalAxisUntilInterrupted(new Position(p), 0, -1));
		output.addAll(getMovesAlongDirectionalAxisUntilInterrupted(new Position(p), -1, -1));
		output.addAll(getMovesAlongDirectionalAxisUntilInterrupted(new Position(p), -1, 0));
		output.addAll(getMovesAlongDirectionalAxisUntilInterrupted(new Position(p), -1, 1));

		return output;
	}

	private ArrayList<Position> getPossibleBishopMoves(Position p){

		assert(getPieceAtPosition(p).type == Values.BISHOP); //make sure that it's a queen!
		assert(p.doesExistOnBoard());
		if(!p.doesExistOnBoard()){
			return null;
		}
		//the four ways to attack [4 diagonals]. start at top and go clock-wise.
		ArrayList<Position> output = new ArrayList<Position>();

		output.addAll(getMovesAlongDirectionalAxisUntilInterrupted(new Position(p), 1, 1));
		output.addAll(getMovesAlongDirectionalAxisUntilInterrupted(new Position(p), 1, -1));
		output.addAll(getMovesAlongDirectionalAxisUntilInterrupted(new Position(p), -1, -1));
		output.addAll(getMovesAlongDirectionalAxisUntilInterrupted(new Position(p), -1, 1));

		return output;
	}

	private ArrayList<Position> getPossibleRookMoves(Position p){

		assert(getPieceAtPosition(p).type == Values.ROOK); //make sure that it's a queen!
		assert(p.doesExistOnBoard());
		if(!p.doesExistOnBoard()){ //an alternative for assert. Assert is better because if we ever call this on the wrong piece, something bad happened.
			return null;
		}
		//the four ways to attack [4 straights]. start at top and go clock-wise.
		ArrayList<Position> output = new ArrayList<Position>();

		output.addAll(getMovesAlongDirectionalAxisUntilInterrupted(new Position(p), 0, 1));
		output.addAll(getMovesAlongDirectionalAxisUntilInterrupted(new Position(p), 1, 0));
		output.addAll(getMovesAlongDirectionalAxisUntilInterrupted(new Position(p), 0, -1));
		output.addAll(getMovesAlongDirectionalAxisUntilInterrupted(new Position(p), -1, 0));

		return output;
	}

	private ArrayList<Position> getPossibleKnightMoves(Position p){

		assert(getPieceAtPosition(p).type == Values.KNIGHT); //make sure that it's a queen!
		assert(p.doesExistOnBoard());
		if(!p.doesExistOnBoard()){
			return null;
		}
		//the eight ways to attack [circle thingy]. start at top and go clock-wise.
		ArrayList<Position> output = new ArrayList<Position>();
		int side = getPieceAtPosition(p).side;

		if(isValidPlaceToMove(p.getPositionRelative(1, 2), side)){output.add(p.getPositionRelative(1, 2));} //the coordinates are the destination position. TLDR: If it's a valid place to move, add it to the list of places to move.
		if(isValidPlaceToMove(p.getPositionRelative(2, 1), side)){output.add(p.getPositionRelative(2, 1));}
		if(isValidPlaceToMove(p.getPositionRelative(2, -1), side)){output.add(p.getPositionRelative(2, -1));}
		if(isValidPlaceToMove(p.getPositionRelative(1, -2), side)){output.add(p.getPositionRelative(1, -2));}
		if(isValidPlaceToMove(p.getPositionRelative(-1, -2), side)){output.add(p.getPositionRelative(-1, -2));}
		if(isValidPlaceToMove(p.getPositionRelative(-2, -1), side)){output.add(p.getPositionRelative(-2, -1));}
		if(isValidPlaceToMove(p.getPositionRelative(-2, 1), side)){output.add(p.getPositionRelative(-2, 1));}
		if(isValidPlaceToMove(p.getPositionRelative(-1, 2), side)){output.add(p.getPositionRelative(-1, 2));}
		return output;
	}

	private ArrayList<Position> getPossibleKingMoves(Position p){

		assert(getPieceAtPosition(p).type == Values.KING); //make sure that it's a queen!
		assert(p.doesExistOnBoard());
		if(!p.doesExistOnBoard()){
			return null;
		}
		//the eight ways to attack [circle]. start at top and go clock-wise.
		ArrayList<Position> output = new ArrayList<Position>();
		int side = getPieceAtPosition(p).side;

		if(isValidPlaceToMove(p.getPositionRelative(0, 1), side)){output.add(p.getPositionRelative(0, 1));} //the coordinates are the destination position. TLDR: If it's a valid place to move, add it to the list of places to move.
		if(isValidPlaceToMove(p.getPositionRelative(1, 1), side)){output.add(p.getPositionRelative(1, 1));}
		if(isValidPlaceToMove(p.getPositionRelative(1, 0), side)){output.add(p.getPositionRelative(1, 0));}
		if(isValidPlaceToMove(p.getPositionRelative(1, -1), side)){output.add(p.getPositionRelative(1, -1));}
		if(isValidPlaceToMove(p.getPositionRelative(0, -1), side)){output.add(p.getPositionRelative(0, -1));}
		if(isValidPlaceToMove(p.getPositionRelative(-1, -1), side)){output.add(p.getPositionRelative(-1, -1));}
		if(isValidPlaceToMove(p.getPositionRelative(-1, 0), side)){output.add(p.getPositionRelative(-1, 0));}
		if(isValidPlaceToMove(p.getPositionRelative(-1, 1), side)){output.add(p.getPositionRelative(-1, 1));}
		return output;
	}

	private ArrayList<Position> getPossiblePawnMoves(Position p){

		assert(getPieceAtPosition(p).type == Values.PAWN); //make sure that it's a queen!
		assert(p.doesExistOnBoard());
		if(!p.doesExistOnBoard()){
			return null;
		}
		//the eight ways to attack [circle]. start at top and go clock-wise.
		ArrayList<Position> output = new ArrayList<Position>();
		int side = getPieceAtPosition(p).side;
		//System.out.println(side);

		boolean isFirstMove = false; //figure out if it is our first move; if it is, we can move forwards 2.
		if((side == Values.SIDE_BLACK && p.getRow() == Values.PAWN_ROW_BLACK) || (side == Values.SIDE_WHITE && p.getRow() == Values.PAWN_ROW_WHITE))isFirstMove = true; //we're on the original pawn file for our color. [Where all the pawns start]

		if(side == Values.SIDE_WHITE){
			if(p.getPositionRelative(0, 2).doesExistOnBoard() && (getPieceAtPosition(p.getPositionRelative(0, 1)).isEmpty() && getPieceAtPosition(p.getPositionRelative(0, 2)).isEmpty()) && isFirstMove){output.add(p.getPositionRelative(0, 2));} //there are two empty squares in front of us, and we're on the original file. We can move 2 forwards!
			if(p.getPositionRelative(0, 1).doesExistOnBoard() && getPieceAtPosition(p.getPositionRelative(0, 1)).isEmpty()){output.add(p.getPositionRelative(0, 1));} //See if we can move forwards; there cannot be a piece there for this to work!
			if(p.getPositionRelative(1, 1).doesExistOnBoard() && (getPieceAtPosition(p.getPositionRelative(1, 1)).getSide() == Values.getOpposingSide(side))){output.add(p.getPositionRelative(1, 1));} //see if we can move diagonally for a capture. They must have a piece there for this to work!
			if(p.getPositionRelative(-1, 1).doesExistOnBoard() && (getPieceAtPosition(p.getPositionRelative(-1, 1)).getSide() == Values.getOpposingSide(side))){output.add(p.getPositionRelative(-1, 1));} //check diagonally the other way
		}

		if(side == Values.SIDE_BLACK){
			if(p.getPositionRelative(0, -2).doesExistOnBoard() && (getPieceAtPosition(p.getPositionRelative(0, -1)).isEmpty() && getPieceAtPosition(p.getPositionRelative(0, -2)).isEmpty()) && isFirstMove){output.add(p.getPositionRelative(0, -2));} //there are two empty squares in front of us, and we're on the original file. We can move 2 forwards!
			if(p.getPositionRelative(0, -1).doesExistOnBoard() && getPieceAtPosition(p.getPositionRelative(0, -1)).isEmpty()){output.add(p.getPositionRelative(0, -1));} //See if we can move forwards; there cannot be a piece there for this to work!
			if(p.getPositionRelative(1, -1).doesExistOnBoard() && (getPieceAtPosition(p.getPositionRelative(1, -1)).getSide() == Values.getOpposingSide(side))){output.add(p.getPositionRelative(1, -1));} //see if we can move diagonally for a capture. They must have a piece there for this to work!
			if(p.getPositionRelative(-1, -1).doesExistOnBoard() && (getPieceAtPosition(p.getPositionRelative(-1, -1)).getSide() == Values.getOpposingSide(side))){output.add(p.getPositionRelative(-1, -1));} //check diagonally the other way
		}
		// TODO we still have to deal with en passant....

		return output;
	}

	public boolean isCheckMate(){

	}

	private boolean isValidPlaceToMove(Position p, int side){ //designed for knights and kings, this tests if one of the spots where they "can" move is A: unoccupied or B: has an opposing piece, but does NOT have a friendly piece. //side should the be the side of the moving piece. p is the destination position.
		return (p.doesExistOnBoard() && getPieceAtPosition(p).getSide() != side); //using getPositionRelative because it doesn't actually modify the object.
		//This tests: is the new position on the board? and is the new position either enemy or unoccupied (not my own)?
	}

	private ArrayList<Position> getMovesAlongDirectionalAxisUntilInterrupted(Position current, int x, int y){ //x = 0, y = 1 will move the unit up until it hits an opposing piece, it's own piece, or the edge of the board. All of these positions will be returned.

		ArrayList<Position> output = new ArrayList<Position>();

		Position start = new Position(current);

		int side = getPieceAtPosition(start).side;

		Piece temp = new Piece(getPieceAtPosition(start));
		setPositionToEmpty(start);

		/*
		//we don't add the starting position, because moving to your original location is not valid.
		current.changePositionRelative(x, y); //without this, we look at the spot where we started, see our own piece in the spot where we are, end the loop, and return. This moves us 1 forwards immediately to avoid this.
		output.add(new Position(current)); */

		while(current.doesExistOnBoard() && getPieceAtPosition(current).isEmpty()){ //while still valid
			current.changePositionRelative(x, y);
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

	public String FENString(int activeSide){
		char castling = '-';
		char enPassant = '-';
		int halfMoveClock = 0;
		int fullMoveClock = 0;

		StringBuilder sb = new StringBuilder();
		for(int i = 7; i >= 0; i--){
			for(int j = 0; j < 8; j++){
				Position current = new Position(j, i);
				if(this.getPieceAtPosition(current) == null)sb.append(' ');
				else if(this.getPieceAtPosition(current).getSide() == Values.SIDE_WHITE){
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
			if(current == 'i'){
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
		output = output.replace("i", ""); //delete all remaining is.
		return output;
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		for(int i = 7; i >= 0; i--){
			for(int j = 0; j < 8; j++){
				Position current = new Position(j, i);
				if(this.getPieceAtPosition(current) == null)sb.append(' ');
				else if(this.getPieceAtPosition(current).getSide() == Values.SIDE_WHITE){
					sb.append(this.getPieceAtPosition(current).getTypeLetter());
				} else sb.append(Character.toLowerCase(this.getPieceAtPosition(current).getTypeLetter()));
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	private int countPiecesOfType(int type, int side){ //sent pawn, black, will return the number of black pawns.
		int counter = 0;
		for(Piece p : getArrayListofMyRealPieces(side)){
			if(p.getType() == type){
				counter++;
			}
		}
		return counter;
	}
}
