package ru.nsu.fit.lylova.model;

public class Point {
    private int x;
    private int y;

    Point() {
        x = 0;
        y = 0;
    }

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Point getNextPointInDirection(Direction direction) {
        Point result = new Point(x, y);
        switch (direction) {
            case LEFT:
                result.setX(result.getX() - 1);
                break;
            case RIGHT:
                result.setX(result.getX() + 1);
                break;
            case DOWN:
                result.setY(result.getY() + 1);
                break;
            case UP:
                result.setY(result.getY() - 1);
        }
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != Point.class) {
            return false;
        }
        Point objPoint = (Point) obj;
        return objPoint.x == x && objPoint.y == y;
    }
}
