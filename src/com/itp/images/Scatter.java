/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itp.images;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 *
 * @author Kevan
 */
public class Scatter {

    private final int size = 600;
    private final int width = size;
    private final int height = size;
    private final BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    private final Graphics2D g2d;
    private final ArrayList<Triangle> triangles = new ArrayList<>();
    private double margin;
    private double scoreScale;
    private double sdScale;
    private double minScore = 99;
    private double minSD = 99;
    private double maxScore = 0;
    private double maxSD = 0;

    public Scatter() {
        g2d = bi.createGraphics();
        g2d.setPaint(Color.WHITE);
        g2d.fillRect(0, 0, size, size);
    }

    public Scatter(Question[] questions) {
        g2d = bi.createGraphics();
        g2d.setPaint(Color.WHITE);
        g2d.fillRect(0, 0, size, size);
        for (Question question : questions) {
            Triangle triangle = new Triangle(question);
            addTriangle(triangle);
        }
    }

    public void addTriangle(Triangle t) {
        triangles.add(t);
    }

    private void setXYs() {
        for (Triangle triangle : triangles) {
            double score = triangle.getScore();
            double sd = triangle.getSD();
            System.out.println("Score " + score + " minScore " + minScore);
            if (score < minScore) {
                minScore = score;
            }
            if (score > maxScore) {
                maxScore = score;
            }
            if (sd < minSD) {
                minSD = sd;
            }
            if (sd > maxSD) {
                maxSD = sd;
            }
        }
        if (minSD == maxSD) {
            maxSD +=1;
        }
        if (minScore == maxScore) {
            maxScore += 1;
        }
        double scoreRange = maxScore - minScore;
        double sdRange = maxSD - minSD;
        margin = size / 10;
        scoreScale = (size - 2 * margin) / scoreRange;
        sdScale = (size - 2 * margin) / sdRange;

        //System.out.println("minScore " + minScore + "maxScore " + maxScore + "size " + size + "scoreScale " + scoreScale);
        for (Triangle triangle : triangles) {
            double score = triangle.getScore();

            double sd = triangle.getSD();
            int x = (int) Math.round(margin + (sd - minSD) * sdScale);
            int y = (int) Math.round(margin + (score - minScore) * scoreScale);
            y = size - y; //biggeris higher up

            Coord coord = new Coord(x, y);
            x = coord.getAdjustedX();
            y = coord.getAdjustedY();

            triangle.setX(x);
            triangle.setY(y);
            //System.out.println("Question =  at " + x + ", " + y);
        }

    }

    public void drawAndWrite(String fileName) throws IOException {
        setXYs();
        //System.out.println("margin = " + margin + " minSd = " + minSD + " sdScale = " + sdScale + " maxSD = " + maxSD);
        //System.out.println("margin = " + margin + " minScore = " + minScore + " scoreScale = " + scoreScale + " maxScore = " + maxScore);

        for (int i = 0; i < triangles.size(); i++) {

            Triangle t = triangles.get(i);
            if (!t.getQuestion().isInWeakestCategory()) {
                t.draw(g2d);
            }
            //System.out.println(i + " " + t.getSD() + " " + t.getScore());
        }
        for (int i = 0; i < triangles.size(); i++) {

            Triangle t = triangles.get(i);
            if (t.getQuestion().isInWeakestCategory()) {
                t.draw(g2d);
            }
            //System.out.println(i + " " + t.getSD() + " " + t.getScore());
        }
        int sdStartX = (int) Math.round(margin / 2);
        int sdY = (int) Math.round(size - margin / 2);
        int sdEndX = (int) Math.round(size - margin / 2);
        g2d.setPaint(Color.BLACK);
        g2d.drawLine(sdStartX, sdY, sdEndX, sdY);
        for (double tick = 0; tick < maxSD + .1; tick += .1) {
            if (tick > minSD) {
                int tickTop = (int) Math.round(size - margin);
                int tickBottom = (int) Math.round(size - margin / 2);
                int tickX = (int) Math.round(sdStartX + margin / 2 + sdScale * tick);
                g2d.drawLine(tickX, tickTop, tickX, tickBottom);
                DecimalFormat decimalFormat = new DecimalFormat("0.0");
                String label = decimalFormat.format(tick);
                g2d.drawString(label, tickX, (int) Math.round(size - margin / 4));
            }
        }
        int scoreStartX = (int) Math.round(margin / 2);
        int scoreEndX = scoreStartX;
        int scoreStartY = (int) Math.round(size - margin / 2);
        int scoreEndY = (int) Math.round(margin / 2);
        g2d.drawLine(scoreStartX, scoreStartY, scoreEndX, scoreEndY);

        for (double tick = 0; tick < maxScore + .1; tick += .1) {
            if (tick > minScore) {
                int tickLeft = (int) Math.round(margin / 2);
                int tickRight = (int) Math.round(margin);
                int tickY = (int) Math.round(scoreStartY - margin / 2 - scoreScale * (tick - minScore));
                DecimalFormat decimalFormat = new DecimalFormat("0.0");
                String label = decimalFormat.format(tick);
                //System.out.println("tick " + label + " tickY " + tickY);
                g2d.drawLine(tickLeft, tickY, tickRight, tickY);
                g2d.drawString(label, (int) (margin / 5), tickY);

            }
        }
        g2d.drawString("Consensus vs Non-Consensus", (int) (sdStartX + sdEndX / 3), (int) (size));
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.rotate(-Math.PI / 2);
        Font font = g2d.getFont();
        Font rotated = font.deriveFont(affineTransform);
        g2d.setFont(rotated);
        g2d.drawString("Agreggate Survey Result", (int) (margin / 6), (int) (size / 2));
        ImageIO.write(bi, "JPEG", new File(fileName));
    }
}
