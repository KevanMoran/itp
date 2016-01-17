/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Answers;

import com.itp.images.Question;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Kevan
 */
public class CSVReader {
    public static void read(String fileName, String domainName) throws FileNotFoundException, IOException {
        Question[] questions = Question.getQuestions();
        
        File file = new File(fileName);
        BufferedReader br;
        FileReader fr = new FileReader(file);
        br = new BufferedReader(fr);
        String line;
        int answer1 = 9;
        while ((line = br.readLine()) != null) {
            if (line.contains(domainName)) {
                String[] bits = line.split(",");
                for (int questionNo=0; questionNo<40; questionNo++) {
                    Integer answer = Integer.parseInt(bits[answer1 + questionNo]);
                    questions[questionNo].addAnswer(answer);
                }
            }
        }
        fr.close();
        br.close();
        
    }
    
    public static void main(String[] args) throws IOException {
        read("c:\\itp\\Sheet_1.csv", "soltius");
    }
}
