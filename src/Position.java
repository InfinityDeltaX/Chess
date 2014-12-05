import java.util.ArrayList;


public class Position {

	private int row;
	private int file;

	public Position(int row, int file){
		this.row = row;
		this.file = file;
	}
	
	public Position(Position p){
		this(p.getRow(), p.getFile());
	}
	
	@Override public String toString(){
		return "[" + this.row + ", " + this.file + "]";
		
	}

	Position getPositionRelative(int x, int y){
		return new Position(row+x, file+y);
	}
	
	void changePositionRelative(int x, int y){
		this.row+=x;
		this.file+=y;
	}

	ArrayList<Position> getOtherPositionsOnDiagonalLtoR(){ //starts at bottom left and goes to right top
		ArrayList<Position> output = new ArrayList<Position>();
		int startX = this.getRow();
		int startY = this.getFile();
		
		Position current = new Position(startX, startY);
		
		while(current.doesExistOnBoard()){ //
			current.changePositionRelative(1, -1);
		} //when done, current will be at the farthest down-right position 
		
		current.changePositionRelative(-1, 1); //go back 1 unit.
		
		while(current.doesExistOnBoard()){ //go towards the top left corner, adding positions as you go. 
			
			Position _current = new Position(current);
			current.changePositionRelative(-1,  1);
			output.add(_current);
		}
		
		return output;
	}

	ArrayList<Position> getOtherPositionsOnDiagonalRtoL(){ //starts at bottom left and goes to right top
		ArrayList<Position> output = new ArrayList<Position>();
		int startX = this.getRow();
		int startY = this.getFile();
		
		Position current = new Position(startX, startY);
		
		while(current.doesExistOnBoard()){ //
			current.changePositionRelative(-1, -1);
		} //when done, current will be at the farthest down-right position 
		
		current.changePositionRelative(1, 1); //go back 1 unit.
		
		while(current.doesExistOnBoard()){ //go towards the top left corner, adding positions as you go. 
			
			Position _current = new Position(current);
			current.changePositionRelative(1,  1);
			
			if()output.add(_current); //check so that we don't add the original position!
		}
		
		return output;
	}
	
	boolean doesExistOnBoard(){
		if((this.getFile() < 8 && this.getFile() >= 0) && this.getRow() < 8 && this.getRow() >= 0){
			return true;
		} else return false;
	}

	ArrayList<Position> getOtherPositionsOnVertical(Position p){
		
	}
	int getRow(){
		return this.row;
	}
	int getFile(){
		return this.file;
	}
}
