import java.util.Scanner;


public class Engine {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
		if(in.nextLine().equals("test")){
			System.out.println("Now in testing mode.");
			Game theGame = new Game(Values.SIDE_BLACK);
			
			System.out.println(Values.ACCEPTABLE_TIME_MIN);
			String input = in.nextLine();
			Board b = new Board();
			b.setToFenString(input);
			//System.out.println(Values.getPieceSquareValue(new Piece(new Position("d1"), Values.SIDE_BLACK, Values.KING), Values.GAME_STATE_START));
			System.out.println(b.fastEvaluateMaterial());
			//System.out.println(b.getAllPossibleMoves(Values.SIDE_WHITE).size());
			
			//System.out.println(b.getPossibleMoves(b.getPieceAtPosition(new Position("e2"))));
			
			System.out.println(b.getAllPossibleMoves(Values.SIDE_BLACK).size());
			
			for(Move m : b.getAllPossibleMoves(Values.SIDE_BLACK)){
				System.out.println(m.getToMoveTo());
			}
			theGame.main(null);
			
		} else {
		UCIInterface theInterface = new UCIInterface();
		(new Thread(theInterface)).start();
		}
	}

}
