package ru.nsu.fit.lylova.model;

import java.util.ArrayDeque;


public class Snake {
    private final ArrayDeque<Point> pointsOfSnake = new ArrayDeque<>();
    private Direction direction;

    Snake(Direction direction) {
        this.direction = direction;
    }

    public void addPointToTale(Point point) {
        pointsOfSnake.add(point);
    }

    public void addPointToHead(Point point) {
        pointsOfSnake.addFirst(point);
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
        Point head = pointsOfSnake.removeFirst();
        Point pointAfterHead = pointsOfSnake.getFirst();
        pointsOfSnake.addFirst(head);

        if (!head.getNextPointInDirection(direction).equals(pointAfterHead)) {
            this.direction = direction;
        }
    }


    public Point getNextHeadPoint() {
        return this.getHeadPoint().getNextPointInDirection(direction);
    }

    public int getLength() {
        return pointsOfSnake.size();
    }

    public ArrayDeque<Point> getPointsOfSnake() {
        return pointsOfSnake;
    }

}
