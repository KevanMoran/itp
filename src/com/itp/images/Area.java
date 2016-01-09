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
public class Area {

    private final String name;
    private final Structure structure;
    private ArrayList<Category> categories = new ArrayList<>();
    private static ArrayList<Area> areas = new ArrayList<>();
    public static int RESOURCES = 0;
    public static int CULTURE = 1;
    public static int IDEATION = 2;
    public static int KNOWLEDGE = 3;
    public static int STRATEGY = 4;
    public static int SYSTEMS = 5;
    
    public Area(String name, Structure structure) {
        this.name = name;
        this.structure = structure;
        structure.addArea(this);
        areas.add(this);
        Wheel.addArea(this);
    }

    public String getName() {
        return name;
    }

    public Structure getStructure() {
        return structure;
    }

    public void addCategory(Category category) {
        categories.add(category);
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }
    
    public double getScore() {
        double score = 0;
        score = categories.stream().map((category) -> category.getScore()).reduce(score, (accumulator, _item) -> accumulator + _item);
        return score / categories.size();
    }
    
    @Override
    public String toString() {
        return "Area{" + "name=" + name + ", structure=" + structure + ", score=" + getScore() + '}';
    }

    public Color getColor() {
        return structure.getColor();
    }
    
    public static double getMinimumScore() {
        double score = 99;
        for (Area area : areas) {
            if (area.getScore() < score) {
                score = area.getScore();
            }
        }
        return score;
    }
    public static double getMaximumScore() {
        double score = 0;
        for (Area area : areas) {
            if (area.getScore() > score) {
                score = area.getScore();
            }
        }
        return score;
    }
    
    public static double getScore(int areaNumber) {
        return areas.get(areaNumber).getScore();
    }
    
}
