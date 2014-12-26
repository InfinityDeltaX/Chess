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
			System.out.println(b.generateRandomBoard(4));
			System.out.println(b.evaluate());
			System.out.println(b.getAllPossibleMoves(Values.SIDE_BLACK).size());
			//System.out.println(Move.orderMoves(b.getAllPossibleMoves(Values.SIDE_WHITE), b));
			System.out.println("---");
			System.out.println(b.getAllPossibleMoves(Values.SIDE_BLACK));
			
			
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
