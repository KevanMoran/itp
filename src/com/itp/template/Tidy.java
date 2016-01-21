/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itp.template;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kevan
 */
public class Tidy {

    public static void main(String[] args) throws IOException {
        List<String> tidied = tidy("C:\\ITP\\Template 20160118 - old\\word\\document.xml");
        FileOutputStream fos = new FileOutputStream(new File("C:\\ITP\\Template 20160118 - old\\word\\tidied_document.xml"));
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos))) {
            for (String line : tidied) {
                bw.write(line + "\n");
            }
        }
    }

    public static List<String> tidy(String toTidy) throws IOException {
        Path inputPath = Paths.get(toTidy);
        List<String> tidiedLines = new ArrayList<>();
        List<String> lines = Files.readAllLines(inputPath);

        int lineNo = 0;
        while (lineNo < lines.size()) {
            String line = lines.get(lineNo);
            int braCount = count(line, "[[");
            int ketCount = count(line, "]]");
            if (braCount != ketCount) {
                int slashWT = line.indexOf("</w:t>");
                String toWrite = line.substring(0, slashWT);
                boolean closed = false;
                while (!closed) {
                    lineNo++;
                    line = lines.get(lineNo);
                    if (line.contains("<w:t")) {
                        int ket = line.indexOf(">");
                        int bra = line.indexOf("<", ket);
                        String toAdd = line.substring(ket + 1, bra);
                        toWrite += toAdd;
                        if (line.contains("[[SecondaryCategory")) {
                            System.out.println("here");
                        }
                        braCount = count(line, "[[");
                        ketCount = count(line, "]]");
                        if (ketCount > braCount) {
                            closed = true;
                        }
                    }
                }
                toWrite += "</w:t>";
                line = toWrite;
            }
            
            tidiedLines.add(line);
            lineNo++;
        }

        return tidiedLines;
    }

    private static int count(String line, String chars) {
        int count = 0;
        int index = 0;
        while ((index = line.indexOf(chars, index + 1)) != -1) {
            count++;
        }
        return count;
    }
}
