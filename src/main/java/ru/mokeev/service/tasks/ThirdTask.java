package ru.mokeev.service.tasks;

import ru.mokeev.service.helpers.CircleResult;
import ru.mokeev.service.helpers.Line;
import ru.mokeev.service.helpers.Point;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public class ThirdTask extends JPanel {
    List<Point> points;
    List<Line> lines = new ArrayList<>();

    private int minX, minY, maxX, maxY;


    private Graphics g;

    public ThirdTask(Point... points) {
        this.points = Arrays.asList(points);


        for (int i = 0; i < points.length - 1; i++) {
            Point currentPoint = points[i];
            Point nextPoint = points[i + 1];
            this.lines.add(new Line(currentPoint, nextPoint));
        }
        this.lines.add(new Line(points[points.length - 1], points[0]));
//        checkPolygon();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        this.g = g;
        resolveAll();
    }

    private void resolveAll() {
        lines.forEach(this::drawLine);

        maxX = points.stream().max(Comparator.comparing(p -> p.x)).get().x;
        minX = points.stream().min(Comparator.comparing(p -> p.x)).get().x;

        maxY = points.stream().max(Comparator.comparing(p -> p.y)).get().y;
        minY = points.stream().min(Comparator.comparing(p -> p.y)).get().y;

        System.out.println("MAXIMUM WIDTH: " + maxX);
        System.out.println("MINIMUM WIDTH: " + minX);

        System.out.println("MAXIMUM HEIGHT: " + maxY);
        System.out.println("MINIMUM HEIGHT: " + minY);

        CircleResult largeCircle = drawLargeCircle();
        drawSmallerCircle(largeCircle);
    }

    private CircleResult drawLargeCircle() {
        CircleResult maxCircle = new CircleResult(points.get(0), 10);
        int largestDistance = 0;

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                Point currentPoint = new Point(x, y);
                if (isPointInside(currentPoint)) {
                    List<Integer> distances = lines.stream().map(l -> distBetweenPointAndLine(currentPoint, l)).collect(Collectors.toList());
                    int minDistToLine = Collections.min(distances);
                    if (minDistToLine >= largestDistance) {
                        largestDistance = minDistToLine;
                        maxCircle = new CircleResult(currentPoint, minDistToLine);
                    }
                }
            }
        }
        System.out.println(maxCircle);

        g.setColor(Color.RED);
        g.drawOval(maxCircle.getPoint().x, maxCircle.getPoint().y, 1, 1);
        g.setColor(Color.BLACK);
        drawCircles(maxCircle);
        return maxCircle;
    }

    private void drawSmallerCircle(CircleResult largeCircle) {
        CircleResult maxCircle = new CircleResult(points.get(0), 10);
        int largestDistance = 0;

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                Point currentPoint = new Point(x, y);
                if (isPointInside(currentPoint)) {
                    List<Integer> distances = lines.stream().map(l -> distBetweenPointAndLine(currentPoint, l)).collect(Collectors.toList());
                    int minDistToLine = Collections.min(distances);
                    CircleResult maybeSmallCircle = new CircleResult(currentPoint, minDistToLine);
                    if (minDistToLine >= largestDistance && !isCirclesIntersected(largeCircle, maybeSmallCircle)) {
                        largestDistance = minDistToLine;
                        maxCircle = maybeSmallCircle;
                    }
                }
            }
        }
        System.out.println(maxCircle);

        g.setColor(Color.RED);
        g.drawOval(maxCircle.getPoint().x, maxCircle.getPoint().y, 1, 1);
        g.setColor(Color.BLACK);
        drawCircles(maxCircle);
    }

    private boolean isCirclesIntersected(CircleResult firstCircle, CircleResult secondCircle) {
        return Math.hypot(firstCircle.getPoint().x - secondCircle.getPoint().x, firstCircle.getPoint().y - secondCircle.getPoint().y) <= (firstCircle.getRadius() + secondCircle.getRadius());
    }

    private void drawCircles(CircleResult circleResult) {
        int diameter = circleResult.getRadius() * 2;
        g.drawOval(circleResult.getPoint().x - circleResult.getRadius(), circleResult.getPoint().y - circleResult.getRadius(), diameter, diameter);
    }


    public int distBetweenPointAndLine(Point point, Line line) {

        float A = point.x - line.start.x; // position of point rel one end of line
        float B = point.y - line.start.y;
        float C = line.end.x - line.start.x; // vector along line
        float D = line.end.y - line.start.y;
        float E = -D; // orthogonal vector
        float F = C;

        float dot = A * E + B * F;
        float len_sq = E * E + F * F;

        return Double.valueOf(Math.abs(dot) / Math.sqrt(len_sq)).intValue();
    }

    private boolean isPointInside(Point current) {
        Point[] array = points.toArray(new Point[0]);
        int i;
        int j;
        boolean result = false;
        for (i = 0, j = array.length - 1; i < array.length; j = i++) {
            if ((array[i].y > current.y) != (array[j].y > current.y) &&
                    (current.x < (array[j].x - array[i].x) * (current.y - array[i].y) / (array[j].y - array[i].y) + array[i].x)) {
                result = !result;
            }
        }
        return result;
    }

    private void drawLine(Line line) {
        drawLineBetweenPoints(line.start, line.end);
    }

    private void drawLineBetweenPoints(Point p1, Point p2) {
        drawLine(p1.x, p1.y, p2.x, p2.y);
    }

    private void drawLine(int x1, int y1, int x2, int y2) {
        g.drawLine(x1, y1, x2, y2);
    }

    private void checkPolygon() {
        if (points.size() < 3) {
            throw new IllegalArgumentException("Wrong points");
        }

        int minus = -1;
        int plus = 1;

        int[] signs = new int[points.size()];

        for (int i = 0; i < points.size(); i++) {
            int k;
            if (i == points.size() - 1) {
                k = 0;
            } else {
                k = i + 1;
            }

            Point a = points.get(i);
            Point b = points.get(k);

            if (a.y != b.y && a.x != b.x) {
                signs[i] = a.x * b.y - a.y * b.x < 0 ? minus : plus;
            }
        }
        OptionalInt anySign = Arrays.stream(signs).filter(z -> z != 0).findAny();
        if (!anySign.isPresent()) {
            throw new IllegalArgumentException("Многоугольник не выпуклый");
        }

        if (Arrays.stream(signs).filter(z -> z != 0).anyMatch(z -> z != anySign.getAsInt())) {
            throw new IllegalArgumentException("Многоугольник не выпуклый");
        }
    }
}
