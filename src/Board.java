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
	
	void setPieceAtPosition(Position positionToSet, int pieceToPlace){ //mostly a debugging function
		//should NOT be passed a general 5, for instance. Has to have side as well; 205 is acceptable.
		boardPosition[positionToSet.getFile()][positionToSet.getRow()] = pieceToPlace;
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
	int[][] getBoardArrayInt(){
		return boardPosition;
	}
	ArrayList<Move> getAllLegalMoves(){
		
	}
	int evaluate(){
		
	}
	void movePiece(Move m){
		
	}
	boolean isLegalMove(Move moveToCheck){
		
		Board afterMove = new Board(this);
		afterMove.movePiece(moveToCheck);
		
		if(afterMove.isKingInCheck()){
			return false; //if the king is in check after the move, we can't make it.
		}
		
	}
	boolean isKingInCheck(){
		//is the king in check on this board?
	}
	
	ArrayList<Board> getPossibleNextMoveStates(int side){
		for()
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
		System.out.println(output);
		return output;
	}
}
