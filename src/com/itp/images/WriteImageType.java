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
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class WriteImageType {

    static public void main(String args[]) throws Exception {
        try {
            int size = 900;
            int width = size, height = size;
            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = bi.createGraphics();
            g2d.setPaint(Color.WHITE);
            g2d.fillRect(0, 0, size, size);
            
            Scatter scatter = new Scatter();
            Triangle triangle;

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
                //System.out.println(question);
                triangle = new Triangle(question, score, sd);
                scatter.addTriangle(triangle);
                questionNo++;
            }
            br.close();
            fr.close();
            scatter.drawAndWrite("C:\\ITP\\Template\\word\\media\\image3.jpeg");

        } catch (IOException ie) {
            ie.printStackTrace();
        }

    }

}
