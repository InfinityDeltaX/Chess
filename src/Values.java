
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
	
	public static final int SIDE_WHITE = 1;
	public static final int SIDE_BLACK = 2;
	
	public static final int SIDE_COMPUTER = 1;
	
	public static final int PAWN_ROW_BLACK = 6;
	public static final int PAWN_ROW_WHITE = 1;
	
	public static final int[][] PIECE_SQUARE_KING_END = new int[][]{ //applies for the white side; for black, flip the board over the X axis.
		{0, 0, 0, 0, 0, 0, 0, 0}, 
		{0, 0, 0, 0, 0, 0, 0, 0}, 
		{0, 0, 0, 0, 0, 0, 0, 0}, 
		{0, 0, 0, 0, 0, 0, 0, 0}, 
		{0, 0, 0, 0, 0, 0, 0, 0}, 
		{0, 0, 0, 0, 0, 0, 0, 0}, 
		{0, 0, 0, 0, 0, 0, 0, 0}, 
		{0, 0, 0, 0, 0, 0, 0, 0}
		};
	
	public static final int[][] PIECE_SQUARE_BISHOP_START = new int[][]{ //applies for the white side; for black, flip the board over the X axis.
		{0, 0, 0, 0, 0, 0, 0, 0}, 
		{0, 0, 0, 0, 0, 0, 0, 0}, 
		{0, 0, 0, 0, 0, 0, 0, 0}, 
		{0, 0, 0, 0, 0, 0, 0, 0}, 
		{0, 0, 0, 0, 0, 0, 0, 0}, 
		{0, 0, 0, 0, 0, 0, 0, 0}, 
		{0, 0, 0, 0, 0, 0, 0, 0}, 
		{0, 0, 0, 0, 0, 0, 0, 0}
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
	
	// TODO
	
	int[][] getPieceSquareTables(int side, int type){ //given a side and a type, return the corresponding piece-square table. Flip if black, etc.
		
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
	
	public static boolean allPieceSquareTablesFair(){
		boolean istrue = true;
		if(isFairPieceSquareTable(Values.PIECE_SQUARE_KING_END)){ istrue = false; System.out.println("Failed"); }
		if(isFairPieceSquareTable(Values.PIECE_SQUARE_KING_END)){ istrue = false; System.out.println("Failed"); }
		if(isFairPieceSquareTable(Values.PIECE_SQUARE_KING_END)){ istrue = false; System.out.println("Failed"); }
		if(isFairPieceSquareTable(Values.PIECE_SQUARE_KING_END)){ istrue = false; System.out.println("Failed"); }
		if(isFairPieceSquareTable(Values.PIECE_SQUARE_KING_END)){ istrue = false; System.out.println("Failed"); }
		if(isFairPieceSquareTable(Values.PIECE_SQUARE_KING_END)){ istrue = false; System.out.println("Failed"); }
		if(isFairPieceSquareTable(Values.PIECE_SQUARE_KING_END)){ istrue = false; System.out.println("Failed"); }
		if(isFairPieceSquareTable(Values.PIECE_SQUARE_KING_END)){ istrue = false; System.out.println("Failed"); }
		
		return istrue;
			
	}
	
	public static int getOpposingSide(int side){
		if(side == Values.SIDE_BLACK) return Values.SIDE_WHITE;
		else if(side == Values.SIDE_WHITE) return Values.SIDE_BLACK;
		else{
			assert(false);
			return -100;
		}
	}

}
