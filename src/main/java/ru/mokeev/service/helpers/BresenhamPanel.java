package ru.mokeev.service.helpers;

import javax.swing.*;
import java.awt.*;

public class BresenhamPanel extends JPanel {

    private final int pixelSize = 1;

    public BresenhamPanel() {
        setPreferredSize(new Dimension(600, 500));
        setBackground(Color.WHITE);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int w = (getWidth() - 1) / pixelSize;
        int h = (getHeight() - 1) / pixelSize;
        int maxX = (w - 1) / 2;
        int maxY = (h - 1) / 2;
        int x1 = -maxX;
        int x2 = maxX * -2 / 3;
        int x3 = maxX * 2 / 3;
        int x4 = maxX;
        int y1 = -maxY;
        int y2 = maxY * -2 / 3;
        int y3 = maxY * 2 / 3;
        int y4 = maxY;

        drawLine(g, 0, 0, x3, y1); // NNE
//        drawLine(g, 0, 0, x4, y2); // ENE
//        drawLine(g, 0, 0, x4, y3); // ESE
//        drawLine(g, 0, 0, x3, y4); // SSE
//        drawLine(g, 0, 0, x2, y4); // SSW
//        drawLine(g, 0, 0, x1, y3); // WSW
//        drawLine(g, 0, 0, x1, y2); // WNW
//        drawLine(g, 0, 0, x2, y1); // NNW
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
}