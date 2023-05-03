package ru.nsu.fit.lylova.model;

import org.junit.jupiter.api.Test;
import ru.nsu.fit.lylova.javafxsnake.cell.CellType;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void getConnectedCellSides() {
        Game game = new Game(3, 4, 1, 0, 0, 12);

        assertSame(ConnectedCellSides.LEFT, game.getConnectedCellSides(1, 0));

        game.setSnakeDirection(Direction.DOWN);
        game.proceedGame();
        assertSame(ConnectedCellSides.UP, game.getConnectedCellSides(1, 1));

        game.setSnakeDirection(Direction.LEFT);
        game.proceedGame();
        assertSame(ConnectedCellSides.RIGHT, game.getConnectedCellSides(0, 1));

        game.setSnakeDirection(Direction.DOWN);
        game.proceedGame();
        game.proceedGame();
        assertSame(ConnectedCellSides.UP, game.getConnectedCellSides(0, 3));

        game.setSnakeDirection(Direction.RIGHT);
        game.proceedGame();
        game.proceedGame();

        game.setSnakeDirection(Direction.UP);
        game.proceedGame();
        assertSame(ConnectedCellSides.DOWN, game.getConnectedCellSides(2, 2));


        assertEquals(7, game.getScore());
        assertFalse(game.getIsEndOfGame());

        assertSame(ConnectedCellSides.RIGHT, game.getConnectedCellSides(0, 0));
        assertSame(CellType.TALE, game.getCellType(0, 0));
        assertSame(ConnectedCellSides.DOWN_LEFT, game.getConnectedCellSides(1, 0));
        assertSame(CellType.ANGULAR_BODY, game.getCellType(1, 0));
        assertSame(ConnectedCellSides.UP_LEFT, game.getConnectedCellSides(1, 1));
        assertSame(CellType.ANGULAR_BODY, game.getCellType(1, 1));
        assertSame(ConnectedCellSides.DOWN_RIGHT, game.getConnectedCellSides(0, 1));
        assertSame(CellType.ANGULAR_BODY, game.getCellType(0, 1));
        assertSame(ConnectedCellSides.UP_DOWN, game.getConnectedCellSides(0, 2));
        assertSame(CellType.STRAIGHT_BODY, game.getCellType(0, 2));
        assertSame(ConnectedCellSides.UP_RIGHT, game.getConnectedCellSides(0, 3));
        assertSame(CellType.ANGULAR_BODY, game.getCellType(0, 3));
        assertSame(ConnectedCellSides.RIGHT_LEFT, game.getConnectedCellSides(1, 3));
        assertSame(CellType.STRAIGHT_BODY, game.getCellType(1, 3));
        assertSame(ConnectedCellSides.UP_LEFT, game.getConnectedCellSides(2, 3));
        assertSame(CellType.ANGULAR_BODY, game.getCellType(2, 3));
        assertSame(ConnectedCellSides.DOWN, game.getConnectedCellSides(2, 2));
        assertSame(CellType.HEAD, game.getCellType(2, 2));

        game.setSnakeDirection(Direction.RIGHT);
        game.proceedGame();
        assertTrue(game.getIsEndOfGame());

        game = new Game(2, 2, 1, 0, 2, 0);
        assertFalse(game.getIsEndOfGame());
        game.setSnakeDirection(Direction.DOWN);
        game.proceedGame();
        assertTrue(game.getIsEndOfGame());
    }

    @Test
    void setSnakeDirection() {
        Game game = new Game(10, 10, 3, 3, 0, 0);
        game.setSnakeDirection(Direction.RIGHT);
        assertSame(Direction.RIGHT, game.getSnakeDirection());
        game.setSnakeDirection(Direction.UP);
        assertSame(Direction.UP, game.getSnakeDirection());
        game.setSnakeDirection(Direction.DOWN);
        assertSame(Direction.DOWN, game.getSnakeDirection());
        game.setSnakeDirection(Direction.LEFT);
        assertNotSame(Direction.LEFT, game.getSnakeDirection());

        game.proceedGame();

        game.setSnakeDirection(Direction.RIGHT);
        assertSame(Direction.RIGHT, game.getSnakeDirection());
        game.setSnakeDirection(Direction.UP);
        assertNotSame(Direction.UP, game.getSnakeDirection());
        game.setSnakeDirection(Direction.DOWN);
        assertSame(Direction.DOWN, game.getSnakeDirection());
        game.setSnakeDirection(Direction.LEFT);
        assertSame(Direction.LEFT, game.getSnakeDirection());
    }
}