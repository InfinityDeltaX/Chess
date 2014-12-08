import java.util.ArrayList;

import javax.swing.plaf.basic.BasicLookAndFeel;


public class Piece {
	int file;
	int row;
	int side;
	int type; //int 1 through 10
	int singleInt;
	Position myPosition;
	
	public Piece(Position p, int side, int type){
		
		this.side = side;
		this.type = type;
		this.myPosition = p;
	}
	
	@Override
	public String toString(){
		return (getTypeName(this.getType()) + " on position " + this.getPosition() + " [Side: " +this.getSide() + "]");
	}
	
	static Piece getCorrespondingPiece(Position p, int input){
		return new Piece(p, input/100, input%100);
	}
	
	boolean isEmpty(){
		return (side==0 && type == 0);
	}
	
	static int getCorrespondingInt(Piece p){
		return p.type;
	}
	int getCorrespondingInt(){
		return this.type;
	}
	
	static int getOpposingSide(int side){
		if(side == Values.SIDE_BLACK) return Values.SIDE_WHITE;
		else if(side == Values.SIDE_WHITE) return Values.SIDE_BLACK;
		else{
			assert(false);
			return -100;
		}
	}
	
	static String getTypeName(int type){
		if(type == 1){
			return "Pawn";
		} else if(type == 2){
			return "Bishop";
		} else if(type == 3){
			return "King";
		} else if(type == 4){
			return "Queen";
		} else if(type == 5){
			return "Rook";
		} else if(type == 6){
			return "Knight";
		} else return "Invalid";
	}
	
	//setters and getters
	public int getFile() {
		return file;
	}

	public void setFile(int file) {
		this.file = file;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getSide() {
		return side;
	}

	public int getType() {
		return type;
	}

	public Position getPosition() {
		return myPosition;
	}

	public void setPosition(Position myPosition) {
		this.myPosition = myPosition;
	}

}
