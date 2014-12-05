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
				{Values.ROOK_BLACK, Values.KNIGHT_BLACK, Values.BISHOP_BLACK, Values.QUEEN_BLACK, Values.KING_BLACK, Values.BISHOP_BLACK, Values.KNIGHT_BLACK, Values.ROOK_BLACK},
				{Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE}, 
				{Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE}, 
				{Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE}, 
				{Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE}, 
				{Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE}, 
				{Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE, Values.EMPTY_SQUARE}, 
				{Values.ROOK_BLACK, Values.KNIGHT_BLACK, Values.BISHOP_BLACK, Values.QUEEN_BLACK, Values.KING_BLACK, Values.BISHOP_BLACK, Values.KNIGHT_BLACK, Values.ROOK_BLACK}};
	}
	
	Piece[][] getBoardArrayPiece(){
		
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
	boolean isLegalMove(Move m){
		
		Board afterMove = new Board(this);
		afterMove.movePiece();
		
		if(isKingInCheck()){
			if(afterMove.isKingInCheck()){ //if the king is in check on the next board
				return false; //King is in check for both the current state, and the one afterwards. 2 turns w/ king in check = mate.
			}
		}
		
		
	}
	boolean isKingInCheck(){
		//is the king in check on this board?
	}
}
