import java.util.Scanner;


public class UCIInterface implements Runnable{

	public volatile Position lastPositionRecieved;
	
	
	@Override
	public void run() {
		Scanner in = new Scanner(System.in);
		// TODO Auto-generated method stub
		while(1==1){
			String input = in.nextLine();
			routeCommand(input);
			System.out.println("Recieved command!");
		}
	}
	
	
	public static void routeCommand(String input){
		String[] split = input.split(" ");
		if(input.equals("uci")){
			UCICommand();
		} else if(split[0].equals("debug")){
			debugCommand(split);
		} else if(split[0].equals("isready")){
			isReadyCommand();
		} else if(split[0].equals("position")){
			positionCommand(split);
		} else if(split[0].equals("go")){
			goCommand(split);
		} else if(split[0].equals("stop")){
			stopCommand();
		} else if(split[0].equals("quit")){
			quitCommand(split);
		} else {
			System.err.println("Command not recognized!");
		}
	}
	
	private static void UCICommand(){		
		tellGUI("id name CivProject");
		tellGUI("id author Robert Cunningham");
		tellGUI("uciok");
	}
	
	private static void quitCommand(String[] input){
		tellGUI("Quitting");
		System.exit(0);
	}
	
	private static void debugCommand(String[] split){
		System.err.println("Debugging not supported at this time.");
	}
	
	private static void isReadyCommand(){
		tellGUI("readyok");
	}
	
	private static void goCommand(String[] split){
		
	}
	
	private static void stopCommand(){
		
	}
	
	public static void sendCommand(){
		
	}
	
	private static void tellGUI(String toSend){
		System.out.println(toSend);
	}
	
	private static Board positionCommand(String[] split){
		Board output;
		
		if(!split[0].equals("position")) assert(false); // should have been sent a position command with the position attached.
		
		boolean isStartingPosition = split[1].equals("startpos");
		
		if(isStartingPosition){
			output = new Board(Values.defaultBoardFenString);
		} else {
			output = new Board(split[1]);
		}
				
		int movesStartingSplitIndex = isStartingPosition ? 2 : 7; // moves start at split[2] or split[7] depending on whether or not there is a fen string.
		
		for(int i = movesStartingSplitIndex; i < split.length; i++){
			output.makeMove(new Move(split[i], output));
		}
		return output;
	}

}
