import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;


public class Board {

	private Map<Position, Piece> boardPosition;

	public Board(Board input){
		this.boardPosition = new HashMap<Position, Piece>(input.getBoardPosition());
	}
	public Board(){
		boardPosition = new HashMap<Position, Piece>(); //x, y coordinates. a8 == 0, 0; h1 == 7, 7; a1 == 7, 0; h8 == 0, 7
	}
	public Board(String startingPosFEN, ArrayList<Move> movesMade){
		this(startingPosFEN);
		for(Move current : movesMade){
			this.makeMove(current);
		}
	}
	
	public Board(String fenString){
		this(FenString.getBoard(fenString));
	}
	
	public String getFenString(){
		return FenString.FENString(this, Side.WHITE);
	}

	private Map<Position, Piece> getBoardPosition(){
		return Collections.unmodifiableMap(boardPosition);
	}

	public void setPieceAtPosition(Position positionToSet, Piece pieceToPlace){
		assert(pieceToPlace.getPosition().equals(positionToSet));
		boardPosition.put(positionToSet, pieceToPlace);
	}

	private void setPositionToEmpty(Position positionToSet){
		boardPosition.remove(positionToSet);
	}

	public void setToClearBoard(){
		boardPosition = new HashMap<Position, Piece>();
	}

	public boolean isPositionEmpty(Position input){
		return getPieceAtPosition(input) == null;
	}

	public void setToDefaultBoard(){
		this.setToFenString(Values.defaultBoardFenString);
	}
	
	private void setToFenString(String fenString){
		this.boardPosition = new HashMap<Position, Piece>(FenString.getBoard(fenString).boardPosition);
	}

	public Piece getPieceAtPosition(Position input){
		return boardPosition.get(input);
	}

	public HashSet<Piece> getArrayListofMyRealPieces(Side side){ //does not return empty spaces. Returns an ArrayList of pieces from the player specified in 'side'.
		HashSet<Piece> output = new HashSet<Piece>();

		boardPosition.values().stream().filter(derp -> derp.getSide() == side).forEach(output::add);
		return output;
	}

	public static long speedTest(){
		long start = System.currentTimeMillis();
		Board b = new Board();
		Game game = new Game(Side.WHITE);
		for(int i = 0; i < 1000000; i++){
			b = Board.generateRandomBoard(20);
			b.evaluate();
		}
		
		System.out.println("Evaluation score: " + (System.currentTimeMillis() - start)/1000000.0);
		start = System.currentTimeMillis();
		
		for(int i = 0; i < 5; i++){
			b = Board.generateRandomBoard(20);
			game.negaMax(b, 5, Side.WHITE);
		}
		
		System.out.println("NegaMax score: " + (System.currentTimeMillis() - start)/5.0);
		
		return System.currentTimeMillis() - start;
	}

	public ArrayList<Move> getAllPossibleMoves(Side side){
		ArrayList<Move> output = new ArrayList<Move>();
		HashSet<Piece> myPieces = getArrayListofMyRealPieces(side);
		myPieces.stream().forEach( //for each piece,
				piece -> piece.getPossibleMoves(this).stream()
				.map(pos -> new Move(piece, pos)).forEach(m -> output.add(m))); //get it's possible next locs, map them to moves, add them to output.
		return output;
	}

	public ArrayList<Board> getAllPossibleNextBoards(Side side){
		//iterate through getAllPossibleMoves and make the move.
		ArrayList<Board> output = new ArrayList<Board>();

		for(Move m : getAllPossibleMoves(side)){
			Board newBoard = new Board(this);
			newBoard.makeMove(m);
			output.add(newBoard);
		}
		return output;
	}

	private static Board generateRandomBoard(int numberOfPieces){
		Board output = new Board();
		Random r = new Random();
		while(output.getArrayListofMyRealPieces(Side.WHITE).size() + output.getArrayListofMyRealPieces(Side.BLACK).size() < numberOfPieces){
			Piece p = Piece.getRandomPiece(new Position(r.nextInt(7), r.nextInt(7)), r.nextInt(2) > 0 ? Side.WHITE : Side.BLACK);
			output.setPieceAtPosition(p.getPosition(), p);
		}
		return output;
	}

	public int evaluate(){
		return boardPosition.values().stream().mapToInt(p -> p.evaluateValue(this)).sum();
	}

	public int kingStatus(){ //returns zero if there are two kings, or -1 if white king gone, 1 if black king gone.
		boolean whiteKing = false; //do they exist
		boolean blackKing = false;
		for(Piece p : boardPosition.values()){
			if (p.getType() == PieceType.KING){
				if(p.getSide() == Side.WHITE){
					whiteKing = true;
				}
				if(p.getSide() == Side.BLACK){
					blackKing = true;
				}
			}
		}
		return Values.booleanCompare(blackKing, whiteKing);
	}

	public int getGameState(){
		return (boardPosition.size() > Values.END_GAME_THRESHOLD ? Values.GAME_STATE_START : Values.GAME_STATE_END);
	}

