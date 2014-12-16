import java.util.ArrayList;
import java.util.Arrays;


public class Board {

	private int[][] boardPosition;

	public Board(Board input){
		this.boardPosition = input.getBoardArrayInt();
	}
	public Board(){
		boardPosition = new int[8][8];
	}

	void setPieceAtPosition(Position positionToSet, Piece pieceToPlace){ //mostly a debugging function
		int intOfPieceToPlace = pieceToPlace.getCorrespondingInt();
		//should NOT be passed a general 5, for instance. Has to have side as well; 205 is acceptable.
		if(!((intOfPieceToPlace == Values.EMPTY_SQUARE) || (intOfPieceToPlace <= Values.KNIGHT_BLACK) && (intOfPieceToPlace >= Values.PAWN_WHITE))){
			assert(false); //needs to be a value between the biggest side-associated piece, KNIGHT_BLACK, and the smallest side-associated piece, PAWN_WHITE.
		}
		//System.out.println(positionToSet);
		boardPosition[positionToSet.getFile()][positionToSet.getRow()] = intOfPieceToPlace;

		//we need to change the coordinates of the piece that we've been passes, as well.
		pieceToPlace.setPosition(positionToSet);
	}

	void setPositionToEmpty(Position positionToSet){
		boardPosition[positionToSet.getFile()][positionToSet.getRow()] = 0;
	}

