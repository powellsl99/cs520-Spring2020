import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import model.RowBlockModel;
import model.RowGameModel;

import controller.RowGameController;


/**
 * An example test class, which merely shows how to write JUnit tests.
 */
public class TestExample {
    private RowGameModel gameModel;
    private RowGameController gameContoller;
    private RowGameController ticTacContoller;

    @Before
    public void setUp() {
    gameModel = new RowGameModel();
    gameContoller = new RowGameController("ThreeInARow");
    ticTacContoller = new RowGameController("TicTacToe");
    }

    @After
    public void tearDown() {
    gameModel = null;
    gameContoller = null;
    }

    @Test
    public void testNewGame() {
        assertEquals ("1", gameModel.player);
        assertEquals (9, gameModel.getMovesLeft());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNewBlockViolatesPrecondition() {
	RowBlockModel block = new RowBlockModel(null);
    }

    @Test
    public void testIllegalMoveThree() {
        boolean isLegal = gameContoller.getModel().blocksData[0][0].getIsLegalMove();
        assertFalse(isLegal);
    }

    @Test
    public void testLegalMoveThree() {
        boolean isLegal = gameContoller.getModel().blocksData[2][2].getIsLegalMove();
        assertTrue(isLegal);
        gameContoller.move(2,2);
        isLegal = gameContoller.getModel().blocksData[2][2].getIsLegalMove();
        assertFalse(isLegal);
    }

    @Test
    public void testWinThree() {
        gameContoller.move(2,2);
        gameContoller.move(2,1);
        gameContoller.move(1,2);
        gameContoller.move(2,0);
        gameContoller.move(0,2);
        assertEquals(gameContoller.getPlayerVictoryString("1"), gameContoller.getModel().getFinalResult());
    }

    @Test
    public void testTieThree() {
        gameContoller.move(2,0);
        gameContoller.move(2,2);
        gameContoller.move(2,1);
        gameContoller.move(1,0);
        gameContoller.move(1,2);
        gameContoller.move(1,1);
        gameContoller.move(0,0);
        gameContoller.move(0,2);
        gameContoller.move(0,1);
        assertEquals(gameContoller.getModel().getTieString(), gameContoller.getModel().getFinalResult());
    }

    @Test
    public void testResetThree() {
        gameContoller.move(2,0);
        gameContoller.move(2,2);
        gameContoller.move(2,1);
        gameContoller.resetGame();
        assertEquals(9, gameContoller.getModel().getMovesLeft());
    }
}
