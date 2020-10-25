package controller;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;

import java.util.*;

import model.RowGameModel;
import view.RowGameGUI;

/**
 * Java implementation of the 3 in a row game, using the Swing framework.
 *
 * This quick-and-dirty implementation violates a number of software engineering
 * principles and needs a thorough overhaul to improve readability,
 * extensibility, and testability.
 */
public class RowGameController {
	public static final String GAME_END_NOWINNER = "Game ends in a draw";

	public RowGameModel gameModel;
	public RowGameGUI gameView;

	/**
	 * Creates a new game initializing the GUI.
	 */
	public RowGameController() {
		gameModel = new RowGameModel();
		gameView = new RowGameGUI(this);

		resetGame();
	}

	public RowGameModel getModel() {
		return this.gameModel;
	}

	public RowGameGUI getView() {
		return this.gameView;
	}

	public void startUp() {
		gameView.gui.setVisible(true);
	}

	public boolean checkColumn(int rowNumber,int columnNumber){
		String currentContents = gameModel.blocksData[rowNumber][columnNumber].getContents();
		switch(rowNumber) {
			case 0:
				return ((currentContents.equals(gameModel.blocksData[rowNumber+1][columnNumber].getContents())) 
					&& (currentContents.equals(gameModel.blocksData[rowNumber+2][columnNumber].getContents())));
			case 1:
				return ((currentContents.equals(gameModel.blocksData[rowNumber+1][columnNumber].getContents())) 
					&& (currentContents.equals(gameModel.blocksData[rowNumber-1][columnNumber].getContents())));
			case 2:
				return ((currentContents.equals(gameModel.blocksData[rowNumber-1][columnNumber].getContents())) 
					&& (currentContents.equals(gameModel.blocksData[rowNumber-2][columnNumber].getContents())));
			default:
				throw new IllegalStateException("Row Number Invalid");
		}
	}

	public boolean checkRow(int rowNumber, int columnNumber){
		String currentContents = gameModel.blocksData[rowNumber][columnNumber].getContents();
		switch(columnNumber) {
			case 0:
				return ((currentContents.equals(gameModel.blocksData[rowNumber][columnNumber+1].getContents())) 
					&& (currentContents.equals(gameModel.blocksData[rowNumber][columnNumber+2].getContents())));
			case 1:
				return ((currentContents.equals(gameModel.blocksData[rowNumber][columnNumber+1].getContents())) 
					&& (currentContents.equals(gameModel.blocksData[rowNumber][columnNumber-1].getContents())));
			case 2:
				return ((currentContents.equals(gameModel.blocksData[rowNumber][columnNumber-1].getContents())) 
					&& (currentContents.equals(gameModel.blocksData[rowNumber][columnNumber-2].getContents())));
			default:
				throw new IllegalStateException("Column Number Invalid");
		}
	}

	public boolean checkDiagonal(int rowNumber, int columnNumber){
		String currentContents = gameModel.blocksData[rowNumber][columnNumber].getContents();
		if(rowNumber != columnNumber){
			return false;
		}
		switch(Integer.toString(rowNumber) + "_" + Integer.toString(columnNumber)) {
			case "0_0":
				return ((currentContents.equals(gameModel.blocksData[rowNumber+1][columnNumber+1].getContents())) 
					&& (currentContents.equals(gameModel.blocksData[rowNumber+2][columnNumber+2].getContents())));
			case "1_1":
				return ((currentContents.equals(gameModel.blocksData[rowNumber+1][columnNumber+1].getContents())) 
					&& (currentContents.equals(gameModel.blocksData[rowNumber-1][columnNumber-1].getContents())));
			case "2_2":
				return ((currentContents.equals(gameModel.blocksData[rowNumber-1][columnNumber-1].getContents())) 
					&& (currentContents.equals(gameModel.blocksData[rowNumber-2][columnNumber-2].getContents())));
			default:
				throw new IllegalStateException("Column Number or Row Number Invalid");
		}
	}

	public boolean checkReverseDiagonal(int rowNumber, int columnNumber){
		String currentContents = gameModel.blocksData[rowNumber][columnNumber].getContents();
		
		switch(Integer.toString(rowNumber) + "_" + Integer.toString(columnNumber)) {
			case "0_2":
				return ((currentContents.equals(gameModel.blocksData[1][1].getContents())) 
					&& (currentContents.equals(gameModel.blocksData[2][0].getContents())));
			case "1_1":
				return ((currentContents.equals(gameModel.blocksData[2][0].getContents())) 
					&& (currentContents.equals(gameModel.blocksData[0][2].getContents())));
			case "2_0":
				return ((currentContents.equals(gameModel.blocksData[1][1].getContents())) 
					&& (currentContents.equals(gameModel.blocksData[0][2].getContents())));
			default:
				return false;
		}
	}

	public boolean isWin(int rowNumber, int columnNumber) {
		return (checkRow(rowNumber, columnNumber) || checkColumn(rowNumber, columnNumber) || checkDiagonal(rowNumber, columnNumber) || checkReverseDiagonal(rowNumber, columnNumber));
	}

	public String getPlayerSymbol(String player) {
		switch(player) {
			case "1":
				return "X";
			case "2":
				return "O";
			default:
				throw new IllegalStateException("Player Number Invalid");
		}
	}

	public String getPlayerVictoryString(String player) {
		switch(player) {
			case "1":
				return "Player 1 wins!";
			case "2":
				return "Player 2 wins!";
			default:
				throw new IllegalStateException("Player Number Invalid");
		}
	}

	public String getNextPlayer(String player) {
		switch(player) {
			case "1":
				return "2";
			case "2":
				return "1";
			default:
				throw new IllegalStateException("Player Number Invalid");
		}
	}

	/**
	 * Moves the current player into the given block.
	 *
	 * @param block
	 *            The block to be moved to by the current player
	 */
	public void move(int row, int column) {
		gameModel.movesLeft = gameModel.movesLeft - 1;
		String player = gameModel.player;
		int movesLeft = gameModel.movesLeft;
		String playerSymbol = getPlayerSymbol(player);
		String playerVictoryString = getPlayerVictoryString(player);
		String nextPlayer = getNextPlayer(player);

		
		gameModel.blocksData[row][column].setContents(playerSymbol);
		gameModel.blocksData[row][column].setIsLegalMove(false);
		gameModel.player = nextPlayer;
		if (row>0){
			gameModel.blocksData[row-1][column].setIsLegalMove(true);
		}
		if (movesLeft < 7) {
			if(isWin(row, column)){
				gameModel.setFinalResult(playerVictoryString);
				endGame();
			} else if (movesLeft == 0) {
				gameModel.setFinalResult(GAME_END_NOWINNER);
			}
		}

		gameView.update(gameModel);
	}

	/**
	 * Ends the game disallowing further player turns.
	 */
	public void endGame() {
		for (int row = 0; row < 3; row++) {
			for (int column = 0; column < 3; column++) {
				this.gameModel.blocksData[row][column].setIsLegalMove(false);
			}
		}

		gameView.update(gameModel);
	}

	/**
	 * Resets the game to be able to start playing again.
	 */
	public void resetGame() {
		for (int row = 0; row < 3; row++) {
			for (int column = 0; column < 3; column++) {
				gameModel.blocksData[row][column].reset();
				// Enable the bottom row
				gameModel.blocksData[row][column].setIsLegalMove(row == 2);
			}
		}
		gameModel.player = "1";
		gameModel.movesLeft = 9;
		gameModel.setFinalResult(null);

		gameView.update(gameModel);
	}
}