	void setToDefaultBoard(){
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

	Piece getPieceAtPosition(Position p){
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

	ArrayList<Board> NextBoardTest(int notUsed){ //always returns 5 boards. used for testing the perft function.
		ArrayList<Board> output = new ArrayList<Board>();
		output.add(new Board());
		output.add(new Board());
		output.add(new Board());
		output.add(new Board());
		output.add(new Board());
		return output;
	}
	
	ArrayList<Piece> getArrayListofMyRealPieces(int side){ //does not return empty spaces. Returns an ArrayList of pieces from the player specified in 'side'.
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

	int[][] getBoardArrayInt(){
		return boardPosition;
	}

	ArrayList<Move> getAllPossibleMoves(int side){
		ArrayList<Move> output = new ArrayList<Move>();
		ArrayList<Piece> myPieces = getArrayListofMyRealPieces(side);
		for(Piece currentPiece : myPieces){ //iterate through each of our pieces.
			//System.out.println("Testing: " + currentPiece);
			ArrayList<Position> possibleLocationsAfterMove = getPossibleMoves(currentPiece); //get all the places it can go
			for(Position currentEndingPosition : possibleLocationsAfterMove){ //iterate through each place it can go
				Move m = new Move(currentPiece, currentEndingPosition);
				//System.out.println(m);
				if(isLegalMove(m)){ //this is not totally done yet...
					output.add(m); // TODO
					//System.out.println("Added move: " + m);
				}
			}
		}
		return output;
	}

	ArrayList<Board> getAllPossibleNextBoards(int side){
		//iterate through getAllPossibleMoves and make the move.

		ArrayList<Board> output = new ArrayList<Board>();

		for(Move m : getAllPossibleMoves(side)){
			Board newBoard = new Board(this);
			newBoard.makeMove(m);
			output.add(newBoard);
		}
		return output;
	}

	int evaluate(){ //White side is trying to maximize, Black to minimize. 
		//first, deal with material, then, deal with piece-square tables.
		return evaluateMaterial();
		// TODO add piece-square tables.
	}

	int evaluateMaterial(){ //returns the difference in material. Positive favors white.
		return(evaluateMaterialSide(Values.SIDE_WHITE) - evaluateMaterialSide(Values.SIDE_BLACK));
	}

	int evaluateMaterialSide(int side){ //gets a positive int representing the total material for either side. 
		int count = 0;
		count += Values.POINT_VALUE_PAWN * countPiecesOfType(Values.PAWN, side);
		count += Values.POINT_VALUE_BISHOP * countPiecesOfType(Values.BISHOP, side);
		count += Values.POINT_VALUE_KNIGHT * countPiecesOfType(Values.KNIGHT, side);
		count += Values.POINT_VALUE_ROOK * countPiecesOfType(Values.ROOK, side);
		count += Values.POINT_VALUE_QUEEN * countPiecesOfType(Values.QUEEN, side);
		count += Values.POINT_VALUE_KING * countPiecesOfType(Values.KING, side);
		return count;
	}

	int evaluatePieceSquareSide(int side){

	}

	void makeMove(Move m){
		//setPieceAtPosition(m.originalPosition, Values.EMPTY_SQUARE);
		setPositionToEmpty(m.originalPosition);
		setPieceAtPosition(m.toMoveTo, m.getPiece());
	}
	boolean isLegalMove(Move input){
		return true;
		
		//for some reason, uncommenting this code creates a mega-derp with getAllPossibleMoves. Is this somehow modifying the input parameter??
		
		/*
		Move moveToCheck = new Move(input);
		Board afterMove = new Board(this);
		afterMove.makeMove(moveToCheck);

		if(afterMove.isKingInCheck()){
			return false; //if the king is in check after the move, we can't make it.
		}

		//is this all that is required?

		return true;
		*/
	}
	boolean isKingInCheck(){
		//is the king in check on this board?
		return false;
	}

	ArrayList<Position> getPossibleMoves(Piece p) { //wrapper function for getPossible___Moves. Given a piece, will return it's possible moves.

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

	ArrayList<Position> getPossibleQueenMoves(Position p){

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

	ArrayList<Position> getPossibleBishopMoves(Position p){

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

	ArrayList<Position> getPossibleRookMoves(Position p){

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

	ArrayList<Position> getPossibleKnightMoves(Position p){

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

	ArrayList<Position> getPossibleKingMoves(Position p){

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

	ArrayList<Position> getPossiblePawnMoves(Position p){

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

	boolean isCheckMate(){

	}

	boolean isValidPlaceToMove(Position p, int side){ //designed for knights and kings, this tests if one of the spots where they "can" move is A: unoccupied or B: has an opposing piece, but does NOT have a friendly piece. //side should the be the side of the moving piece. p is the destination position.
		return (p.doesExistOnBoard() && getPieceAtPosition(p).getSide() != side); //using getPositionRelative because it doesn't actually modify the object.
		//This tests: is the new position on the board? and is the new position either enemy or unoccupied (not my own)?
	}

	ArrayList<Position> getMovesAlongDirectionalAxisUntilInterrupted(Position current, int x, int y){ //x = 0, y = 1 will move the unit up until it hits an opposing piece, it's own piece, or the edge of the board. All of these positions will be returned.

		ArrayList<Position> output = new ArrayList<Position>();

		int side = getPieceAtPosition(current).side;
		boolean didChange = false;

		//we don't add the starting position, because moving to your original location is not valid.
		current.changePositionRelative(x, y); //without this, we look at the spot where we started, see our own piece in the spot where we are, end the loop, and return. This moves us 1 forwards immediately to avoid this.
		output.add(new Position(current));

		while(current.doesExistOnBoard() && getPieceAtPosition(current).isEmpty()){ //while still valid
			current.changePositionRelative(x, y);
			output.add(new Position(current));
			didChange = true;
		}


		if(!didChange){ //cannot move at all
			//account for initial jump-start
			output.remove(output.size()-1); //get rid of the last one
		} else if(!current.doesExistOnBoard()){ //we're off the board
			//move back onto the board.
			output.remove(output.size()-1); //get rid of the last one
		} else if(getPieceAtPosition(current).side == side){ //the piece that we ran into is our own
			//go back so we aren't on top of our piece.
			output.remove(output.size()-1); //get rid of the last one
		} else { //we're on top of an opposing piece, and that is O.K.

		}
		//System.out.println(output);
		return output;
	}

	static String FENString(Board b, int activeSide){
		char castling = '-';
		char enPassant = '-';
		int halfMoveClock = 0;
		int fullMoveClock = 0;

		StringBuilder sb = new StringBuilder();
		for(int i = 7; i >= 1; i--){
			for(int j = 1; j < 8; j++){
				Position current = new Position(j, i);
				if(b.getPieceAtPosition(current) == null)sb.append(' ');
				else if(b.getPieceAtPosition(current).getSide() == Values.SIDE_WHITE){
					sb.append(b.getPieceAtPosition(current).getTypeLetter());
				} else sb.append(Character.toLowerCase(b.getPieceAtPosition(current).getTypeLetter()));
			}
			sb.append("/ \n");
		}
		System.out.println(sb);
		return sb.toString();
	}
	
	static String replaceIsWithNums(String input){ //fen helper. Replaces rows of unoccupied cells with a number.
		int count = 0;
		boolean lastWasI = false;
		
		StringBuilder sb = new StringBuilder(input);
		
		for(int i = 0; i < sb.length(); i++){ //iterate over input string
			char current = sb.charAt(i);
			System.out.println("Looking at: " + current);
			if(current == 'i'){
				count++;
				//sb.setCharAt(i, '~');
				//i--;
				sb.deleteCharAt(i);
				i--;
				lastWasI = true;
			} else if(lastWasI){ //breaking a streak
				sb.setCharAt(i, (char)((int)'0' + count));
				count = 0;
				lastWasI = false;
			}
		}
		//sb.repl
		return sb.toString();
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
		System.out.println(sb);
		return sb.toString();
	}
	
	int countPiecesOfType(int type, int side){ //sent pawn, black, will return the number of black pawns.
		int counter = 0;
		for(Piece p : getArrayListofMyRealPieces(side)){
			if(p.getType() == type){
				counter++;
			}
		}
		return counter;
	}
}
