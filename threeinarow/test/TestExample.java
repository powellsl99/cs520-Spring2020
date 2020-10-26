import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import model.RowBlockModel;
import model.RowGameModel;

import controller.RowGameController;
import controller.ThreeInARowController;
import controller.TicTacToeController;


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
    gameContoller = new ThreeInARowController();
    ticTacContoller = new TicTacToeController();
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
    public void testViewThree() {
        assertNotNull(gameContoller.getView().gameStatusView);
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

    @Test
    public void testViewTicTac() {
        assertNotNull(ticTacContoller.getView().gameStatusView);
    }

    @Test
    public void testIllegalMoveTicTac() {
        ticTacContoller.move(2,0);
        boolean isLegal = ticTacContoller.getModel().blocksData[2][0].getIsLegalMove();
        assertFalse(isLegal);
    }

    @Test
    public void testLegalMoveTicTac() {
        boolean isLegal = ticTacContoller.getModel().blocksData[2][2].getIsLegalMove();
        assertTrue(isLegal);
        ticTacContoller.move(2,2);
        isLegal = ticTacContoller.getModel().blocksData[2][2].getIsLegalMove();
        assertFalse(isLegal);
    }

    @Test
    public void testWinTicTac() {
        ticTacContoller.move(2,2);
        ticTacContoller.move(2,1);
        ticTacContoller.move(1,2);
        ticTacContoller.move(2,0);
        ticTacContoller.move(0,2);
        assertEquals(ticTacContoller.getPlayerVictoryString("1"), ticTacContoller.getModel().getFinalResult());
    }

    @Test
    public void testTieTicTac() {
        ticTacContoller.move(2,0);
        ticTacContoller.move(2,2);
        ticTacContoller.move(2,1);
        ticTacContoller.move(1,0);
        ticTacContoller.move(1,2);
        ticTacContoller.move(1,1);
        ticTacContoller.move(0,0);
        ticTacContoller.move(0,2);
        ticTacContoller.move(0,1);
        assertEquals(ticTacContoller.getModel().getTieString(), ticTacContoller.getModel().getFinalResult());
    }

    @Test
    public void testResetTicTac() {
        ticTacContoller.move(2,0);
        ticTacContoller.move(2,2);
        ticTacContoller.move(2,1);
        ticTacContoller.resetGame();
        assertEquals(9, ticTacContoller.getModel().getMovesLeft());
    }
}
