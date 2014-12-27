
public class Values {
	
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
	
	public static final int POINT_VALUE_PAWN = 100;
	public static final int POINT_VALUE_BISHOP = 300;
	public static final int POINT_VALUE_KING = 1000000;
	public static final int POINT_VALUE_QUEEN = 900;
	public static final int POINT_VALUE_ROOK = 500;
	public static final int POINT_VALUE_KNIGHT = 300;
	public static final int POINT_VALUE_EMPTY_SQUARE = 0;
	
	public static final int[] POINT_VALUE_TABLE = new int[]{POINT_VALUE_EMPTY_SQUARE, POINT_VALUE_PAWN, POINT_VALUE_BISHOP, POINT_VALUE_KING, POINT_VALUE_QUEEN, POINT_VALUE_ROOK, POINT_VALUE_KNIGHT};
	
	public static final int SIDE_WHITE = 1;
	public static final int SIDE_BLACK = 2;
	
	public static int SIDE_COMPUTER;
	public static int SIDE_USER;
	
	public static int GAME_STATE_START = 0;
	public static int GAME_STATE_END = 1;
	
	public static final int ACCEPTABLE_TIME_MIN = (int) ((double) 0.5*60*1000); //ms
	public static final int ACCEPTABLE_TIME_MAX = 1*60*1000; //ms
	
	public static final boolean MOVE_ORDERING = true;
	public static final int DEPTH_NOT_TO_ORDER = 1; //when only this many nodes remain, we won't bother with ordering, because it takes longer to sort than to guess. 
	
	public static final int STARTING_DEPTH = 7;
	public static boolean lockDepth = true;
	
	public static final int PAWN_ROW_BLACK = 6;
	public static final int PAWN_ROW_WHITE = 1;
	
	public static final int END_GAME_THRESHOLD = 10;
	
	public static final String defaultBoardFenString = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";
	
	public static final double[] PIECE_SQUARE_VALUE_TYPE_MULTIPLIER = new double[]{0, 1, 1, 1, 1, 1, 0.5}; //multiply any values for knights by this[6].
	
	public static final int[][] PIECE_SQUARE_KING_END = new int[][]{ //applies for the white side; for black, flip the board over the X axis.
		{-20, -10, -10, -10, -10, -10, -10, -20}, 
		{-10, -7, 5, -5, -5, 5, -7, -10}, 
		{-10, 5, 20, 20, 20, 20, 5, -10}, 
		{-10, -5, 20, 27, 27, 20, -5, -10}, 
		{-10, -5, 20, 27, 27, 20, -5, -10}, 
		{-10, 5, 20, 20, 20, 20, 5, -10}, 
		{-10, -7, 5, -5, -5, 5, -7, -10}, 
		{-20, -10, -10, -10, -10, -10, -10, -20}
		};
	
	public static final int[][] PIECE_SQUARE_BISHOP_START = new int[][]{ //applies for the white side; for black, flip the board over the X axis.
		{-8, -8, -8, -8, -8, -8, -8, -8}, 
		{-8, 0, 0, 0, 0, 0, 0, -8}, 
		{-8, 0, 12, 12, 12, 12, 0, -8}, 
		{-8, 0, 12, 20, 20, 12, 0, -8}, 
		{-8, 0, 12, 20, 20, 12, 0, -8}, 
		{-8, 0, 12, 12, 12, 12, 0, -8}, 
		{-8, 0, 0, 0, 0, 0, 0, -8}, 
		{-8, -8, -8, -8, -8, -8, -8, -8}
		};
	
	public static final int[][] PIECE_SQUARE_ROOK_START = new int[][]{ //applies for the white side; for black, flip the board over the X axis.
		{-2, -2, -2, -2, -2, -2, -2, -2}, 
		{14, 14, 14, 14, 14, 14, 14, 14}, 
		{-2, -2, -2, -2, -2, -2, -2, -2}, 
		{-2, -2, -2, -2, -2, -2, -2, -2}, 
		{-2, -2, -2, -2, -2, -2, -2, -2}, 
		{-2, -2, -2, -2, -2, -2, -2, -2}, 
		{-2, -2, -2, -2, -2, -2, -2, -2}, 
		{-2, -2, -2, -2, -2, -2, -2, -2}
		};
	
	public static final int[][] PIECE_SQUARE_EMPTY_SQUARE = new int[][]{ //applies for the white side; for black, flip the board over the X axis.
		{0, 0, 0, 0, 0, 0, 0, 0}, 
		{0, 0, 0, 0, 0, 0, 0, 0}, 
		{0, 0, 0, 0, 0, 0, 0, 0}, 
		{0, 0, 0, 0, 0, 0, 0, 0}, 
		{0, 0, 0, 0, 0, 0, 0, 0}, 
		{0, 0, 0, 0, 0, 0, 0, 0}, 
		{0, 0, 0, 0, 0, 0, 0, 0}, 
		{0, 0, 0, 0, 0, 0, 0, 0}
		};
	
