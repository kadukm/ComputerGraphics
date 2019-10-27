package ru.mokeev.service.tasks;

import ru.mokeev.Constants;
import ru.mokeev.service.helpers.DoublePoint;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class FourthTask extends JPanel {

    private BiFunction<Double, Double, Double> function;
    private final double x1 = -3;
    private final double x2 = 3;
    private final double y1 = -3;
    private final double y2 = 3;

    private double n = 50;
    private double m = Constants.width * 2;
    private double mx = Constants.width - 1;
    private double my = Constants.height - 1;

    private double minX;
    private double maxX;
    private double minY;
    private double maxY;

    private Graphics g;

    public FourthTask(BiFunction<Double, Double, Double> function) {
        this.function = function;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        this.g = g;
        drawFunction();
    }

    private void drawFunction() {
        findMinMax();

        List<Integer> top = new ArrayList<>();
        List<Integer> bot = new ArrayList<>();

        for (int i = 0; i <= this.mx + 1000; i++) {
            top.add((int) this.my + 1);
            bot.add(0);
        }

        for (int i = 0; i <= n; i++) {
            double x = x2 + i * (x1 - x2) / n;
            for (int j = 0; j <= m; j++) {
                double y = y2 + j * (y1 - y2) / m;
                double z = function.apply(x, y);

                DoublePoint xy = leadToIsometric(x, y, z);
                double xp = xy.x;
                double yp = xy.y;
                int xx = Math.toIntExact(Math.round((xp - minX) * mx / (maxX - minX)));
                int yy = Math.toIntExact(Math.round((yp - minY) * my / (maxY - minY)));
                if (yy > bot.get(xx)) {
                    putPixel(xx, yy, Color.RED);
                    bot.set(xx, yy);
                }
                if (yy < top.get(xx)) {
                    putPixel(xx, yy, Color.BLUE);
                    top.set(xx, yy);
                }
            }
        }

        top = new ArrayList<>();
        bot = new ArrayList<>();

        for (int i = 0; i <= this.mx + 1000; i++) {
            top.add((int) this.my + 1);
            bot.add(0);
        }

        findMinMaxAcross();
        for (int i = 0; i <= n; i++) {
            double y = y2 + i * (y1 - y2) / n;
            for (int j = 0; j <= m; j++) {
                double x = x2 + j * (x1 - x2) / m;
                double z = function.apply(x, y);

                DoublePoint xy = leadToIsometric(x, y, z);
                double xp = xy.x;
                double yp = xy.y;
                int xx = Math.toIntExact(Math.round((xp - minX) * mx / (maxX - minX)));
                int yy = Math.toIntExact(Math.round((yp - minY) * my / (maxY - minY)));
                if (yy > bot.get(xx)) {
                    putPixel(xx, yy, Color.RED);
                    bot.set(xx, yy);
                }
                if (yy < top.get(xx)) {
                    putPixel(xx, yy, Color.BLUE);
                    top.set(xx, yy);
                }
            }
        }
    }

    private void findMinMax() {
        minX = 100000;
        maxX = -minX;

        maxY = maxX;
        minY = minX;

        for (int i = 0; i < n + 1; i++) {
            double x = x2 + i * (x1 - x2) / n;
            for (int j = 0; j < m; j++) {
                double y = y2 + j * (y1 - y2) / m;
                double z = function.apply(x, y);

                DoublePoint xy = leadToIsometric(x, y, z);
                double xp = xy.x;
                double yp = xy.y;
                if (xp > maxX) maxX = xp;
                if (yp > maxY) maxY = yp;
                if (xp < minX) minX = xp;
                if (yp < minY) minY = yp;
            }
        }
    }

    private void findMinMaxAcross() {
        minX = 100000;
        maxX = -minX;

        maxY = maxX;
        minY = minX;

        for (int i = 0; i < n + 1; i++) {
            double y = y2 + i * (y1 - y2) / n;
            for (int j = 0; j < m; j++) {
                double x = x2 + j * (x1 - x2) / m;
                double z = function.apply(x, y);

                DoublePoint xy = leadToIsometric(x, y, z);
                double xp = xy.x;
                double yp = xy.y;
                if (xp > maxX) maxX = xp;
                if (yp > maxY) maxY = yp;
                if (xp < minX) minX = xp;
                if (yp < minY) minY = yp;
            }
        }
    }

    private DoublePoint leadToIsometric(double x, double y, double z) {
        return new DoublePoint((y - x) * Math.sqrt(3.0) / 2, (x + y) / 2 - z);
    }

    private void putPixel(int x, int y, Color color) {
        g.setColor(color);
        g.drawOval(x - 1, y - 1, 1, 1);
    }
}
