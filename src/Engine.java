import java.util.Scanner;


public class Engine {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
		if(in.nextLine().equals("test")){
			
		} else {
		UCIInterface theInterface = new UCIInterface();
		(new Thread(theInterface)).start();
		}
		//Game game = new Game(Values.SIDE_BLACK);
		//game.
		
	}

}
