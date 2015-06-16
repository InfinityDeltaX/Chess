public enum PieceType {
	PAWN(new int[0][0], Values.POINT_VALUE_PAWN, PieceSquareTables.PAWN_START, PieceSquareTables.PAWN_END),
	BISHOP(new int[][]{{1,1}, {1,-1}, {-1,1}, {-1,-1}}, Values.POINT_VALUE_BISHOP, PieceSquareTables.BISHOP_START, PieceSquareTables.BISHOP_END),
	ROOK(new int[][]{{1,0}, {0,1}, {-1,0}, {0,-1}}, Values.POINT_VALUE_ROOK, PieceSquareTables.ROOK_START, PieceSquareTables.ROOK_END),
	KING(new int[][]{{1,1}, {1,-1}, {-1,1}, {-1,-1},{1,0}, {0,1}, {-1,0}, {0,-1}}, Values.POINT_VALUE_KING, PieceSquareTables.KING_START, PieceSquareTables.KING_START),
	QUEEN(new int[][]{{1,1}, {1,-1}, {-1,1}, {-1,-1},{1,0}, {0,1}, {-1,0}, {0,-1}}, Values.POINT_VALUE_QUEEN, PieceSquareTables.QUEEN_START, PieceSquareTables.QUEEN_END),
	NIGHT(new int[][]{{2,1}, {-2,1}, {2,-1}, {-2,-1}, {1,2}, {-1,2}, {1,-2}, {-1,-2}}, Values.POINT_VALUE_KNIGHT, PieceSquareTables.KNIGHT_START, PieceSquareTables.KNIGHT_END);
	
	public final int[][] endGameSquareValues;
	public final int[][] startGameSquareValues;
	public final RelativePosition[] moves;
	public final int value;
	
	private PieceType(int[][] relativePositions, int value, int[][] startGameSquareValues, int[][] endGameSquareValues){
		this.endGameSquareValues = endGameSquareValues;
		this.startGameSquareValues = startGameSquareValues;
		this.value = value;
		this.moves = new RelativePosition[relativePositions.length];
		for(int i = 0; i < relativePositions.length; i++){
			moves[i] = new RelativePosition(relativePositions[i][0], relativePositions[i][1]);
		}
	}
	
	public RelativePosition[] getMoves() {
		return moves;
	}

}
