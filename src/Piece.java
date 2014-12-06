import java.util.ArrayList;


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
}
