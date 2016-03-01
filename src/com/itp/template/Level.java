/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itp.template;

import java.util.ArrayList;

/**
 *
 * @author Kevan
 */
public class Level {
    public static String[] levelNames = {
        "",
        "Ad-Hoc",
        "Repeatable",
        "Defined",
        "Managed",
        "Optimising"
    };
    
    private int number;
    private ArrayList<String> actions = new ArrayList<>();
    private ArrayList<String> characteristics = new ArrayList<>();

    public Level(int number) {
        this.number = number;
    }

    public String getName() {
        return levelNames[number];
    }

    public ArrayList<String> getActions() {
        return actions;
    }

    public ArrayList<String> getCharacteristics() {
        return characteristics;
    }
    
    public void addAction(String action) {
        actions.add(action);
    }
    
    public void addCharacteristic(String characteristic) {
        characteristics.add(characteristic);
    }
    
    public String getNextLevelName() {
        return levelNames[number + 1];
    }
    
    public Level getNextLevel() {
        return new Level(number + 1);
    }
}
