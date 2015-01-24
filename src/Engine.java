import java.util.Scanner;


public class Engine {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
		String first = in.nextLine();
		if(first.equals("test")){
			Game theGame = new Game(Values.SIDE_BLACK);
			
			System.out.println(Values.ACCEPTABLE_TIME_MIN);
			String input = in.nextLine();
			Board b = new Board();
			b.setToFenString(input);
			//b.testSpeed(6, 5, 20, 10);
			System.out.println(b.evaluate());
			System.out.println(b.getAllPossibleMoves(Values.SIDE_BLACK).size());
			//System.out.println(Move.orderMoves(b.getAllPossibleMoves(Values.SIDE_WHITE), b));
			System.out.println("---");
			System.out.println(b.getAllPossibleMoves(Values.SIDE_BLACK));
			System.out.println(b.toString());
			
			for(Move m : b.getAllPossibleMoves(Values.SIDE_BLACK)){
				System.out.println(m.getToMoveTo());
			}
			
			b.makeMove(new Move(in.nextLine(), b));
			System.out.println(b.toString());
			
			theGame.minimax(Values.SIDE_BLACK, 1, b, true);
			
			System.exit(0);
			
			theGame.main(null);
			
		} else if(first.equals("mrpoles")){
			System.out.println("Now in text-based interface mode.");
			Board b = new Board();
			
			System.out.println("Please enter a FEN string to resume a game, or type 'new' to begin a new game.");
			String possibleFEN = in.nextLine();
			if(possibleFEN.toLowerCase().trim().equals("new")){
				b.setToDefaultBoard();
			} else {
				b.setToFenString(possibleFEN);
			}
			
			//choose sides
			Game theGame = null;
			System.out.println("Please enter the side you wish to be: ");
			if(in.nextLine().toLowerCase().trim().equals("white")){
				theGame = new Game(Values.SIDE_BLACK); //takes user side, not computer side.
				theGame.setBoard(b);
			} else {
				theGame = new Game(Values.SIDE_WHITE);
				b.makeMove(theGame.getComputerMove(b)); //the user has to move first. Let them make a move, then start the game.
				theGame.setBoard(b);
			}
			
			theGame.play(); //starts with a user move.
			//theGame.main(null);
			
			while(true){
				System.out.println("Current board state:");
				System.out.println(b);
				System.out.println("Please enter a move. ");
				//theGame.theBoard.makeMove();
			}
		} else {
		UCIInterface theInterface = new UCIInterface();
		(new Thread(theInterface)).start();
		UCIInterface.routeCommand(first);
		}
	}

}
