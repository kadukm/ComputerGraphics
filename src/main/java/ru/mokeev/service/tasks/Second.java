package ru.mokeev.service.tasks;

import javax.swing.*;
import java.awt.*;

import static java.lang.Math.abs;

public class Second extends JPanel {

    private final int pixelSize = 1;


    private Graphics g;
    private int A, B, C;

    private final int xMin;
    private final int xMax;
    private final int yMax;
    private final int yMin;


    public Second(int a, int b, int c) {

//        setSize(800, 600);
        this.A = a;
        this.B = b;
        this.C = c;

        this.xMax = A / C + 2;
        this.xMin = -xMax;

        this.yMax = abs(b) * 2;
        this.yMin = -yMax;
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        this.g = g;

        draw(0, 0);
    }


    private int p() {
        return B * B / 2;
    }

    private int x(int x) {
        return 1 - C * x / A;
    }


    private void draw(int x, int y) {
        int p = p();

        //  центр
        int x0 = A / C;
        int y0 = 0;

        int Sh, Sv, Sd;
        Sd = getSd(x, y, p);  // диагональ
        Sv = getSv(x, y, p);  // горизонталь
        Sh = getSh(x, y, p);  // вертикаль
        plot(g, x0, y0);
        while (x + x0 < getWidth()) // пока полотно не кончится
        {
            if (Math.abs(Sh) - Math.abs(Sv) <= 0) {
                if (Math.abs(Sd) - Math.abs(Sh) < 0)
                    y++;
                x++;

            } else {
                if (Math.abs(Sv) - Math.abs(Sd) > 0)
                    x++;
                y++;

            }

            int direction = 1;
            if ((double) A / (double) C > 0) {
                direction = -1;
            }
            plot(g, direction * (x + x0), y0 + y);
            plot(g, direction * (x + x0), y0 - y);

            Sd = getSd(x, y, p);
            Sv = getSd(x, y, p);
            Sh = getSh(x, y, p);
        }
    }

    private int getSd(int x, int y, int p) {
        return ((y + 1) * (y + 1)) - 2 * p * (x - 1);
    }

    private int getSv(int x, int y, int p) {
        return ((y + 1) * (y + 1)) - 2 * p * x;
    }

    private int getSh(int x, int y, int p) {
        return (y * y) - 2 * p * (x - 1);
    }


    private void drawLine(Graphics g, int x1, int y1, int x2, int y2) {
        // delta of exact value and rounded value of the dependent variable
        int d = 0;

        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);

        int dx2 = 2 * dx; // slope scaling factors to
        int dy2 = 2 * dy; // avoid floating point

        int ix = x1 < x2 ? 1 : -1; // increment direction
        int iy = y1 < y2 ? 1 : -1;

        int x = x1;
        int y = y1;

        if (dx >= dy) {
            while (true) {
                plot(g, x, y);
                if (x == x2)
                    break;
                x += ix;
                d += dy2;
                if (d > dx) {
                    y += iy;
                    d -= dx2;
                }
            }
        } else {
            while (true) {
                plot(g, x, y);
                if (y == y2)
                    break;
                y += iy;
                d += dx2;
                if (d > dy) {
                    x += ix;
                    d -= dy2;
                }
            }
        }
    }

    private void putPixel(Graphics g, int x, int y) {
        g.setColor(Color.BLACK);
        g.drawOval(x, y, 1, 1);
    }


    private void plot(Graphics g, int x, int y) {
        int w = (getWidth() - 1) / pixelSize;
        int h = (getHeight() - 1) / pixelSize;
        int maxX = (w - 1) / 2;
        int maxY = (h - 1) / 2;

        int borderX = getWidth() - ((2 * maxX + 1) * pixelSize + 1);
        int borderY = getHeight() - ((2 * maxY + 1) * pixelSize + 1);
        int left = (x + maxX) * pixelSize + borderX / 2;
        int top = (y + maxY) * pixelSize + borderY / 2;
        putPixel(g, left, top);
    }
}
