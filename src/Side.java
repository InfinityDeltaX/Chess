public enum Side {
	WHITE(1), BLACK(-1);

	int multiplier;

	Side(int m) {
		this.multiplier = m;
	}

	public static Side getOpposingSide(Side side) {
		if(side.equals(Side.BLACK)) return Side.WHITE;
		else if(side.equals(Side.WHITE)) return Side.BLACK;
		else return null;
	}
}