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

    private int x;
    private int y;
    private int adjustedX;
    private int adjustedY;
    private boolean bigSpacing;
    private static int close;

    private static final ArrayList<Coord> others = new ArrayList<>();

    public Coord(int x, int y, boolean bigSpacing) {
        this.x = x;
        this.y = y;
        adjustedX = x;
        adjustedY = y;
        if (bigSpacing) {
            close = 15;
        } else {
            close = 5;
        }
        this.bigSpacing = bigSpacing;
        while (!ok()) {
            adjust();
        }
        //System.out.println(x + " " + y + " " + adjustedX + " " + adjustedY);
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
            boolean sameType = this.bigSpacing == other.bigSpacing;
            //System.out.println("sameType: " + sameType);
            if (sameType && close(other)) {
                return false;
            }
        }
        return true;
    }

    private boolean close(Coord other) {
        if (Math.abs(other.adjustedX - adjustedX) < close && Math.abs(other.adjustedY - adjustedY) < close) {
            return true;
        }
        return false;
    }

    private void adjust() {
        adjustedX += adjustment();
        adjustedY += adjustment();
    }

    private int adjustment() {
        if (Math.random() < .5) {
            return close;
        } else {
            return -close;
        }
    }
}
