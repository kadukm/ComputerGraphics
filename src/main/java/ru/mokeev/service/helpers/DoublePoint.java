package ru.mokeev.service.helpers;

public class DoublePoint {
    public Double x;
    public Double y;

    public DoublePoint(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Point(x=" + x + "y=" + y + ")";
    }
}
