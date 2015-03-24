import java.awt.Point;
import java.util.ArrayList;

public abstract class Piece {
	boolean hasBeenPromoted;
	int file;
	int row;
	Side side;
	//int side;
	//int type; //int 1 through 10
	int singleInt;
	Position myPosition;
	
	public Piece(Position p, Side side){
		
		this.side = side;
		this.myPosition = new Position(p);
		hasBeenPromoted = false;
	}
	
	public abstract ArrayList<Position> getPossibleMoves(Board b);
	
	public Piece(Piece piece) {
		this(piece.getPosition(), piece.getSide());
	}

	@Override
	public String toString(){
		return (getTypeName(this.getType()) + " on position " + this.getPosition() + " [Side: " +this.getSide() + "]");
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
		String name = getTypeName(this.getType());
		if(!name.equals("Knight")){
			return name.charAt(0);
		} else return 'N'; 
	}
	
	public static Point getDifference(Position a, Position b){
		return new Point(a.getFile()-b.getFile(), a.getRow()-b.getRow());
	}
	
	public static int getTypeInt(char input){ //get the first letter of a type from it's number. Always returns capital!
		input = Character.toUpperCase(input);
		if(input == 'P'){
			return Values.PAWN;
		} else if(input == 'B'){
			return Values.BISHOP;
		} else if(input == 'K'){
			return Values.KING;
		} else if(input == 'Q'){
			return Values.QUEEN;
		} else if(input == 'R'){
			return Values.ROOK;
		} else if(input == 'N'){
			return Values.KNIGHT;
		} else {
			assert(false);
			return -1;
		}
	}
	
	public int evaluateValue(){
		
		//TODO
	}
	
	public static Piece getPieceFromLetter(char input, Position p){ //return a piece from an appropriately capitalized letter, such as those found in a fen string. White = capital, Black = lower case.
		int type = -1; //no side encoded.
		Side s;
		
		if(Character.isUpperCase(input)) s = Side.WHITE;
		else s = Side.BLACK;
		
		type = getTypeInt(Character.toUpperCase(input));
		return new Piece(p, s);
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

	public int getType() {
		return type;
	}

	public Position getPosition() {
		return new Position(myPosition);
	}

	public void setPosition(Position myPosition) {
		this.myPosition = myPosition;
	}

}
