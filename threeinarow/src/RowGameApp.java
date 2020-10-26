import controller.ThreeInARowController;
import controller.TicTacToeController;
import controller.RowGameController;

public class RowGameApp {
	/**
	 * Starts a new game in the GUI.
	 */
	public static void main(String[] args) {
		RowGameController game;
		if(args.length == 0){
			throw new IllegalStateException("Please input which type of game to play");
		}
		switch(args[0]) {
			case RowGameController.TicTacToeString:
				game = new TicTacToeController();
				break;
			case RowGameController.ThreeRowString:
				game = new ThreeInARowController();
				break;
			default:
			    throw new IllegalStateException("The game types are TicTacToe and ThreeInARow");
		}
		
		game.startUp();
	}
}
