/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itp.images;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

/**
 *
 * @author Kevan
 */
public class Triangle {

    double score;
    double sd;
    Question question;
    int x;
    int y;
    private final static ArrayList<Triangle> others = new ArrayList<>();

    public Triangle(Question question, double score, double sd) {
        this.score = score;
        this.sd = sd;
        this.question = question;
        others.add(this);
    }

    public Triangle(Question question) {
        this.score = question.getScore();
        this.sd = question.getSd();
        this.question = question;
        others.add(this);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getScore() {
        return score;
    }

    public double getSD() {
        return sd;
    }

    public static void adjustCoords() {

    }

    public void draw(Graphics2D g2d) {
        if (question.isInWeakestCategory()) {
            g2d.setPaint(Color.YELLOW);
            Ellipse2D.Double circle = new Ellipse2D.Double(x - 5, y - 15, 20, 20);
            g2d.draw(circle);
            //g2d.fill(circle);
            g2d.setColor(Color.BLACK);
            Font font = g2d.getFont();
            Font boldFont = font.deriveFont(Font.BOLD, 9);
            g2d.setFont(boldFont);
            g2d.drawString("Q" + question.getNumber(), x, y);
            g2d.setFont(font);
        } else {
            int[] xPoints = {x, x + 5, x + 10};
            int[] yPoints = {y, y - 10, y};
            Polygon triangle = new Polygon(xPoints, yPoints, 3);
            Category category = question.getCategory();
            g2d.setPaint(category.getColor());
            g2d.fill(triangle);
        }
    }

    public Question getQuestion() {
        return question;
    }
    
    
}
