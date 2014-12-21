import java.util.ArrayList;


public class Position {

	private int row;
	private int file;

	public Position(int file, int row){
		this.row = row;
		this.file = file;
	}
	
	public Position(Position p){
		this(p.getFile(), p.getRow());
	}
	
	public String getMathCoords(){ //0-7
		return "[" + this.file + ", " + this.row + "]";
	}
	
	@Override public String toString(){
		char file;
		file = (char) ((getFile() +(int) 'a'));
		return(""+file+(row+1));
	}

	public Position getPositionRelative(int x, int y){
		return new Position(file+x, row+y);
	}
	
	public void changePositionRelative(int x, int y){
		this.file+=x;
		this.row+=y;
	}

	private ArrayList<Position> getOtherPositionsOnDiagonalLtoR(){ //starts at bottom left and goes to right top
		ArrayList<Position> output = new ArrayList<Position>();
		int startX = this.getFile();
		int startY = this.getRow();
		
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

	private ArrayList<Position> getOtherPositionsOnDiagonalRtoL(){ //starts at bottom left and goes to right top. Method: Go down/left until we hit an edge, then go back right/up the whole way.
		ArrayList<Position> output = new ArrayList<Position>();
		int startX = this.getFile();
		int startY = this.getRow();
		
		Position current = new Position(startX, startY);
		
		while(current.doesExistOnBoard()){ //
			current.changePositionRelative(-1, -1);
		} //when done, current will be at the farthest down-right position 
		
		current.changePositionRelative(1, 1); //go back 1 unit.
		
		while(current.doesExistOnBoard()){ //go towards the top left corner, adding positions as you go. 
			
			Position _current = new Position(current);
			current.changePositionRelative(1,  1);
			
			// TODO ^ check so that we don't add the original position!
		}
		
		return output;
	}
	
	public boolean doesExistOnBoard(){
		if((this.getFile() < 8 && this.getFile() >= 0) && (this.getRow() < 8 && this.getRow() >= 0)){
			return true;
		} else return false;
	}

	public int getRow(){
		return this.row;
	}
	public int getFile(){
		return this.file;
	}
	public void setRow(int row){
		this.row = row;
	}
	public void setFile(int file){
		this.file = file;
	}
}
