/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itp.images;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Kevan
 */
public class Question {

    private final String text;
    private final int number;
    private final Category category;
    private double score;
    private double sd;
    private boolean inWeakestCategory;
    private static final Question[] questions = new Question[40];
    private ArrayList<Integer> answers = new ArrayList<>();

    public Question(String text, int number, Category category) {
        this.text = text;
        this.number = number;
        this.category = category;
        inWeakestCategory = false;
        questions[number - 1] = this;
        category.addQuestion(this);
    }

    public boolean isInWeakestCategory() {
        return inWeakestCategory;
    }

    public void setInWeakestCategory(boolean inWeakestCategory) {
        this.inWeakestCategory = inWeakestCategory;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public void setSd(double sd) {
        this.sd = sd;
    }

    public String getText() {
        return text;
    }

    public int getNumber() {
        return number;
    }

    public Category getCategory() {
        return category;
    }

    public double getScore() {
        return score;
    }

    public double getSd() {
        if (answers.size() < 2) {
            return 0;
        }
        double sum = 0;
        for (Integer answer : answers) {
            sum += answer;
        }
        double mean = sum / answers.size();
        double sumVarianceSquared = 0;
        for (Integer answer : answers) {
            double diff = mean - answer;
            sumVarianceSquared += diff * diff;
        }
        sd = Math.sqrt(sumVarianceSquared) / (answers.size() - 1);
        return sd;
    }

    public static Question[] getQuestions() {
        return questions;
    }

    @Override
    public String toString() {
        return "Question{" + "text=" + text + ", number=" + number + ", category=" + category + ", score=" + score + ", sd=" + sd + '}';
    }

    static {
        int index = 0;
        Category category;
        Question question;

        category = Category.getCategory(index++);
        question = new Question("Are competencies and technologies effectively managed?", 18, category);
        question = new Question("Are supplier competencies and technology evaluated for their ability to support requirements?", 22, category);
        question = new Question("How effectively does infrastructure, systems and tools support innovation?", 32, category);
        question = new Question("How does organisational structures and infrastructure installation support innovation?", 33, category);

        category = Category.getCategory(index++);
        question = new Question("Are resources appropriately allocated?", 8, category);
        question = new Question("Are key decision points identified within the innovation process?", 11, category);
        question = new Question("What is the decision making process?", 15, category);
        question = new Question("How does capital investment support innovation?", 29, category);
        question = new Question("How are resources made available to support innovation?", 31, category);

        category = Category.getCategory(index++);
        question = new Question("What is the contribution of values and principles to the organisational environment?", 27, category);
        question = new Question("How is innovative behaviour encouraged and supported?", 28, category);
        question = new Question("How are teams constructed?", 34, category);
        question = new Question("What is the state of communication?", 35, category);

        category = Category.getCategory(index++);
        question = new Question("How are individuals and teams motivated and rewarded?", 36, category);
        question = new Question("How are new people hired and roles assigned?", 37, category);

        category = Category.getCategory(index++);
        question = new Question("How well is the market understood (customer needs, maturity and size, competition, regulation etc.)?", 1, category);
        question = new Question("How is research conducted?", 14, category);

        category = Category.getCategory(index++);
        question = new Question("How does the opportunity identification process work?", 2, category);
        question = new Question("How are opportunities and ideas transformed into concepts?", 3, category);
        question = new Question("How are opportunities and ideas found?", 5, category);
        question = new Question("How are opportunities and concepts tested, screened and prioritised?", 6, category);
        question = new Question("How are opportunities and concepts put into context?", 9, category);

        category = Category.getCategory(index++);
        question = new Question("What are the Intellectual property policies and management practices?", 17, category);
        question = new Question("How is intellectual property policy deployed and managed?", 21, category);

        category = Category.getCategory(index++);
        question = new Question("Has a knowledge acquisition strategy been established?", 12, category);
        question = new Question("How is new information identified and extracted?", 16, category);
        question = new Question("How is data and information captured, stored and deployed?", 19, category);
        question = new Question("How is tacit knowledge managed?", 23, category);

        category = Category.getCategory(index++);
        question = new Question("How is innovation measured and monitored?", 38, category);
        question = new Question("How is the innovation model adapted and improved?", 39, category);
        question = new Question("How are innovation processes & management practices benchmarked?", 40, category);

        category = Category.getCategory(index++);
        question = new Question("How effectively are customers, suppliers & other stakeholders involved in the innovation process?", 4, category);
        question = new Question("What approach is used for innovation planning and co-ordination?", 10, category);
        question = new Question("What is the state of collaboration and networking internally?", 24, category);
        question = new Question("What is the state of collaboration and networking with external parties?", 25, category);
        question = new Question("Has an innovation strategy been established and communicated?", 26, category);
        question = new Question("What is the level of organisational support for practices & procedures that drive innovation?", 30, category);

        category = Category.getCategory(index++);
        question = new Question("How are uncertainties and risks managed and reduced?", 13, category);

        category = Category.getCategory(index++);
        question = new Question("Are appropriate project management techniques utilised?", 7, category);
        question = new Question("Are the effects of projects understood?", 20, category);
    }

    public void addAnswer(Integer a) {
        answers.add(a);
        score = 0;
        for (Integer answer : answers) {
            score += answer;
        }
        score = score / answers.size();
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        File file = new File("c:/itp/answers.csv");
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        int questionNo = 0;
        Question[] questions = Question.getQuestions();
        while ((line = br.readLine()) != null) {
            String[] split = line.split(",");
            double score = Double.parseDouble(split[0]);
            double sd = Double.parseDouble(split[1]);
            Question question = questions[questionNo];
            question.setScore(score);
            question.setSd(sd);
            questionNo++;
        }
        br.close();
        fr.close();
        for (int i = 0; i < 12; i++) {
            Category category = Category.getCategory(i);
            //System.out.println(category);
            ArrayList<Question> answeredQuestions = category.getQuestions();
            answeredQuestions.stream().forEach((question) -> {
                //System.out.println("   " + question);
            });
        }
    }
}
