package controller;

import model.RowGameModel;

public interface RowGameRulesStrategy {
	public void resetGame();

	public void move(int row, int column);

	public boolean isWin(int rowNumber, int columnNumber);

	public boolean isTie();
}
