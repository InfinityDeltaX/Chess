import java.util.Scanner;

public class Engine {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		String first = in.nextLine();
		if(first.equals("speedtest")){
			Board.speedTest();
		} else if(first.equals("text")){
			System.out.println("Now in text-based interface mode.");
			Board b = new Board();
			
			System.out.println("Please enter a FEN string to resume a game, or type 'new' to begin a new game.");
			String possibleFEN = in.nextLine();
			if(possibleFEN.toLowerCase().trim().equals("new")){
				b.setToDefaultBoard();
			} else {
				b = FenString.getBoard(possibleFEN);
			}
			
			//choose sides
			Game theGame = null;
			System.out.println("Please enter the side you wish to be: ");
			if(in.nextLine().toLowerCase().trim().equals("white")){
				theGame = new Game(Side.BLACK); //takes user side, not computer side.
				theGame.setBoard(b);
			} else {
				theGame = new Game(Side.WHITE, b);
				b.makeMove(theGame.getComputerMove(b)); //the user has to move first. Let them make a move, then start the game.
			}
			
			theGame.play(); //starts with a user move.
			
			while(true){
				System.out.println("Current board state:");
				System.out.println(b);
				System.out.println("Please enter a move. ");
			}
		} else {
		UCIInterface theInterface = new UCIInterface();
		(new Thread(theInterface)).start();
		UCIInterface.routeCommand(first);
		}
	}

}
