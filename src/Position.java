public class Position implements Comparable<Position>{

	private final int row;
	private final int file;
	
	public Position(int file, int row){
		this.row = row;
		this.file = file;
	}
	
	public Position(String input){ //for example, e4 or h8.
		this.row = Integer.parseInt("" + input.charAt(1)) - 1;
		this.file = input.charAt(0)-'a';
	}
	
	public String getMathCoords(){ //0-7
		return "[" + this.file + ", " + this.row + "]";
	}
	
	@Override 
	public String toString(){
		char file = (char) ((getFile() +(int) 'a'));
		return(""+file+(row+1));
	}

	public Position getPositionRelative(int x, int y){
		return new Position(file+x, row+y);
	}
	
	public boolean doesExistOnBoard(){
		return ((this.getFile() < 8 && this.getFile() >= 0) && (this.getRow() < 8 && this.getRow() >= 0));
	}

	public int getRow(){
		return this.row;
	}
	public int getFile(){
		return this.file;
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
		return ((this==obj) || (obj!=null && getClass() == obj.getClass() && file==((Position) obj).file && row==((Position) obj).file));
	}

	@Override
	public int compareTo(Position o) {
		if(this.getFile() == o.getFile()){
			return this.getRow()-o.getRow();
		} else {
			return this.getFile()-o.getFile();
		}
	}
	
}
