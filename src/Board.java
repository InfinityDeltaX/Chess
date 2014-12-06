import java.util.ArrayList;


public class Board {
	
	private int[][] boardPosition;
	
	public Board(Board input){
		this.boardPosition = input.getBoardArrayInt();
	}
	public Board(){
		boardPosition = new int[8][8];
	}
	
	void setToDefaultBoard(){
		boardPosition = new int[][]{
				{Values.ROOK_WHITE, Values.KNIGHT_WHITE, Values.BISHOP_WHITE, Values.QUEEN_WHITE, Values.KING_WHITE, Values.BISHOP_WHITE, Values.KNIGHT_WHITE, Values.ROOK_WHITE},
				{Values.PAWN_WHITE, Values.PAWN_WHITE, Values.PAWN_WHITE, Values.PAWN_WHITE, Values.PAWN_WHITE, Values.PAWN_WHITE, Values.PAWN_WHITE, Values.PAWN_WHITE}, 
				{Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE}, 
				{Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE}, 
				{Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE}, 
				{Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE}, 
				{Values.PAWN_BLACK, Values.PAWN_BLACK, Values.PAWN_BLACK, Values.PAWN_BLACK, Values.PAWN_BLACK, Values.PAWN_BLACK, Values.PAWN_BLACK, Values.PAWN_BLACK}, 
				{Values.ROOK_BLACK, Values.KNIGHT_BLACK, Values.BISHOP_BLACK, Values.QUEEN_BLACK, Values.KING_BLACK, Values.BISHOP_BLACK, Values.KNIGHT_BLACK, Values.ROOK_BLACK}};
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
		//move up first, then rotate counter-clockwise among the eight ways to attack [4 diagonals, 4 straights]
		
	}
	
	boolean isCheckMate(){
		
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
		
		return output;
	}
}
