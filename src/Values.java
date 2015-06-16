import java.awt.Point;
import java.util.HashMap;

public class Values {
	/*
	public static final int EMPTY_SQUARE = 0;
	
	public static final int PAWN_WHITE = 101;
	public static final int BISHOP_WHITE = 102;
	public static final int KING_WHITE = 103;
	public static final int QUEEN_WHITE = 104;
	public static final int ROOK_WHITE = 105;
	public static final int KNIGHT_WHITE = 106;
	
	public static final int PAWN_BLACK = 201;
	public static final int BISHOP_BLACK = 202;
	public static final int KING_BLACK = 203;
	public static final int QUEEN_BLACK = 204;
	public static final int ROOK_BLACK = 205;
	public static final int KNIGHT_BLACK = 206;
	
	public static final int PAWN = 1;
	public static final int BISHOP = 2;
	public static final int KING = 3;
	public static final int QUEEN = 4;
	public static final int ROOK = 5;
	public static final int KNIGHT = 6;
	*/
	public static final int POINT_VALUE_PAWN = 100;
	public static final int POINT_VALUE_BISHOP = 300;
	public static final int POINT_VALUE_KING = 1000000;
	public static final int POINT_VALUE_QUEEN = 900;
	public static final int POINT_VALUE_ROOK = 500;
	public static final int POINT_VALUE_KNIGHT = 300;
	
	public static int nodeCounter = 0;
	
	//public static final int[] POINT_VALUE_TABLE = new int[]{POINT_VALUE_EMPTY_SQUARE, POINT_VALUE_PAWN, POINT_VALUE_BISHOP, POINT_VALUE_KING, POINT_VALUE_QUEEN, POINT_VALUE_ROOK, POINT_VALUE_KNIGHT};
	
	//public static final int SIDE_WHITE = 1;
	//public static final int SIDE_BLACK = 2;
	
	public static Side SIDE_COMPUTER;
	public static Side SIDE_USER;
	
	public static int GAME_STATE_START = 0;
	public static int GAME_STATE_END = 1;
	
	public static int ACCEPTABLE_TIME_MIN = (int) ((double) 0.5*60*1000); //ms
	public static int ACCEPTABLE_TIME_MAX = 1*60*1000; //ms
	
	public static boolean MOVE_ORDERING = true;
	public static final int DEPTH_NOT_TO_ORDER = 1; //when only this many nodes remain, we won't bother with ordering, because it takes longer to sort than to guess. 
	
	public static int STARTING_DEPTH = 3;
	public static boolean lockDepth = true;
	
	public static final int PAWN_ROW_BLACK = 6;
	public static final int PAWN_ROW_WHITE = 1;
	
	public static final int END_GAME_THRESHOLD = 10;
	
	public static final String defaultBoardFenString = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";
	
	/*
	public static final int[][][][] PIECE_SQUARE_TABLE = new int[][][][]{
		{PIECE_SQUARE_EMPTY_SQUARE, PIECE_SQUARE_PAWN_START, PIECE_SQUARE_BISHOP_START, PIECE_SQUARE_KING_START, PIECE_SQUARE_QUEEN_START, PIECE_SQUARE_ROOK_START, PIECE_SQUARE_KNIGHT_START},
		{PIECE_SQUARE_EMPTY_SQUARE, PIECE_SQUARE_PAWN_END, PIECE_SQUARE_BISHOP_END, PIECE_SQUARE_KING_END, PIECE_SQUARE_QUEEN_END, PIECE_SQUARE_ROOK_END, PIECE_SQUARE_KNIGHT_END}
		}; //[0=start, 1=end][piece type][x coord][y coord]
	*/
	
	public static Point getAbsolutePoint(Point input){
		return new Point(Math.abs(input.x), Math.abs(input.x));
	}
	
	
	static int[][] getPieceSquareTable(int gameState, PieceType type){ //given a side and a type, return the corresponding piece-square table. Flip if black, etc.
		return gameState == GAME_STATE_START ? type.endGameSquareValues : type.endGameSquareValues;
	}
	
	
	public static int getPieceSquareValue(Piece input, int gameState){
		PieceType type = input.getType();
		Side side = input.getSide();
		int XCoord = input.getFile();
		int YCoord = 7-input.getRow(); //this is just how the array is stored. 
		
		if(side == Side.BLACK){ //invert for black side
			YCoord = 7-YCoord;
		}
		
		return (int) ((double) getPieceSquareTable(gameState, type)[YCoord][XCoord]);// * PIECE_SQUARE_VALUE_TYPE_MULTIPLIER.get(type));
	}
	
	private static int pieceSquareTableTotal(int[][] input){
		int count = 0;
		for(int i = 0; i < input.length; i++){
			for (int j = 0; j < input[0].length; j++) {
				count += input[i][j];
			}
		}
		return count;
	}
	
	private static boolean isFairPieceSquareTable(int[][] input){
		return(pieceSquareTableTotal(input) == 0);
	}
	
	public static int booleanCompare(boolean x, boolean y){
		if(x==y) return 0;
		else if((!x) && y) return -1;
		else return 1;
	}

}
