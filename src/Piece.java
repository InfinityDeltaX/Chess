import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public abstract class Piece implements PieceGroup{
	boolean hasBeenPromoted;
	int file;
	int row;
	Side side;
	//int side;
	//int type; //int 1 through 10
	int singleInt;
	Position myPosition;
	
	protected Piece(){};
	
	public static final ArrayList<Class<? extends Piece>> pieceTypes = new ArrayList<Class<? extends Piece>>();
	static{
		pieceTypes.add(Pawn.class);
		pieceTypes.add(Bishop.class);
		pieceTypes.add(King.class);
		pieceTypes.add(Queen.class);
		pieceTypes.add(Rook.class);
		pieceTypes.add(Knight.class);
	};
	
	public Piece(Position p, Side side){
		
		this.side = side;
		this.myPosition = new Position(p);
		hasBeenPromoted = false;
	}
	
	public abstract Piece copy();
	
	public abstract ArrayList<Position> getPossibleMoves(Board b);
	
	public Piece(Piece piece) {
		this(piece.getPosition(), piece.getSide());
	}

	@Override
	public String toString(){
		return (this.getClass().getName() + " on position " + this.getPosition() + " [Side: " +this.getSide() + "]");
	}
	
	public static String getTypeName(int type){
		if(type == Values.PAWN){
			return "Pawn";
		} else if(type == Values.BISHOP){
			return "Bishop";
		} else if(type == Values.KING){
			return "King";
		} else if(type == Values.QUEEN){
			return "Queen";
		} else if(type == Values.ROOK){
			return "Rook";
		} else if(type == Values.KNIGHT){
			return "Knight";
		} else return "Invalid";
	}
	
	public char getTypeLetter(){
		if(!getClass().equals(Knight.class)){
			return getClass().getName().charAt(0);
		} else return 'N'; 
	}
	
	public static Point getDifference(Position a, Position b){
		return new Point(a.getFile()-b.getFile(), a.getRow()-b.getRow());
	}
	
	public static Class<? extends Piece> getPieceClass(char input){ //get the first letter of a type from it's number. Always returns capital!
		input = Character.toUpperCase(input);
		if(input == 'P'){
			return Pawn.class;
		} else if(input == 'B'){
			return Bishop.class;
		} else if(input == 'K'){
			return King.class;
		} else if(input == 'Q'){
			return Queen.class;
		} else if(input == 'R'){
			return Rook.class;
		} else if(input == 'N'){
			return Knight.class;
		} else {
			assert(false);
			return null;
		}
	}
	
	public int evaluateValue(Board b){
		int pieceValue = Values.pieceValues.get(this.getClass());
		int sideMultiplier = getSide().multiplier;
		int pieceSquareTableValue = Values.getPieceSquareValue(this, b.getGameState());
		
		int output = sideMultiplier*(pieceSquareTableValue+pieceValue);
		return output;
	}
	
	public static Piece getPieceFromLetter(char input, Position p){ //return a piece from an appropriately capitalized letter, such as those found in a fen string. White = capital, Black = lower case.
		Side s;
		
		if(Character.isUpperCase(input)) s = Side.WHITE;
		else s = Side.BLACK;
		
		Class<? extends Piece> type = getPieceClass(Character.toUpperCase(input));
		
		try {
			return type.newInstance().factory(p, s);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static Piece getRandomPiece(Position p, Side s){
		Random r = new Random();
		int i = r.nextInt(Piece.pieceTypes.size());
		try {
			return pieceTypes.get(i).newInstance().factory(p, s);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	//setters and getters
	public int getFile() {
		return myPosition.getFile();
		//return file;
	}

	public void setFile(int file) {
		this.myPosition.setFile(file);
		//this.file = file;
	}

	public int getRow() {
		return myPosition.getRow();
		//return row;
	}

	public void setRow(int row) {
		//this.row = row;
		myPosition.setRow(row);
	}

	public Side getSide() {
		return side;
	}

	public Position getPosition() {
		return new Position(myPosition);
	}

	public void setPosition(Position myPosition) {
		this.myPosition = myPosition;
	}

}
