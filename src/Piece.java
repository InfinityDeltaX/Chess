import java.util.ArrayList;


public class Piece {
	int file;
	int row;
	int side;
	int type;
	int singleInt;
	Position myPosition;
	
	public Piece(Position p, int side, int type){
		
		this.side = side;
		this.type = type;
		this.myPosition = p;
	}
	
	static int getCorrespondingInt(Piece p){
		return p.type;
	}
	int getCorrespondingInt(){
		return this.type;
	}
	
	ArrayList<Position> getSquaresAttacked(){
		ArrayList<Position> output = new ArrayList<Position>();
		
		if(this.type%100 == Values.PAWN){
			output.add()
		}
	}
	
	ArrayList<Position> getSquaresCanMoveTo(){
		
	}
	
}
