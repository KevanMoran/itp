/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itp.images;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author Kevan
 */
public class Pie {
    private final int no;
    private final int noPerCircle;
    private final Color color;
    private final String text;
    private final double score;

    public Pie(int no, int noPerCircle, Color color, String text, double score) {
        this.no = no;
        this.noPerCircle = noPerCircle;
        this.color = color;
        this.text = text;
        this.score = score;
    }
    
    public void drawIt(Canvas canvas) {
        Graphics2D g2d = canvas.getG2D();
        int layer = 3 - noPerCircle; //
        
        int min = canvas.getMin();
        int max = canvas.getMax();

        // it's just a rectangle and an angle pair
        //ellipseBounds - The framing rectangle that defines the outer boundary of the full ellipse of which this arc is a partial section.
        //start - The starting angle of the arc in degrees.
        //extent - The angular extent of the arc in degrees.
        //type - The closure type for the arc: Arc2D.OPEN, Arc2D.CHORD, or Arc2D.PIE.
        int size = max - min;
        int upperLeftX = min;
        int upperLeftY = min;
        int width = size;
        int height = size;
        
        
        Rectangle2D.Double boundingRectangle = new Rectangle2D.Double(upperLeftX, upperLeftY, width, height);

        g2d.setPaint(Color.RED);
        Arc2D.Double arc = new Arc2D.Double(boundingRectangle, 360 + 1 * 30, 30, Arc2D.PIE);
        g2d.fill(arc);
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(10));
        g2d.draw(arc);
    }
    
}