	public void makeMove(Move m){

		//check if the move is an en passant
		if(m.getPiece().getType() == PieceType.PAWN && this.isPositionEmpty(m.getDestination()) && (Values.getAbsolutePoint(Piece.getDifference(m.getDestination(), m.getOrigin())).equals(new Point(1, 1)))){
			//System.out.println("En Passant detected!");
			if(Piece.getDifference(m.getDestination(), m.getOrigin()).equals(new Point(1, 1))){
				setPositionToEmpty(m.getDestination().getPositionRelative(0, -1));
			}
			if(Piece.getDifference(m.getDestination(), m.getOrigin()).equals(new Point(-1, 1))){
				setPositionToEmpty(m.getDestination().getPositionRelative(0, -1));
			}
			if(Piece.getDifference(m.getDestination(), m.getOrigin()).equals(new Point(1, -1))){
				setPositionToEmpty(m.getDestination().getPositionRelative(0, 1));
			}
			if(Piece.getDifference(m.getDestination(), m.getOrigin()).equals(new Point(1, -1))){
				setPositionToEmpty(m.getDestination().getPositionRelative(0, 1));
			}
		}

		//check if the move is a promotion
		else if(m.getPiece().getType() == PieceType.PAWN && ((m.getPiece().getSide() == Side.WHITE && m.getDestination().getRow() == 7) || (m.getPiece().getSide() == Side.BLACK && m.getDestination().getRow() == 0))){
			//System.out.println("Promotion detected.");
			m = new Move(new Piece(PieceType.QUEEN, m.getOrigin(), m.getPiece().getSide()), m.getDestination());
		}

		//check if the move is a castle.
		else if(m.getPiece().getType() == PieceType.PAWN){
			//System.out.println("Castling detected!");
			//System.out.println(Piece.getDifference(m.getOriginalPosition(), m.getToMoveTo()));
			if(m.getPiece().getSide() == Side.WHITE && Piece.getDifference(m.getOrigin(), m.getDestination()).equals(new Point(-2, 0))){
				this.makeMove(new Move(this.getPieceAtPosition(new Position("h1")), new Position("f1")));
			} else if(m.getPiece().getSide() == Side.BLACK && Piece.getDifference(m.getOrigin(), m.getDestination()).equals(new Point(2, 0))){
				this.makeMove(new Move(this.getPieceAtPosition(new Position("a8")), new Position("c8")));
			}
		}

		//setPieceAtPosition(m.originalPosition, Values.EMPTY_SQUARE);
		setPositionToEmpty(m.getOrigin());
		Piece toSet = m.getPiece();
		toSet.setPosition(m.getDestination());
		m = new Move(toSet, m.getDestination());
		setPieceAtPosition(m.getDestination(), m.getPiece());
	}

	boolean isValidPlaceToMove(Position p, Side side){ //designed for knights and kings, this tests if one of the spots where they "can" move is A: unoccupied or B: has an opposing piece, but does NOT have a friendly piece. //side should the be the side of the moving piece. p is the destination position.
		return (p.doesExistOnBoard() && !isPositionEmpty(p) && getPieceAtPosition(p).getSide() != side); //using getPositionRelative because it doesn't actually modify the object.
		//This tests: is the new position on the board? and is the new position either enemy or unoccupied (not my own)?
	}

	public ArrayList<Position> getMovesAlongDirectionalAxisUntilInterrupted(Position current, RelativePosition r){ //x = 0, y = 1 will move the unit up until it hits an opposing piece, it's own piece, or the edge of the board. All of these positions will be returned.

		ArrayList<Position> output = new ArrayList<Position>();
		Position start = current;
		Side side = getPieceAtPosition(start).side;
		Piece temp = getPieceAtPosition(start);
		setPositionToEmpty(start);

		//we don't add the starting position, because moving to your original location is not valid.

		while(current.doesExistOnBoard() && isPositionEmpty(current)){ //while still valid
			current = current.getPositionRelative(r.getX(), r.getY());
			output.add(current);
		}

		if(!current.doesExistOnBoard()){ //we're off the board
			//move back onto the board.
			output.remove(output.size()-1); //get rid of the last one
		} else if(getPieceAtPosition(current).side == side){ //the piece that we ran into is our own
			//go back so we aren't on top of our piece.
			output.remove(output.size()-1); //get rid of the last one
		} else {} //we're on top of an opposing piece, and that is O.K.
		
		setPieceAtPosition(start, temp); //replace ourselves. 
		return output;
	}

	public void dumpBoard(){
		for(Piece p : boardPosition.values()){
			System.out.println(p);
		}
	}
	
	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append("===========\n");
		for(int i = 7; i >= 0; i--){
			for(int j = 0; j < 8; j++){
				Position current = new Position(j, i);
				if(this.getPieceAtPosition(current) == null)sb.append(' ');
				else if(this.getPieceAtPosition(current).getSide() == Side.WHITE){
					sb.append(this.getPieceAtPosition(current).getTypeLetter());
				} else sb.append(Character.toLowerCase(this.getPieceAtPosition(current).getTypeLetter()));
			}
			sb.append("\n");
		}
		sb.append("===========\n");
		return sb.toString();
	}
	
	Comparator<Piece> byPosition = new Comparator<Piece>(){
		@Override
		public int compare(Piece a, Piece b){
			return a.getPosition().compareTo(b.getPosition());
		}
	};
}
