import java.util.Arrays;

public class FenString {
	public static Board getBoard(String input){
		Board output = new Board();
		String[] split = input.split("/"); //divides each row.
		split = Arrays.copyOfRange(split, 0, 8);

		for(int i = split.length-1; i >= 0; i--){ //rows
			int currentXPositionOnBoard = 0; //for example, if the first character we encounter is 5, this will go to 4, while j will iterate to 1.
			for (int j = 0; j < split[7-i].length(); j++) { //loop through each character. Columns. 

				char currentChar = split[7-i].charAt(j);
				Position currentPosition = new Position(currentXPositionOnBoard, i);

				if(currentChar <= '9' && currentChar >= '0'){ //if currentChar is an int, jump that number of spots.
					currentXPositionOnBoard+= (currentChar-'0'); //if we see one, we want to move 1 square. 

				} else { //currentChar is not an int, and is therefore a character representing a piece.
					output.setPieceAtPosition(currentPosition, Piece.getPieceFromLetter(currentChar, currentPosition));
					currentXPositionOnBoard++;
				}
			}
		}
		return output;
	}
	
	public static String FENString(Board input, Side activeSide){
		char castling = '-';
		char enPassant = '-';
		int halfMoveClock = 0;
		int fullMoveClock = 0;

		StringBuilder sb = new StringBuilder();
		for(int i = 7; i >= 0; i--){
			for(int j = 0; j < 8; j++){
				Position current = new Position(j, i);
				if(input.getPieceAtPosition(current) == null)sb.append(' ');
				else if(input.getPieceAtPosition(current).getSide() == Side.WHITE){
					sb.append(input.getPieceAtPosition(current).getTypeLetter());
				} else sb.append(Character.toLowerCase(input.getPieceAtPosition(current).getTypeLetter()));
			}
			sb.append("/");
		}
		StringBuilder sb2 = new StringBuilder(replaceIsWithNums(sb.toString()));
		sb2.append(castling + " " + enPassant + " " + halfMoveClock + " " + fullMoveClock);
		return sb2.toString();
	}

	private static String replaceIsWithNums(String input){ //fen helper. Replaces rows of unoccupied cells with a number.
		int count = 0;
		boolean lastWasI = false;

		StringBuilder sb = new StringBuilder(input);

		for(int i = 0; i < sb.length(); i++){ //iterate over input string
			char current = sb.charAt(i);
			//System.out.println("Looking at: " + current);
			if(current == ' '){
				count++;
				lastWasI = true;
			} else if(lastWasI){ //breaking a streak
				sb.insert(i, (char)((int)'0' + count)); //insert the number of consecutive Is into the String.
				count = 0;
				lastWasI = false;
			}
		}
		//sb.repl
		String output = sb.toString();
		output = output.replace(" ", ""); //delete all remaining is.
		return output;
	}
}
