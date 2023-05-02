package ru.nsu.fit.lylova.model;

import ru.nsu.fit.lylova.javafxsnake.cell.CellType;

import java.util.Random;

import static java.lang.Math.abs;

public class Game {
    private final CellType[][] field;
    private final Point[][] prevPoint;
    private final Point[][] nextPoint;
    Snake userSnake = new Snake(Direction.RIGHT);
    private boolean isEndOfGame = false;
    private int score = 0;
    private int fieldWidth;
    private int fieldHeight;

    public Game(int width, int height, int headX, int headY, int rockCount, int foodCount) {
        fieldWidth = width;
        fieldHeight = height;
        field = new CellType[width][height];
        prevPoint = new Point[width][height];
        nextPoint = new Point[width][height];

        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                field[i][j] = CellType.EMPTY;
            }
        }
        userSnake.addPointToTale(new Point(headX, headY));
        field[headX][headY] = CellType.HEAD;
        if (headX > 0) {
            field[headX - 1][headY] = CellType.TALE;
            userSnake.addPointToTale(new Point(headX - 1, headY));
        }
        for (int i = 0; i < rockCount; ++i) {
            Point p = generateNewFreePoint(headY);
            if (p == null) {
                isEndOfGame = true;
                return;
            }
            int x = p.getX();
            int y = p.getY();

            field[x][y] = CellType.ROCK;
        }
        for (int i = 0; i < foodCount; ++i) {
            Point p = generateNewFreePoint(headY);
            if (p == null) {
                isEndOfGame = true;
                return;
            }
            int x = p.getX();
            int y = p.getY();

            field[x][y] = CellType.FOOD;
        }

        updateNextAndPrevPointsOfSnake(userSnake);
    }

    private void updateNextAndPrevPointsOfSnake(Snake snake) {
        var pointsOfSnake = snake.getPointsOfSnake();
        Point prev = null;
        for (Point p : pointsOfSnake) {
            if (prev != null) {
                prevPoint[prev.getX()][prev.getY()] = p;
            }
            nextPoint[p.getX()][p.getY()] = prev;
            prev = p;
        }
    }

    public Point getNextSnakePoint(int x, int y) {
        return nextPoint[x][y];
    }

    public Point getPrevSnakePoint(int x, int y) {
        return prevPoint[x][y];
    }

    public void proceedGame() {
        Point newHead = userSnake.getNextHeadPoint();
        int x = newHead.getX();
        int y = newHead.getY();
        if (isPointOnField(newHead) && field[x][y] == CellType.FOOD) {
            ++score;
            Point p = generateNewFreePoint(-1);
            if (p == null) {
                isEndOfGame = true;
                return;
            }
            int xNewFood = p.getX();
            int yNewFood = p.getY();

            field[xNewFood][yNewFood] = CellType.FOOD;
        } else {
            Point tale = userSnake.getTalePoint();
            field[tale.getX()][tale.getY()] = CellType.EMPTY;
            userSnake.removePointFromTale();
            tale = userSnake.getTalePoint();
            field[tale.getX()][tale.getY()] = CellType.TALE;
        }
        updateSnakeHeadOnField();
        updateNextAndPrevPointsOfSnake(userSnake);
    }

    private boolean isPointOnField(Point point) {
        return point.getX() >= 0 && point.getX() < fieldWidth &&
                point.getY() >= 0 && point.getY() < fieldHeight;
    }

    private void updateSnakeHeadOnField() {
        Point head = userSnake.getHeadPoint();
        Point nextHead = userSnake.getNextHeadPoint();
        if (nextHead.getX() < 0 || nextHead.getX() >= fieldWidth ||
                nextHead.getY() < 0 || nextHead.getY() >= fieldHeight ||
                (field[nextHead.getX()][nextHead.getY()] != CellType.FOOD &&
                        field[nextHead.getX()][nextHead.getY()] != CellType.EMPTY)) {
            isEndOfGame = true;
            return;
        }
        if (userSnake.getLength() >= 2) {
            userSnake.removePointFromHead();
            Point pointAfterHead = userSnake.getHeadPoint();
            userSnake.addPointToHead(head);
            if (abs(nextHead.getX() - pointAfterHead.getX()) == 2 ||
                    abs(nextHead.getY() - pointAfterHead.getY()) == 2) {
                field[head.getX()][head.getY()] = CellType.STRAIGHT_BODY;
            } else {
                field[head.getX()][head.getY()] = CellType.ANGULAR_BODY;
            }
        }
        field[nextHead.getX()][nextHead.getY()] = CellType.HEAD;
        userSnake.addPointToHead(nextHead);
    }

    public void setSnakeDirection(Direction direction) {
        userSnake.setDirection(direction);
    }

    public CellType getCellType(int x, int y) {
        return field[x][y];
    }

    public boolean getIsEndOfGame() {
        return isEndOfGame;
    }

    private Point generateNewFreePoint(int bannedRow) {
        int count = 0;
        for (int i = 0; i < fieldWidth; ++i) {
            for (int j = 0; j < fieldHeight; ++j) {
                if (field[i][j] == CellType.EMPTY && j != bannedRow) {
                    ++count;
                }
            }
        }
        if (count == 0) {
            return null;
        }
        Random random = new Random();
        int pointId = random.nextInt(0, count);
        count = 0;
        for (int x = 0; x < fieldWidth; ++x) {
            for (int y = 0; y < fieldHeight; ++y) {
                if (field[x][y] == CellType.EMPTY && y != bannedRow) {
                    if (pointId == count) {
                        return new Point(x, y);
                    }
                    ++count;
                }
            }
        }
        return null;
    }

    public void printField() {
        for (int j = 0; j < fieldHeight; ++j) {
            for (int i = 0; i < fieldWidth; ++i) {
                char c = 'n';
                switch (field[i][j]) {
                    case EMPTY -> c = ' ';
                    case STRAIGHT_BODY -> c = 's';
                    case FOOD -> c = 'f';
                    case ROCK -> c = '#';
                    case TALE -> c = 't';
                    case HEAD -> c = 'h';
                    case ANGULAR_BODY -> c = 'a';
                }
                System.out.print(c);
            }
            System.out.print('\n');
        }
    }

    public int getScore() {
        return score;
    }
}
