package ru.mokeev.service.tasks;

import javax.swing.*;
import java.awt.*;

public class SecondTask extends JPanel {

    private final int pixelSize = 1;

    private Graphics g;
    private double A, B, C;
    private int direction;

    public SecondTask(int a, int b, int c) {

//        setSize(800, 600);
        if ((a < 0 && c >= 0) || (c < 0 && a >= 0)) {
            this.direction = 1;
        } else {
            this.direction = -1;
        }
        this.A = Math.abs(a);
        this.B = Math.abs(b);
        this.C = Math.abs(c);

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        this.g = g;
        drawAxes();
        draw(0, 0);
    }

    private void drawAxes() {
        for (int x = 0; x < getWidth(); x++) {
            plot(g, x, 0);
            plot(g, -x, 0);
        }
        for (int y = 0; y < getHeight(); y++) {
            plot(g, 0, y);
            plot(g, 0, -y);
        }
    }

    private double p() {
        return B * B / 2;
    }

    private void draw(int x, int y) {
        double p = p();

        //  центр
        double x0 = -A / C;
        double y0 = 0;
        System.out.println(x0);
        System.out.println(y0);

        double Sh, Sv, Sd;
        Sd = getSd(x, y, p);  // диагональ
        Sv = getSv(x, y, p);  // горизонталь
        Sh = getSh(x, y, p);  // вертикаль

        plot(g, direction * x0, y0, Color.RED, 1, 1);
        while (x + x0 < getWidth()) // пока полотно не кончится
        {
            if (Math.abs(Sh) - Math.abs(Sv) <= 0) {
                x++;
                if (Math.abs(Sd) - Math.abs(Sh) < 0)
                    y++;

            } else {
                y++;
                if (Math.abs(Sv) - Math.abs(Sd) > 0)
                    x++;

            }
            plot(g, direction * (x + x0), y0 + y);
            plot(g, direction * (x + x0), y0 - y);

            Sd = getSd(x, y, p);
            Sv = getSd(x, y, p);
            Sh = getSh(x, y, p);
        }
    }

    private double getSd(double x, double y, double p) {
        return ((y + 1) * (y + 1)) - 2 * p * (x * A / C + 1);
    }

    private double getSv(double x, double y, double p) {
        return ((y + 1) * (y + 1)) - 2 * p * x * A / C;
    }

    private double getSh(double x, double y, double p) {
        return (y * y) - 2 * p * (x * A / C + 1);
    }

    private void putPixel(Graphics g, Double x, Double y, Color color, int sizeX, int sizeY) {
        g.setColor(color);
        g.drawOval(x.intValue(), y.intValue(), sizeX, sizeY);
    }

    private void plot(Graphics g, double x, double y) {
        plot(g, x, y, Color.BLACK, 1, 1);
    }

    private void plot(Graphics g, double x, double y, Color color, int sizeX, int sizeY) {
        double w = (getWidth() - 1) / pixelSize;
        double h = (getHeight() - 1) / pixelSize;
        double maxX = (w - 1) / 2;
        double maxY = (h - 1) / 2;

        double borderX = getWidth() - ((2 * maxX + 1) * pixelSize + 1);
        double borderY = getHeight() - ((2 * maxY + 1) * pixelSize + 1);
        double left = (x + maxX) * pixelSize + borderX / 2;
        double top = (y + maxY) * pixelSize + borderY / 2;
        putPixel(g, left, top, color, sizeX, sizeY);
    }
}
