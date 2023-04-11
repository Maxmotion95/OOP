package ru.nsu.fit.lylova.model;

import java.util.LinkedList;

public class Snake {
    private final LinkedList<Point> pointsOfSnake = new LinkedList<>();
    private Direction direction = Direction.LEFT;

    Snake() {
    }

    public void addPointToTale(Point point) {
        pointsOfSnake.add(point);
    }

    public boolean removePointFromTale() {
        if (pointsOfSnake.isEmpty()) {
            return false;
        }
        pointsOfSnake.removeLast();
        return true;
    }

    public boolean removePointFromHead() {
        if (pointsOfSnake.isEmpty()) {
            return false;
        }
        pointsOfSnake.removeFirst();
        return true;
    }

    public Point getHeadPoint() {
        return pointsOfSnake.getFirst();
    }

    public Point getTalePoint() {
        return pointsOfSnake.getLast();
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        if (direction != getOppositeDirection(this.direction)) {
            this.direction = direction;
        }
    }

    private Direction getOppositeDirection(Direction direction) {
        switch (direction) {
            case UP:
                return Direction.DOWN;
            case DOWN:
                return Direction.UP;
            case LEFT:
                return Direction.RIGHT;
            case RIGHT:
                return Direction.LEFT;
        }
        return null;
    }

    public enum Direction {
        LEFT,
        RIGHT,
        DOWN,
        UP
    }
}