	public static final int[][] PIECE_SQUARE_KNIGHT_START = new int[][]{ //applies for the white side; for black, flip the board over the X axis.
		{-20, -10, -10, -10, -10, -10, -10, -20}, 
		{-10, -7, 5, -5, -5, 5, -7, -10}, 
		{-10, 5, 20, 20, 20, 20, 5, -10}, 
		{-10, -5, 20, 27, 27, 20, -5, -10}, 
		{-10, -5, 20, 27, 27, 20, -5, -10}, 
		{-10, 5, 20, 20, 20, 20, 5, -10}, 
		{-10, -7, 5, -5, -5, 5, -7, -10}, 
		{-20, -10, -10, -10, -10, -10, -10, -20}
		};
	
	public static final int[][] PIECE_SQUARE_PAWN_START = new int[][]{ //applies for the white side; for black, flip the board over the X axis.
		{0, 0, 0, 0, 0, 0, 0, 0}, 
		{8, 8, 8, 8, 8, 8, 8, 8}, 
		{5, 5, 5, 5, 5, 5, 5, 5}, 
		{0, 0, 0, 7, 7, 0, 0, 0}, 
		{-3, -3, 10, 10, -3, -3, -3, -3}, 
		{-6, -6, -6, -6, -6, -6, -6, -6}, 
		{-9, -9, -9, -9, -9, -9, -9, -9}, 
		{0, 0, 0, 0, 0, 0, 0, 0}
		};
	
	public static final int[][] PIECE_SQUARE_PAWN_END = PIECE_SQUARE_PAWN_START;
	
	public static final int[][] PIECE_SQUARE_KNIGHT_END = PIECE_SQUARE_KNIGHT_START;
	
	public static final int[][] PIECE_SQUARE_BISHOP_END = PIECE_SQUARE_BISHOP_START;
	
	public static final int[][] PIECE_SQUARE_ROOK_END = PIECE_SQUARE_ROOK_START;
	
	public static final int[][] PIECE_SQUARE_KING_START = new int[][]{
		{-4, -4, -4, 0, -4, -4, -4, -4}, 
		{-4, -4, -4, -4, -4, -4, -4, -4}, 
		{-4, -4, -4, -4, -4, -4, -4, -4}, 
		{-4, -4, -4, -4, -4, -4, -4, -4}, 
		{-4, -4, -4, -4, -4, -4, -4, -4}, 
		{-4, -4, -4, -4, -4, -4, -4, -4}, 
		{5, 5, 5, 5, 5, 5, 5, 5}, 
		{25, 25, 15, 11, 11, 15, 25, 25}
		};
	
	public static final int[][] PIECE_SQUARE_QUEEN_START = new int[][] {
		{-8, -8, -8, -8, -8, -8, -8, -8}, 
		{-8, 0, 0, 0, 0, 0, 0, -8}, 
		{-8, 0, 12, 12, 12, 12, 0, -8}, 
		{-8, 0, 12, 20, 20, 12, 0, -8}, 
		{-8, 0, 12, 20, 20, 12, 0, -8}, 
		{-8, 0, 12, 12, 12, 12, 0, -8}, 
		{-8, 0, 0, 0, 0, 0, 0, -8}, 
		{-8, -8, -8, -8, -8, -8, -8, -8}
		};
	
	
	
	public static final int[][] PIECE_SQUARE_QUEEN_END = PIECE_SQUARE_QUEEN_START;
	
	public static final int[][][][] PIECE_SQUARE_TABLE = new int[][][][]{
		{PIECE_SQUARE_EMPTY_SQUARE, PIECE_SQUARE_PAWN_START, PIECE_SQUARE_BISHOP_START, PIECE_SQUARE_KING_START, PIECE_SQUARE_QUEEN_START, PIECE_SQUARE_ROOK_START, PIECE_SQUARE_KNIGHT_START},
		{PIECE_SQUARE_EMPTY_SQUARE, PIECE_SQUARE_PAWN_END, PIECE_SQUARE_BISHOP_END, PIECE_SQUARE_KING_END, PIECE_SQUARE_QUEEN_END, PIECE_SQUARE_ROOK_END, PIECE_SQUARE_KNIGHT_END}
		}; //[0=start, 1=end][piece type][x coord][y coord]
	
	// TODO
	
	static int[][] getPieceSquareTable(int gameState, int type){ //given a side and a type, return the corresponding piece-square table. Flip if black, etc.
		return PIECE_SQUARE_TABLE[gameState][type];
	}
	
	public static int getPieceSquareValue(Piece input, int gameState){
		int type = input.getType();
		int side = input.getSide();
		int XCoord = input.getFile();
		int YCoord = 7-input.getRow(); //this is just how the array is stored. 
		
		if(side == Values.SIDE_BLACK){ //invert for black side
			YCoord = 7-YCoord;
		}
		
		//System.out.println(YCoord + " " + XCoord);
		
		return (int) ((double) getPieceSquareTable(gameState, type)[YCoord][XCoord] * PIECE_SQUARE_VALUE_TYPE_MULTIPLIER[type]);
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
	
	public static int getOpposingSide(int side){
		if(side == Values.SIDE_BLACK) return Values.SIDE_WHITE;
		else if(side == Values.SIDE_WHITE) return Values.SIDE_BLACK;
		else{
			return -101;
		}
	}

}
