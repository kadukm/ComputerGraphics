package ru.mokeev.service.helpers;

import javax.swing.*;
import java.awt.*;

public class Frame extends JPanel {

    private Graphics g;
    private int A, B, C, D;


    public Frame(int a, int b, int c, int d) {

        setSize(800, 600);
        this.A = a;
        this.B = b;
        this.C = c;
        this.D = d;

    }

    private void putpixel(int x, int y) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        g2.drawOval(x, y, 1, 1);
    }

    void pixel4(int x, int y, int _x, int _y) { // Рисование пикселя для первого квадранта, и, симметрично, для остальных
        putpixel(x + _x, y + _y);
        putpixel(x + _x, y - _y);
        putpixel(x - _x, y - _y);
        putpixel(x - _x, y + _y);
    }

    private void draw(int x, int y, int a, int b) {
        int _x = 0; // Компонента x
        int _y = b; // Компонента y
        int a_sqr = a * a; // a^2, a - большая полуось
        int b_sqr = b * b; // b^2, b - малая полуось
        int delta = 4 * b_sqr * ((_x + 1) * (_x + 1)) + a_sqr * ((2 * _y - 1) * (2 * _y - 1)) - 4 * a_sqr * b_sqr; // Функция координат точки (x+1, y-1/2)
        while (a_sqr * (2 * _y - 1) > 2 * b_sqr * (_x + 1)) // Первая часть дуги
        {
            pixel4(x, y, _x, _y);
            if (delta < 0) // Переход по горизонтали
            {
                _x++;
                delta += 4 * b_sqr * (2 * _x + 3);
            } else // Переход по диагонали
            {
                _x++;
                delta = delta - 8 * a_sqr * (_y - 1) + 4 * b_sqr * (2 * _x + 3);
                _y--;
            }
        }
        delta = b_sqr * ((2 * _x + 1) * (2 * _x + 1)) + 4 * a_sqr * ((_y + 1) * (_y + 1)) - 4 * a_sqr * b_sqr; // Функция координат точки (x+1/2, y-1)
        while (_y + 1 != 0) // Вторая часть дуги, если не выполняется условие первого цикла, значит выполняется a^2(2y - 1) <= 2b^2(x + 1)
        {
            pixel4(x, y, _x, _y);
            if (delta < 0) // Переход по вертикали
            {
                _y--;
                delta += 4 * a_sqr * (2 * _y + 3);
            } else // Переход по диагонали
            {
                _y--;
                delta = delta - 8 * b_sqr * (_x + 1) + 4 * a_sqr * (2 * _y + 3);
                _x++;
            }
        }

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        this.g = g;
        draw(B, D, A, C);
    }
}