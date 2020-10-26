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

import controller.RowGameController;

public class TicTacToeController extends RowGameController {
    @Override
    public void move(int row, int column) {
		gameModel.setMovesLeft(gameModel.getMovesLeft() - 1);
		String player = gameModel.player;
		int movesLeft = gameModel.getMovesLeft();
		String playerSymbol = getPlayerSymbol(player);
		String playerVictoryString = getPlayerVictoryString(player);
		String nextPlayer = getNextPlayer(player);

		
		gameModel.blocksData[row][column].setContents(playerSymbol);
		gameModel.blocksData[row][column].setIsLegalMove(false);
		gameModel.player = nextPlayer;
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
    
    @Override
    public void resetGame(){
		for (int row = 0; row < 3; row++) {
			for (int column = 0; column < 3; column++) {
				gameModel.blocksData[row][column].reset();
				// Enable the bottom row
				
				gameModel.blocksData[row][column].setIsLegalMove(true);
			}
		}
		gameModel.player = "1";
		gameModel.movesLeft = 9;
		gameModel.setFinalResult(null);

		gameView.update(gameModel);
	}
}