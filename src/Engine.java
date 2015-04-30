import java.util.Scanner;


public class Engine {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
		String first = in.nextLine();
		if(first.equals("test")){
			Game theGame = new Game(Side.BLACK);
			
			Board b = new Board();
			b.setToDefaultBoard();
			b.makeMove(new Move(b.getPieceAtPosition(new Position(0, 0)), new Position(4, 4)));
			System.out.println(b);
			
			Board.speedTest();
			
			//theGame.minimax(Side.BLACK, 1, b, true);
			
			System.exit(0);
			
			theGame.main(null);
			
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
				theGame = new Game(Side.WHITE);
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
