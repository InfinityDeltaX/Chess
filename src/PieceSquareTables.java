
public class PieceSquareTables {
	public static final int[][] KING_END = new int[][]{ //applies for the white side; for black, flip the board over the X axis.
		{-20, -10, -10, -10, -10, -10, -10, -20}, 
		{-10, -7, 5, -5, -5, 5, -7, -10}, 
		{-10, 5, 20, 20, 20, 20, 5, -10}, 
		{-10, -5, 20, 27, 27, 20, -5, -10}, 
		{-10, -5, 20, 27, 27, 20, -5, -10}, 
		{-10, 5, 20, 20, 20, 20, 5, -10}, 
		{-10, -7, 5, -5, -5, 5, -7, -10}, 
		{-20, -10, -10, -10, -10, -10, -10, -20}
		};
	
	public static final int[][] BISHOP_START = new int[][]{ //applies for the white side; for black, flip the board over the X axis.
		{-8, -8, -8, -8, -8, -8, -8, -8}, 
		{-8, 0, 0, 0, 0, 0, 0, -8}, 
		{-8, 0, 12, 12, 12, 12, 0, -8}, 
		{-8, 0, 12, 20, 20, 12, 0, -8}, 
		{-8, 0, 12, 20, 20, 12, 0, -8}, 
		{-8, 0, 12, 12, 12, 12, 0, -8}, 
		{-8, 0, 0, 0, 0, 0, 0, -8}, 
		{-8, -8, -8, -8, -8, -8, -8, -8}
		};
	
	public static final int[][] ROOK_START = new int[][]{ //applies for the white side; for black, flip the board over the X axis.
		{-2, -2, -2, -2, -2, -2, -2, -2}, 
		{14, 14, 14, 14, 14, 14, 14, 14}, 
		{-2, -2, -2, -2, -2, -2, -2, -2}, 
		{-2, -2, -2, -2, -2, -2, -2, -2}, 
		{-2, -2, -2, -2, -2, -2, -2, -2}, 
		{-2, -2, -2, -2, -2, -2, -2, -2}, 
		{-2, -2, -2, -2, -2, -2, -2, -2}, 
		{-2, -2, -2, -2, -2, -2, -2, -2}
		};
	
	public static final int[][] KNIGHT_START = new int[][]{ //applies for the white side; for black, flip the board over the X axis.
		{-20, -10, -10, -10, -10, -10, -10, -20}, 
		{-10, -7, 5, -5, -5, 5, -7, -10}, 
		{-10, 5, 20, 20, 20, 20, 5, -10}, 
		{-10, -5, 20, 27, 27, 20, -5, -10}, 
		{-10, -5, 20, 27, 27, 20, -5, -10}, 
		{-10, 5, 20, 20, 20, 20, 5, -10}, 
		{-10, -7, 5, -5, -5, 5, -7, -10}, 
		{-20, -10, -10, -10, -10, -10, -10, -20}
		};
	
	public static final int[][] PAWN_START = new int[][]{ //applies for the white side; for black, flip the board over the X axis.
		{0, 0, 0, 0, 0, 0, 0, 0}, 
		{8, 8, 8, 8, 8, 8, 8, 8}, 
		{5, 5, 5, 5, 5, 5, 5, 5}, 
		{0, 0, 0, 7, 7, 0, 0, 0}, 
		{-3, -3, -3, 10, 10, -3, -3, -3}, 
		{-6, -6, -6, -6, -6, -6, -6, -6}, 
		{-9, -9, -9, -9, -9, -9, -9, -9}, 
		{0, 0, 0, 0, 0, 0, 0, 0}
		};
	
	public static final int[][] KING_START = new int[][]{
		{-4, -4, -4, 0, -4, -4, -4, -4}, 
		{-4, -4, -4, -4, -4, -4, -4, -4}, 
		{-4, -4, -4, -4, -4, -4, -4, -4}, 
		{-4, -4, -4, -4, -4, -4, -4, -4}, 
		{-4, -4, -4, -4, -4, -4, -4, -4}, 
		{-4, -4, -4, -4, -4, -4, -4, -4}, 
		{5, 5, 5, 5, 5, 5, 5, 5}, 
		{15, 15, 15, 15, 15, 15, 15, 15}
		};
	
	public static final int[][] QUEEN_START = new int[][] {
		{-8, -8, -8, -8, -8, -8, -8, -8}, 
		{-8, 0, 0, 0, 0, 0, 0, -8}, 
		{-8, 0, 12, 12, 12, 12, 0, -8}, 
		{-8, 0, 12, 20, 20, 12, 0, -8}, 
		{-8, 0, 12, 20, 20, 12, 0, -8}, 
		{-8, 0, 12, 12, 12, 12, 0, -8}, 
		{-8, 0, 0, 0, 0, 0, 0, -8}, 
		{-8, -8, -8, -8, -8, -8, -8, -8}
		};
	
	public static final int[][] PAWN_END = PAWN_START;
	
	public static final int[][] KNIGHT_END = KNIGHT_START;
	
	public static final int[][] BISHOP_END = BISHOP_START;
	
	public static final int[][] ROOK_END = ROOK_START;
	
	public static final int[][] QUEEN_END = QUEEN_START;
}
