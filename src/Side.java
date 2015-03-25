
public enum Side {
	WHITE(1),
	BLACK(-1);
	
	int multiplier;
	
	Side(int m){
		this.multiplier = m;
	}
}
