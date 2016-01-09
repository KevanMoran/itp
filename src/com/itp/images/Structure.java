/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itp.images;

import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author Kevan
 */
public class Structure {
    private final String name;
    private final Color color;
    private final ArrayList<Area> areas = new ArrayList<>();
    
    public static int OPERATIONS = 0;
    public static int INSIGHTS = 1;
    public static int PROCESSES = 2;
    
    public Structure(String name, Color color) {
        this.name = name;
        this.color = color;      
        Wheel.addStructure(this);
    }

    public void addArea(Area area) {
        areas.add(area);
    }
    
    public ArrayList<Area> getAreas() {
        return areas;
    }
    
    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }
    
    public double getScore() {
        double score = 0;
        for (Area area : areas) {
            score += area.getScore();
        }
        return score / areas.size();
    }

    @Override
    public String toString() {
        return "Structure{" + "name=" + name + ", color=" + color + ", score=" + getScore() + '}';
    }
    
    public double getOverAllScore() {
        ArrayList<Structure> structures = Wheel.getStructures();
        double score = 0;
        for (Structure structure : structures) {
            score += structure.getScore();
        }
        return score/ structures.size();
    }
    
    public static double getScore(int structureNumber) {
        Structure structure = Wheel.getStructures().get(structureNumber);
        return structure.getScore();
    }
}
