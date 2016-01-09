/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itp.images;

import java.util.ArrayList;

/**
 *
 * @author Kevan
 */
public class Coord {
    protected int x;
    protected int y;
    protected int adjustedX;
    protected int adjustedY;
    private static final int CLOSE = 10;
    
    private static final ArrayList<Coord> others = new ArrayList<>();

    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
        adjustedX = x;
        adjustedY = y;
        while (!ok()) {
            adjust();
        }
        others.add(this);
    }

    public int getAdjustedX() {
        return adjustedX;
    }

    public int getAdjustedY() {
        return adjustedY;
    }
    
    private boolean ok() {
        for (Coord other : others) {
            if (close(other)) {
                return false;
            }
        }
        return true;
    }
    
    private boolean close(Coord other) {
        if (Math.abs(other.adjustedX - adjustedX) < CLOSE && Math.abs(other.adjustedY - adjustedY) < CLOSE) {
            return true;
        }
        return false;
    }
    
    private void adjust() {
        adjustedX += adjustment();
        adjustedY += adjustment();
    }
    
    private int adjustment()  {
        if (Math.random() < .5) {
            return CLOSE;
        } else {
            return - CLOSE;
        }
    }
}
