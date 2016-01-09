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
public class Wheel {
    private static final ArrayList<Structure> structures = new ArrayList<>();
    private static final ArrayList<Area> areas = new ArrayList<>();
    private static final ArrayList<Category> categories = new ArrayList<>();
    
    public static void addStructure(Structure structure) {
        structures.add(structure);
    }
    
    public static void addArea(Area area) {
        areas.add(area);
    }
    
    public static void addCategory(Category category) {
        category.setIndex(categories.size());
        categories.add(category);
    }

    public static ArrayList<Structure> getStructures() {
        return structures;
    }

    public static ArrayList<Area> getAreas() {
        return areas;
    }

    public static ArrayList<Category> getCategories() {
        return categories;
    }
    
    
}
