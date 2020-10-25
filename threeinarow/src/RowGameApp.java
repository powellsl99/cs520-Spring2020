import controller.RowGameController;

public class RowGameApp {
	/**
	 * Starts a new game in the GUI.
	 */
	public static void main(String[] args) {
		System.out.println(args[0]);
		if(args.length == 0){
			throw new IllegalStateException("Please input which type of game to play");
		}
		if(!args[0].equals(RowGameController.TicTacToeString) && !args[0].equals(RowGameController.ThreeRowString)){
			throw new IllegalStateException("The game types are TicTacToe and ThreeInARow");
		}
		RowGameController game = new RowGameController(args[0]);
		game.startUp();
	}
}
