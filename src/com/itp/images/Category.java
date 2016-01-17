/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itp.images;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Kevan
 */
public class Category implements Comparable {

    private final String name;
    private final Area area;
    private final ArrayList<Question> questions = new ArrayList<>();
    private int index;
    private int rank;
    
    public Category(String name, Area area) {
        this.name = name;
        this.area = area;
        area.addCategory(this);
        Wheel.addCategory(this);
    }

    public String getName() {
        return name;
    }

    public Area getArea() {
        return area;
    }

    @Override
    public String toString() {
        return "Category{" + "name=" + name + ", area=" + area + ", score=" + getScore() + '}';
    }

    public void addQuestion(Question question) {
        //System.out.println("Adding " + question + " to " + this);
        questions.add(question);
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public double getScore() {
        double score = 0;
        score = questions.stream().map((question) -> question.getScore()).reduce(score, (accumulator, _item) -> accumulator + _item);
        return score / questions.size();
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    private static final Category[] categories = new Category[12];

    public void setAsWeakest() {
        for (Question question : questions) {
            question.setInWeakestCategory(true);
        }
        System.out.println("Setting " + name + " as weakest category");
        int myIndex = getIndex();
        double[] myLinkages = linkages[index];
        for (int othersIndex = 0; othersIndex < myLinkages.length; othersIndex++) {
            if (myIndex == othersIndex) {
                myLinkages[myIndex] = -99;
            } else {
                Category otherCategory = getCategory(othersIndex);
                double doubleDiff = 2 * (getScore() - otherCategory.getScore());
                //System.out.println("otherCategory=" + otherCategory.getName() + ", othersIndex=" + othersIndex + ", myLinkages[othersIndex]=" + myLinkages[othersIndex] + ", doubleDiff=" + doubleDiff);
                myLinkages[othersIndex] += doubleDiff;
                //System.out.println("Adjusted=" + myLinkages[othersIndex]);
            }
        }
        double maxLinkage = -99;
        for (int index = 0; index < myLinkages.length; index++) {
            double thisLinkage = myLinkages[index];
            if (thisLinkage > maxLinkage) {
                maxLinkage = thisLinkage;
                secondary1index = index;
            }
        }
        maxLinkage = -99;
        for (int index = 0; index < myLinkages.length; index++) {
            double thisLinkage = myLinkages[index];
            if (thisLinkage > maxLinkage && index != secondary1index) {
                maxLinkage = thisLinkage;
                secondary2index = index;
            }
        }
    }

    public int getSecondary1index() {
        return secondary1index;
    }

    public int getSecondary2index() {
        return secondary2index;
    }

    private int secondary1index;
    private int secondary2index;

    private static final double[][] linkages
            = {
                {1.0, 3.0, 1.0, 1.0, 2.0, 1.0, 1.0, 1.0, 1.0, 3.0, 1.0, 2.0},
                {3.0, 1.0, 3.0, 1.0, 2.0, 1.0, 1.0, 2.0, 2.0, 3.0, 1.0, 1.0},
                {1.0, 1.0, 1.0, 2.0, 1.0, 3.0, 1.0, 3.0, 1.0, 3.0, 1.0, 1.0},
                {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 3.0, 3.0, 1.0, 1.0},
                {3.0, 1.0, 2.0, 2.0, 1.0, 1.0, 1.0, 3.0, 2.0, 3.0, 1.0, 1.0},
                {3.0, 3.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 3.0, 3.0, 1.0, 1.0},
                {2.0, 1.0, 2.0, 2.0, 2.0, 1.0, 1.0, 1.0, 3.0, 3.0, 2.0, 1.0},
                {3.0, 1.0, 2.0, 2.0, 1.0, 1.0, 3.0, 1.0, 3.0, 3.0, 1.0, 1.0},
                {3.0, 1.0, 3.0, 1.0, 1.0, 1.0, 1.0, 2.0, 1.0, 1.0, 1.0, 3.0},
                {1.0, 3.0, 3.0, 1.0, 1.0, 1.0, 1.0, 1.0, 3.0, 1.0, 1.0, 1.0},
                {3.0, 2.0, 2.0, 1.0, 1.0, 1.0, 1.0, 2.0, 1.0, 3.0, 1.0, 3.0},
                {2.0, 3.0, 2.0, 1.0, 1.0, 3.0, 1.0, 2.0, 3.0, 3.0, 3.0, 1.0}
            };

    static {
        Structure structure;
        Area area;
        Category category;
        int index = 0;

        structure = new Structure("Operations", Color.GREEN);

        area = new Area("Resources", structure);
        category = new Category("Technology Management", area);
        categories[index++] = category;
        category = new Category("Decision Making", area);
        categories[index++] = category;

        area = new Area("Culture", structure);
        category = new Category("Team Dynamics", area);
        categories[index++] = category;
        category = new Category("Rewards and Recognition", area);
        categories[index++] = category;

        structure = new Structure("Insights", Color.RED);

        area = new Area("Ideation", structure);
        category = new Category("Analytics and Research", area);
        categories[index++] = category;
        category = new Category("Idea Management", area);
        categories[index++] = category;

        area = new Area("Knowledge", structure);
        category = new Category("IP Management", area);
        categories[index++] = category;
        category = new Category("Information Management", area);
        categories[index++] = category;

        structure = new Structure("Processes", Color.BLUE);

        area = new Area("Strategy", structure);
        category = new Category("Performance Metrics", area);
        categories[index++] = category;
        category = new Category("Leadership Practices", area);
        categories[index++] = category;

        area = new Area("Systems", structure);
        category = new Category("Priorities and Risk", area);
        categories[index++] = category;
        category = new Category("Projects and Control", area);
        categories[index++] = category;

    }

    public static Category getCategory(int index) {
        return categories[index];
    }

    public Color getColor() {
        return area.getColor();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 12; i++) {
            Category category = Category.getCategory(i);
            //System.out.println(category);
            ArrayList<Question> questions = category.getQuestions();
            for (Question question : questions) {
                //System.out.println("    " + question);
            }
        }
    }

    @Override
    public int compareTo(Object o) {
        Category other = (Category) o;
        if (this.getScore() < other.getScore()) {
            return -1;
        }
        if (this.getScore() > other.getScore()) {
            return 1;
        }
        return 0;
    }

    public static Category getByRank(int rank) {
        ArrayList<Category> toSort = new ArrayList<>();
        for (Category category : Wheel.getCategories()) {
            toSort.add(category);
            //System.out.println(category);
        }
        Collections.sort(toSort);
        Category category = toSort.get(rank);
        //System.out.println("category of rank " + rank + " is " + category);
        return category;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
    
    public static void setRanks() {
        ArrayList<Category> toSort = new ArrayList<>();
        for (Category category : Wheel.getCategories()) {
            toSort.add(category);
        }
        Collections.sort(toSort);
        for (int rank = 0; rank < toSort.size(); rank++) {
            Category category = toSort.get(rank);
            category.setRank(rank);
        }
    }
    
    public String getHeatMapColor() {
        switch (rank) {
            case 0:
            case 1:
            case 2:
                return "FF0000";
            case 9:
            case 10:
            case 11:
                return "00FF00";
            default:
                return "FBF000";
        }
    }
}
