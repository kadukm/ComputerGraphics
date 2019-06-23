package ru.mokeev.service.tasks;

import org.apache.commons.lang3.tuple.Pair;

import java.awt.*;

public class FirstTask extends Component {
    private final double xMin;
    private final double xMax;
    private final double width;
    private final double height;

    private double yMin;
    private double yMax;

    private Graphics2D g2;

    public FirstTask(double xMin, double xMax, double width, double height) {
        this.xMin = xMin;
        this.xMax = xMax;
        this.width = width;
        this.height = height;

        Pair<Double, Double> minMax = findMinMaxY();
        this.yMin = minMax.getLeft();
        this.yMax = minMax.getRight();
    }

    public static Double calculateFunction(double x) {
        return Math.cos(x) * Math.pow(x, 2);
    }

    public void paint(Graphics g) {
        g2 = (Graphics2D) g;

        drawAxes();
        drawFunction();
    }

    private void drawFunction() {
        double oldXX = 0;
        double oldYY = 0;
        for (double xx = oldXX; xx < width; ++xx) {
            double x = screenToCoordinates(xx, 0d).getLeft();
            double yy = coordinatesToScreen(0d, calculateFunction(x)).getRight();
            drawLine(oldXX, oldYY, xx, yy);
            oldXX = xx;
            oldYY = yy;
        }
    }

    private Pair<Double, Double> coordinatesToScreen(Double x, Double y) {
        return Pair.of(
                (x - xMin) * width / (xMax - xMin),
                (y - yMax) * height / (yMin - yMax));
    }

    private Pair<Double, Double> screenToCoordinates(Double xx, Double yy) {
        return Pair.of(
                xx * (xMax - xMin) / width + xMin,
                yy * (yMin - yMax) / height + yMax);
    }

    private Pair<Double, Double> findMinMaxY() {
        double ymin = calculateFunction(xMin);
        double ymax = calculateFunction(xMin);

        for (int i = 0; i < width; ++i) {
            double x = i * (xMax - xMin) / width + xMin;
            double y = calculateFunction(x);
            ymin = Double.min(ymin, y);
            ymax = Double.max(ymax, y);
        }

        return Pair.of(
                Double.max(ymin, -height),
                Double.min(ymax, height));
    }

    private void drawAxes() {
        Pair<Double, Double> xy = coordinatesToScreen(0d, 0d);
        double xx0 = xy.getLeft();
        double yy0 = xy.getRight();

        drawLine(xx0, 0d, xx0, height);
        drawLine(0d, yy0, width, yy0);

        numerateAxes();
    }

    private void numerateAxes() {
        int stepX = Integer.max(1, (int) Math.round(screenToCoordinates(32d, 0d).getLeft() - screenToCoordinates(0d, 0d).getLeft()));

        for (double x = stepX; x < Integer.max(Math.abs((int) xMin), Math.abs((int) xMax)); x += stepX) {
            double yy = coordinatesToScreen(x, 0d).getRight();

            double xxRight = coordinatesToScreen(x, 0d).getLeft();
            double xxLeft = coordinatesToScreen(-x, 0d).getLeft();

            drawLine(xxRight, yy - 2, xxRight, yy + 2);
            drawLine(xxLeft, yy - 2, xxLeft, yy + 2);

            g2.setFont(new Font("TimesRoman", Font.PLAIN, 10));
            g2.drawString(String.valueOf((int) x), (int) xxRight - 4, (int) yy + 15);
            g2.drawString(String.valueOf((int) -x), (int) xxLeft - 7, (int) yy + 15);
        }

        int stepY = Integer.max(1, (int) Math.round(screenToCoordinates(0d, 0d).getRight() - screenToCoordinates(0d, 32d).getRight()));

        for (double y = stepY; y < Integer.max(Math.abs((int) yMin), Math.abs((int) yMax)); y += stepY) {
            double xx = coordinatesToScreen(0d, y).getLeft();

            double yyTop = coordinatesToScreen(0d, y).getRight();
            double yyBot = coordinatesToScreen(0d, -y).getRight();

            drawLine(xx - 2, yyTop, xx + 2, yyTop);
            drawLine(xx - 2, yyBot, xx + 2, yyBot);

            g2.setFont(new Font("TimesRoman", Font.PLAIN, 10));
            g2.drawString(String.valueOf((int) y), (int) xx + 15, (int) yyTop + 3);
            g2.drawString(String.valueOf((int) -y), (int) xx + 15, (int) yyBot + 3);
        }
    }

    private void drawPoint(Double x, Double y) {
        g2.drawLine(x.intValue(), y.intValue(), x.intValue(), y.intValue());
    }

    private void drawLine(Double x1, Double y1, Double x2, Double y2) {
        g2.drawLine(x1.intValue(), y1.intValue(), x2.intValue(), y2.intValue());
    }
}