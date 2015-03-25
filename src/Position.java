
public class Position {

	private int row;
	private int file;
	
	public Position(int file, int row){
		this.row = row;
		this.file = file;
	}
	
	public Position(int file, int row, boolean hasOpposingPiece){
		this(file, row);
	}
	
	public Position(String input){ //for example, e4 or h8.
		this.row = Integer.parseInt("" + input.charAt(1)) - 1;
		this.file = input.charAt(0)-'a';
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + file;
		result = prime * result + row;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		if (file != other.file)
			return false;
		if (row != other.row)
			return false;
		return true;
	}
	
}
