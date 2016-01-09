/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itp.images;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Kevan
 */
public class Canvas {
    private final int size;
    private final BufferedImage bi;
    private final Graphics2D g2d;
    private int margin = 5;
    
    public Canvas(int size) {
        this.size = size;
        bi = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        g2d = bi.createGraphics();            
    }
    
    public Graphics2D getG2D() {
        return g2d;
    }
    
    public int getCentre() {
        return size / 2;
    }
    
    public int getMin() {
        return margin;
    }
    
    public int getMax() {
        return size - margin;
    }
    
    public void write(String fileName) throws IOException {
        ImageIO.write(bi, "JPEG", new File(fileName));
    }
}
