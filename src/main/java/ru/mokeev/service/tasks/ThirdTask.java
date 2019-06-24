package ru.mokeev.service.tasks;

import ru.mokeev.service.helpers.Point;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.OptionalInt;

public class ThirdTask extends JPanel {
    List<Point> points;

    private int minX, minY, maxX, maxY;


    private Graphics g;

    public ThirdTask(Point... points) {
        this.points = Arrays.asList(points);
        checkPolygon();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        this.g = g;

//        draw(0, 0);
    }
    private void drawCircles() {
        maxX = points.stream().max(Comparator.comparing(Point::getX)).get().getX();
        minX = points.stream().min(Comparator.comparing(Point::getX)).get().getX();

        maxY = points.stream().max(Comparator.comparing(Point::getY)).get().getY();
        minY = points.stream().min(Comparator.comparing(Point::getY)).get().getY();



    }

    private void checkPointsIsInside() {

    }

    private void checkPolygon() {
        if (points.size() < 3) {
            throw new IllegalArgumentException("Wrong points");
        }

        int minus = -1;
        int plus = 1;

        int[] znaki = new int[points.size()];

        for (int i = 0; i < points.size(); i++) {
            int k;
            if (i == points.size() - 1) {
                k = 0;
            } else {
                k = i + 1;
            }

            Point a = points.get(i);
            Point b = points.get(k);

            if (a.getY() != b.getY() && a.getX() != b.getX()) {
                znaki[i] = a.getX() * b.getY() - a.getY() * b.getX() < 0 ? minus : plus;
            }
        }
        OptionalInt anyZnak = Arrays.stream(znaki).filter(z -> z != 0).findAny();
        if (!anyZnak.isPresent()) {
            throw new IllegalArgumentException("Многоугольник не выпуклый");
        }

        if (Arrays.stream(znaki).filter(z -> z != 0).anyMatch(z -> z != anyZnak.getAsInt())) {
            throw new IllegalArgumentException("Многоугольник не выпуклый");
        }
    }
}
