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
	
	Position moveUnitUntilInterrupted(Position current, int x, int y){ //x = 0, y = 1 will move the unit up until it hits an opposing piece, it's own piece, or the edge of the board.
		
		int side = getPieceAtPosition(current).side;
		boolean didChange = false;
		
		while(current.doesExistOnBoard() && getPieceAtPosition(current).isEmpty()){ //while still valid
			current.changePositionRelative(x, y);
			didChange = true;
		}
		if(didChange && (getPieceAtPosition(current) == null || getPieceAtPosition(current).side == side)){ //if the piece I run into is my own, I need to back up 1 space to be on an empty square.
			//this one applies for the case where we fall off the board, because if the position isn't valid, we should move it back once to where it is legal.
			current.changePositionRelative(x*-1, y*-1);
			return current;
		} else {
			return current; //otherwise, we can end up on top of the opposing piece
		}
	}
}
